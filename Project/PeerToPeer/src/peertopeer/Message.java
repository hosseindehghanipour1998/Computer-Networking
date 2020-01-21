package peertopeer;

import java.util.ArrayList;

public class Message {
    private static int idCounter = 0 ;
    private static ArrayList<Message> allMessages = new ArrayList<>() ;
    private int msgId ;
    private String senderUsernamePort;
    private String message = "DEFAULT";

    public Message(String messageBody , String username ){
        msgId = idCounter++ ;
        this.message = messageBody ;
        this.senderUsernamePort = username ;
        allMessages.add(this);
    }

    public Message(String messageBody , String username , int id){
        msgId = id ;
        this.message = messageBody ;
        this.senderUsernamePort = username ;
    }

    public String printMessage(){
        String msgFormat =  this.msgId+"|" + this.senderUsernamePort + "|" + message ;
        return msgFormat ;
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

    public String getSenderUsernamePort() {
        return senderUsernamePort;
    }

    public void setSenderUsernamePort(String senderUsernamePort) {
        this.senderUsernamePort = senderUsernamePort;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String[] extractMessageParts(String messageString){
        System.out.println(messageString);
        return messageString.split("|");
    }
}
