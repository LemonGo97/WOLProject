package cn.lemongo97.wol;

import cn.hutool.crypto.CipherMode;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author lemongo97
 */
@Service
public class WebSocketClient implements InitializingBean {

    @Value("${wol.clientId}")
    private String clientId;
    @Value("${wol.clientKey}")
    private String clientKey;
    @Value("${wol.serviceUrl}")
    private String serviceUrl;

    private WebSocketContainer container;
    private WebSocketClientHandler client;

    public void sendMessage(String message){
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        String encrypt = new AES(Mode.CBC, Padding.PKCS5Padding).setIv(clientId.getBytes(StandardCharsets.UTF_8)).encryptBase64(message);
        client.send("client send: 客户端消息 " + message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        container = ContainerProvider.getWebSocketContainer();
        client = new WebSocketClientHandler();
        container.connectToServer(client, new URI(serviceUrl + clientId));
    }
}
