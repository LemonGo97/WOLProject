package cn.lemongo97.wol;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
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

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("clientId") String clientId) {
        this.session = session;
        this.clientId = clientId;
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
        log.info("收到客户端消息: {} ,报文: {}", clientId, message);
        try {
            sendMessage("asd");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, String clientId) throws IOException {
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
}
