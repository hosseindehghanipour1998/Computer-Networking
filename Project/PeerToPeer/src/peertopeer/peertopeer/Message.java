package peertopeer.peertopeer;

import java.util.ArrayList;

public class Message {
    private static int idCounter = 0 ;
    private static ArrayList<Message> allMessages = new ArrayList<>() ;
    private int msgId ;
    private String message = "DEFAULT";

    public Message(String messageBody){
        msgId = idCounter++ ;
        this.message = messageBody ;
        allMessages.add(this);
    }


    public static ArrayList<Message> getAllMessages() {
        return allMessages;
    }


    public int getMsgId() {
        return msgId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
