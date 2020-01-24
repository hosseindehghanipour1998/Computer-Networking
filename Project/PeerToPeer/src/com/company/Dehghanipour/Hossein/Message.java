package com.company.Dehghanipour.Hossein;

import java.util.ArrayList;

public class Message {
    private static int idCounter = 0 ;
    private static ArrayList<Message> allMessages = new ArrayList<>() ;
    private int msgId = 0 ;
    private String senderPort;
    private String senderUsername ;
    private String message = "DEFAULT";
/*
    public Message(String messageBody , String username , String senderPort){
        msgId = idCounter++ ;
        this.message = messageBody ;
        this.senderPort = senderPort ;
        this.senderUsername = username ;
        allMessages.add(this);
    }
*/
    public Message(String messageBody , String username){
        msgId = idCounter++ ;
        this.message = messageBody ;
        this.senderUsername = username ;
    }

    public Message(String messageBody , int id ){
        this.msgId = id ;
        this.message = messageBody;
    }

    public String format(){
        return ( this.msgId + "|" + this.senderUsername + "|" + this.senderPort + "|" + this.message ) ;
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

    public String getSenderPort() {
        return senderPort;
    }

    public void setSenderPort(String senderPort) {
        this.senderPort = senderPort;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
