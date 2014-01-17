package controllers;

import org.jboss.netty.handler.codec.http.websocket.WebSocketFrame;

import play.mvc.Http.WebSocketClose;
import play.mvc.Http.WebSocketEvent;
import play.mvc.WebSocketController;

public class TestSocket extends WebSocketController {
 
    public static void hello(String name) {
 
        while(inbound.isOpen()) {
            WebSocketEvent evt = await(inbound.nextEvent());
            if(evt instanceof WebSocketFrame) {
                WebSocketFrame frame = (WebSocketFrame)evt;
                System.out.println("received: " + frame.getTextData());
                if(!frame.isBinary()) {
                    if(frame.getTextData().equals("quit")) {
                        outbound.send("Bye!");
                       
                    } else {
                        outbound.send("Echo: %s", frame.getTextData());
                    }
                }
            } else if(evt instanceof WebSocketClose) {
                System.out.println("socket closed");
            }
        }
    }
 
}