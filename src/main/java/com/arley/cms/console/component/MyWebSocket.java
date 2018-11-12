package com.arley.cms.console.component;

import com.arley.cms.console.pojo.vo.WSMessage;
import com.arley.cms.console.util.FastJsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XueXianlei
 * @Description: websocket
 * @date 2018/11/12 10:35
 */
@ServerEndpoint(value = "/websocket/{sid}")
@Component
public class MyWebSocket {
    private static final Logger logger = LoggerFactory.getLogger(MyWebSocket.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Map，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static Map<String, MyWebSocket> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        if (null != webSocketMap.get(sid)) {
            // 已经存在 先删除
            webSocketMap.remove(sid);
            subOnlineCount();
        }
        //加入map中
        webSocketMap.put(sid, this);
        //在线数加1
        addOnlineCount();
        logger.info("【WebSocket】有新连接加入={} 当前在线人数为={}", sid, getOnlineCount());
        try {
            WSMessage wsMessage = new WSMessage();
            wsMessage.setMessage("连接成功");
            wsMessage.setType(0);
            sendMessage(wsMessage);
        } catch (IOException e) {
            logger.error("【WebSocket】IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        //从set中删除
        webSocketMap.remove(sid);
        //在线数减1
        subOnlineCount();
        logger.info("【WebSocket】{}连接关闭!当前在线人数为={}", sid, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("【WebSocket】来自客户端的消息={}", message);

    }

    /**
     * 发生错误时调用
     * */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("【WebSocket】发生错误={}", error);
        error.printStackTrace();
    }


    /**
     * 发送消息
     * @param wsMessage
     * @throws IOException
     */
    public void sendMessage(WSMessage wsMessage) throws IOException {
        this.session.getBasicRemote().sendText(FastJsonUtils.obj2Str(wsMessage));
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(WSMessage wsMessage){
        if (StringUtils.isNotBlank(wsMessage.getSid())) {
            // 单发消息
            MyWebSocket webSocket = webSocketMap.get(wsMessage.getSid());
            if (null != webSocket) {
                try {
                    webSocket.sendMessage(wsMessage);
                } catch (IOException e) {
                    logger.error("【WebSocket】发送失败:{}", wsMessage);
                }
            }
        } else {
            // 群发消息
           for (MyWebSocket item : webSocketMap.values()) {
               try {
                   item.sendMessage(wsMessage);
               } catch (IOException e) {
                   continue;
               }
           }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}