package cn.lemongo97.wol;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

/**
 * @author lemongo97
 */
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
        client.send(messageCrypt.encode(message));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        container = ContainerProvider.getWebSocketContainer();
        client = new WebSocketClientHandler(messageCrypt);
        container.connectToServer(client, new URI(serviceUrl + clientId));
    }
}
