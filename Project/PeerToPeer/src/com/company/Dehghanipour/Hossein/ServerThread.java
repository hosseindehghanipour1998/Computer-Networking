package com.company.Dehghanipour.Hossein;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private static int idCounter = 0 ;
    public static ArrayList<ServerThread> allThreads = new ArrayList<>();
    private Socket socket = null;
    private int threadID = 0 ;
    private Server boss ;
    private PrintWriter printWriter;


    public ServerThread(Socket socket , Server serverBoss) throws IOException {
        this.socket = socket ;
        this.threadID = idCounter++ ;
        this.boss = serverBoss ;
        //allThreads.add(this);


    }


    @Override
    public void run() {
        try{
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(),true);

           // while (true) {
                //String sendingMsg = bufferedReader.readLine() ;
                /*
                String tempUserName , tempMsg ;
                System.out.println(sendingMsg);
                int tempId ;
                String[] msgParts = sendingMsg.split("\\|");
                tempId = Integer.valueOf(msgParts[0]);
                tempUserName = msgParts[1] ;
                tempMsg = msgParts[2] ;
                */

                //Message newMessage = new Message("haaaaa" , "Khaaaar") ;
                //boss.sendMessage(newMessage);
                //Node.sendMessages.add(newMessage);

           // }
        }
        catch (Exception e){
            boss.getThisServerThreads().remove(this);
            System.out.println("In ServerThread.Run");
            e.printStackTrace();
        }

    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        ServerThread.idCounter = idCounter;
    }

    public static ArrayList<ServerThread> getAllThreads() {
        return allThreads;
    }

    public static void setAllThreads(ArrayList<ServerThread> allThreads) {
        ServerThread.allThreads = allThreads;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getThreadID() {
        return threadID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    public Server getBoss() {
        return boss;
    }

    public void setBoss(Server boss) {
        this.boss = boss;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
}


