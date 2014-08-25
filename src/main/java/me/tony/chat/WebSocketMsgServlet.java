/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.tony.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

/**
 *
 * @author Administrator
 */
@WebServlet(urlPatterns = { "/message"})
public class WebSocketMsgServlet extends WebSocketServlet{
   private static final long serialVersionUID = -4853540828121130946L;
    private static ArrayList<WebSocketMessageInbound> mmiList = new ArrayList<WebSocketMessageInbound>();

    @Override
    protected StreamInbound createWebSocketInbound(String string, HttpServletRequest hsr) {
         return new WebSocketMessageInbound();
    }
    
    
    private class WebSocketMessageInbound extends MessageInbound{

       WsOutbound myoutbound;

        @Override
        public void onOpen(WsOutbound outbound) {
            try {
                System.out.println("Open Client.");
                this.myoutbound = outbound;
                mmiList.add(this);
                outbound.writeTextMessage(CharBuffer.wrap("Hello!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClose(int status) {
            System.out.println("Close Client.");
            mmiList.remove(this);
        }

        @Override
        public void onTextMessage(CharBuffer cb) throws IOException {
            System.out.println("Accept Message : " + cb);
            for (WebSocketMessageInbound mmib : mmiList) {
                CharBuffer buffer = CharBuffer.wrap(cb);
                mmib.myoutbound.writeTextMessage(buffer);
                mmib.myoutbound.flush();
            }
        }

        @Override
        public void onBinaryMessage(ByteBuffer bb) throws IOException {
        }
    }
}
