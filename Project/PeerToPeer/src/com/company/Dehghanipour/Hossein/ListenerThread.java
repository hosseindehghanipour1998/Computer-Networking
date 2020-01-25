package com.company.Dehghanipour.Hossein;

import javafx.application.Platform;

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

        for(Message msg : Node.sendMessages){
            if ( msg.getMsgId() == m.getMsgId()){
                return true ;
            }
        }
        return false ;
    }

    public static ArrayList<Message> query(String findStr){
        ArrayList<Message> found = new ArrayList<>();
        for( Message msg : Node.sendMessages ){
            if ( msg.getMessage().contains(findStr)){
                found.add(msg);
            }
        }

        for(Message msg : Node.receivedMessages ){
            if ( msg.getMessage().contains(findStr)){
                found.add(msg);
            }
        }

        return found ;
    }

    @Override
    public void run() {
        super.run();
        while(connected){
            try{
                String latestMessage = bufferedReader.readLine();
                if ( latestMessage.equals("") == false){
                    String[] messageElements = latestMessage.split("\\|");
                    Message m = new Message(latestMessage , Integer.valueOf(messageElements[0]) , messageElements[1]);

                    if ( doesExist(m) == false ){
                        System.out.println("["+messageElements[1]+"]:" + messageElements[2]);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Main.logs.setText(Main.logs.getText()+'\n'+"["+messageElements[1]+"]:" + messageElements[2]);
                                if ( Main.logs.getText().length() > 50 ){
                                    Main.logs.setText("Cleared!");
                                }
                                Node.receivedMessages.add(m);
                                bossNode.getServer().sendMessage(m);
                            }
                        });

                       // Node.sendMessages.add(m);
                    }

                }


            }
            catch (Exception e){

            }
        }
    }
}
