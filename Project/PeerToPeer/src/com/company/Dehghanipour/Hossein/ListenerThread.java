package com.company.Dehghanipour.Hossein;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ListenerThread extends Thread{
    public static ArrayList<ListenerThread> allListenerThreads = new ArrayList<>() ;
    private Socket targetSocket ;
    private Boolean connected = true ;
    private Node bossNode  = null ;
    private BufferedReader bufferedReader;


    public ListenerThread(Socket targetSocket , Node boss ) throws IOException {

        this.bossNode = boss ;
        this.targetSocket = targetSocket ;
        bufferedReader = new BufferedReader(new InputStreamReader(targetSocket.getInputStream()));
        this.start();
        allListenerThreads.add(this);
    }

    boolean doesExist(Message m){
        for(Message msg : Node.receivedMessages ){
            if ( msg.getMsgId() == m.getMsgId() ){
                return true ;
            }
        }


        return false ;
    }

    @Override
    public void run() {
        super.run();
        while(connected){
            try{
                String latestMessage = bufferedReader.readLine();
                if ( latestMessage.equals("") == false){
                    String[] messageElements = latestMessage.split("\\|");
                    Message m = new Message(latestMessage , Integer.valueOf(messageElements[0]));
                    bossNode.getServer().sendMessage(m);
                    Node.sendMessages.add(m);
                    if ( doesExist(m) == false ){
                        System.out.println(latestMessage);
                        Node.receivedMessages.add(m);
                    }

                }

            }
            catch (Exception e){

            }
        }
    }
}
