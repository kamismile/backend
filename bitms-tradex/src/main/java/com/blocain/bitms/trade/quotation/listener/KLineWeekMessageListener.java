package com.blocain.bitms.trade.quotation.listener;

import java.util.Iterator;
import java.util.Set;

import org.springframework.data.redis.connection.Message;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import com.blocain.bitms.tools.utils.ListUtils;
import com.blocain.bitms.tools.utils.StringUtils;
import com.blocain.bitms.trade.quotation.websocket.QuotationWebSocketHandler;

/**
 * 周线消息监听器
 * <p>File：KLineWeekMessageListener.java</p>
 * <p>Title: KLineWeekMessageListener</p>
 * <p>Description: KLineWeekMessageListener</p>
 * <p>Copyright: Copyright (c) 2017/8/3</p>
 * <p>Company: BloCain</p>
 *
 * @author Playguy
 * @version 1.0
 */
public class KLineWeekMessageListener extends BaseMessageListener
{
    private QuotationWebSocketHandler quotationWebSocketHandler;
    
    @Override
    public void onMessage(Message message, byte[] pattern)
    {
        String content = (String) serializer.deserialize(message.getBody());
        if (StringUtils.isNotBlank(content))
        {
            if (content.hashCode() != quotationWebSocketHandler.getSessionConst().getKlineWeekContent().hashCode())
            {
                // 如果数据更新，向全体广播
                if (ListUtils.isNotNull(quotationWebSocketHandler.getSessionConst().getkLineWeekSessions()))
                {
                    BinaryMessage binaryMessage = new BinaryMessage(content.getBytes());
                    for (WebSocketSession session : quotationWebSocketHandler.getSessionConst().getkLineWeekSessions())
                    {
                        try
                        {
                            if (session.isOpen()) session.sendMessage(binaryMessage);
                        }
                        catch (Exception e)
                        {
                            // logger.warn("消息发送失败！{}", e.getLocalizedMessage());
                        }
                    }
                }
                quotationWebSocketHandler.getSessionConst().setKlineWeekContent(content);
            }
            // 为主动拉数据的用户推送消息
            if (ListUtils.isNotNull(quotationWebSocketHandler.getSessionConst().getkLineWeekSessions_tmp()))
            {
                Set<WebSocketSession> tmp = quotationWebSocketHandler.getSessionConst().getkLineWeekSessions_tmp();
                BinaryMessage binaryMessage = new BinaryMessage(content.getBytes());
                Iterator<WebSocketSession> it = tmp.iterator();
                while (it.hasNext())
                {
                    try
                    {
                        WebSocketSession session = it.next();
                        if (session.isOpen()) session.sendMessage(binaryMessage);
                        it.remove();
                    }
                    catch (Exception e)
                    {
                        // logger.warn("消息发送失败！{}", e.getLocalizedMessage());
                    }
                }
            }
        }
    }
    
    public void setQuotationWebSocketHandler(QuotationWebSocketHandler quotationWebSocketHandler)
    {
        this.quotationWebSocketHandler = quotationWebSocketHandler;
    }
}
