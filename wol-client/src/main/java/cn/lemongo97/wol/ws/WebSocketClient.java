package cn.lemongo97.wol.ws;

import cn.hutool.core.lang.Console;
import cn.hutool.crypto.CryptoException;
import cn.lemongo97.wol.common.Response;
import cn.lemongo97.wol.model.command.Command;
import cn.lemongo97.wol.tools.MessageCrypt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import java.net.URI;

/**
 * @author lemongo97
 */
@Slf4j
@Service
public class WebSocketClient implements InitializingBean {

    @Value("${wol.clientId}")
    private String clientId;
    @Value("${wol.serviceUrl}")
    private String serviceUrl;

    @Autowired
    private MessageCrypt messageCrypt;

    private WebSocketContainer container;
    private WebSocketClientHandler client;

    public void sendMessage(String message){
        client.send(Response.success(clientId, message));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        container = ContainerProvider.getWebSocketContainer();
        client = new WebSocketClientHandler(clientId, messageCrypt);
        container.connectToServer(client, new URI(serviceUrl + clientId));
    }

    @Slf4j
    @ClientEndpoint
    public static class WebSocketClientHandler {

        private Session session;

        private final String clientId;
        private final MessageCrypt messageCrypt;

        public WebSocketClientHandler(String clientId, MessageCrypt messageCrypt) {
            this.clientId = clientId;
            this.messageCrypt = messageCrypt;
        }

        @OnOpen
        public void open(Session session) {
            log.info("Client WebSocket is opening...");
            this.session = session;
        }

        @OnMessage
        public void onMessage(String message) {
            try{
                message = messageCrypt.decode(message);
                Console.log(message);
                Response<Command> response = new Gson().fromJson(message, new TypeToken<Response<Command>>() {
                }.getType());
                response.getBody().execute(this);
            } catch (CryptoException e){
                log.error("服务端信息解码失败，请检查 clientId 和 clientKey 是否正确！！！",e);
            }
        }

        @OnClose
        public void onClose() {
            log.info("Websocket closed");
        }

        /**
         * 发送客户端消息到服务端
         *
         * @param message 消息内容
         */
        public <T> void send(Response<T> message) {
            this.session.getAsyncRemote().sendText(messageCrypt.encode(new Gson().toJson(message)));
        }

        public <T> void send(T message) {
            this.send(Response.success(clientId, message));
        }
    }
}
