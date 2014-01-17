package models;

import java.util.*;

import play.libs.*;
import play.libs.F.*;

public class ChatRoom {
    
    // ~~~~~~~~~ Let's chat! 
    
    final ArchivedEventStream<ChatRoom.Event> chatEvents = new ArchivedEventStream<ChatRoom.Event>(100);
    List<String> usuarios = new ArrayList<>();
    /**
     * For WebSocket, when a user join the room we return a continuous event stream
     * of ChatEvent
     */
    public EventStream<ChatRoom.Event> join(String user) {
        chatEvents.publish(new Join(user));
        usuarios.add(user);
        chatEvents.publish(new Lista(usuarios));
        return chatEvents.eventStream();
    }
    
    /**
     * A user leave the room
     */
    public void leave(String user) {
    	
        chatEvents.publish(new Leave(user));
    }
    
    /**
     * A user say something on the room
     */
    public void say(String user, String text) {
        if(text == null || text.trim().equals("")) {
            return;
        }
        chatEvents.publish(new Message(user, text));
    }
    
    /**
     * For long polling, as we are sometimes disconnected, we need to pass 
     * the last event seen id, to be sure to not miss any message
     */
    public Promise<List<IndexedEvent<ChatRoom.Event>>> nextMessages(long lastReceived) {
        return chatEvents.nextEvents(lastReceived);
    }
    
    /**
     * For active refresh, we need to retrieve the whole message archive at
     * each refresh
     */
    public List<ChatRoom.Event> archive() {
        return chatEvents.archive();
    }
    
    // ~~~~~~~~~ Chat room events

    public static abstract class Event {
        
        final public String type;
        final public Long timestamp;
        
        public Event(String type) {
            this.type = type;
            this.timestamp = System.currentTimeMillis();
        }
        
    }
    
public static class Lista extends Event {
	 	List<String> usuarios = new ArrayList<>();
       
        
        public Lista(List<String> usuarios) {
            super("join");
            this.usuarios = usuarios;
        }
        
    }
    
    public static class Join extends Event {
        
        final public String user;
        
        public Join(String user) {
            super("join");
            this.user = user;
        }
        
    }
    
    public static class Leave extends Event {
        
        final public String user;
        
        public Leave(String user) {
            super("leave");
            this.user = user;
        }
        
    }
    
    public static class Message extends Event {
        
        final public String user;
        final public String text;
        
        public Message(String user, String text) {
            super("message");
            this.user = user;
            this.text = text;
        }
        
    }
    
    // ~~~~~~~~~ Chat room factory

    static Map<Integer,ChatRoom> listaSalas = new HashMap<Integer,ChatRoom>();
    public static ChatRoom get(Integer codigo) {
    	
    	if(listaSalas.get(codigo) == null) {
    		listaSalas.put(codigo, new ChatRoom());
        }
        return listaSalas.get(codigo);
    }
    
}

