package cn.lemongo97.wol.ws;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.lemongo97.wol.common.Response;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.Console;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lemongo97
 */
@Slf4j
@ServerEndpoint("/ws/{clientId}")
@Component
public class WebSocketServer {

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收 clientId
     */
    private String clientId = "";

    private AES aes;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("clientId") String clientId) {
        this.session = session;
        this.clientId = clientId;
        this.createCryptMethod();
        webSocketMap.remove(clientId);
        webSocketMap.put(clientId, this);
        log.info("当前连接客户端: {}", clientId);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(clientId);
        log.info("客户端 {} 退出:", clientId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        message = this.aes.decryptStr(message);
        log.info("收到客户端消息: {} ,报文: {}", clientId, message);
//        try {
//            sendMessage("asd");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (StrUtil.isNotBlank(message)) {
//            try {
//                Map<String, String> jsonObject = new Gson().fromJson(message, new TypeToken<Map<String, String>>() {
//                }.getType());
//                //解析发送的报文
//                //追加发送人(防止窜改)
//                jsonObject.put("from", this.clientId);
//                String clientId = jsonObject.get("to");
//                //传送给对应 to 客户端的websocket
//                if (StrUtil.isNotBlank(clientId) && webSocketMap.containsKey(clientId)) {
//                    webSocketMap.get(clientId).sendMessage(new Gson().toJson(jsonObject));
//                } else {
//                    webSocketMap.get(this.clientId).sendMessage("请求的 client:" + clientId + "不在该服务器上");
//                    log.info("请求的 client: {} 不在该服务器上", clientId);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("客户端错误: {} ,原因: {} " ,this.clientId, error);
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public <T> void sendMessage(Response<T> message) throws IOException {
        this.session.getBasicRemote().sendText(aes.encryptBase64(new Gson().toJson(message)));
    }

    /**
     * 发送自定义消息
     */
    public static <T> void sendInfo(String clientId, Response<T> message) throws IOException {
        log.info("发送消息到: {} ，报文: {}",clientId , message);
        if (StrUtil.isNotBlank(clientId) && webSocketMap.containsKey(clientId)) {
            webSocketMap.get(clientId).sendMessage(message);
        } else {
            log.info("客户端 {} ,不在线！", clientId);
        }
    }

    public static ConcurrentHashMap<String, WebSocketServer> getWebSocketMap() {
        return webSocketMap;
    }

    public static void setWebSocketMap(ConcurrentHashMap<String, WebSocketServer> webSocketMap) {
        WebSocketServer.webSocketMap = webSocketMap;
    }

    private void createCryptMethod(){
        // TODO find clientKey By clientId from db
        String clientKey = "FVHarl4P3PNLCksPpp7S7w==";
        byte[] key = Base64.decode(clientKey);
        byte[] iv = new byte[16];

        byte[] clientIdBytes = clientId.getBytes();
        for (int i = 0; i < clientIdBytes.length; i+=2) {
            iv[i/2] = (byte) (clientIdBytes[i] & clientIdBytes[i+1]);
        }
        this.aes = new AES(Mode.CBC, Padding.PKCS5Padding, key, iv);
    }

}
