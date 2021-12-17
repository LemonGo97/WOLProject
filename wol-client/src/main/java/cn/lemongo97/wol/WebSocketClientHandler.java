package cn.lemongo97.wol;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;

/**
 * @author lemongo97
 */
@Slf4j
@ClientEndpoint
public class WebSocketClientHandler {

    private Session session;

    private final MessageCrypt messageCrypt;

    public WebSocketClientHandler(MessageCrypt messageCrypt) {
        this.messageCrypt = messageCrypt;
    }

    @OnOpen
    public void open(Session session) {
        log.info("Client WebSocket is opening...");
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("Server send message: " + messageCrypt.decode(message));
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
    public void send(String message) {
        this.session.getAsyncRemote().sendText(message);
    }
}
