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

    @Override
    public void run() {
        super.run();
        while(connected){
            try{
                String latestMessage = bufferedReader.readLine();
                if ( latestMessage.equals("") == false){
                    //String[] messageElements = latestMessage.split("\\|");
                    bossNode.printMsg(latestMessage);
                    System.out.println(latestMessage);
                    Message m = new Message(latestMessage , "Shit");
                    bossNode.getServer().sendMessage(m);
                }

            }
            catch (Exception e){

            }
        }
    }
}
