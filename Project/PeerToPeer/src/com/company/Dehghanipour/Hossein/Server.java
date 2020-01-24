package com.company.Dehghanipour.Hossein;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    public static ArrayList<Server> allServers = new ArrayList<>() ;
    private ArrayList<ServerThread> thisServerThreads = new ArrayList<>() ;

    private ServerSocket server  = null ;
    private Socket socket = null ;
    private DataInputStream in = null ;
    private String portNo = null ;



    public Server(String port) throws IOException {
        this.portNo = port ;
        server = new ServerSocket(Integer.valueOf(port)) ;
        allServers.add(this);
        this.start();
    }

    public void createThread() throws IOException {
        socket = server.accept();
        System.out.println(this.socket.getPort());
        ServerThread newThread = new ServerThread(this.socket,this);
        this.thisServerThreads.add(newThread);
        newThread.start();
    }

    public void sendMessage(Message msg){
        try{

            for ( ServerThread serverThread : thisServerThreads){
                serverThread.getPrintWriter().println(msg.format());
                Node.sendMessages.add(msg);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        super.run();
        while (true){
            try{

                createThread();
            }

            catch (Exception e){

            }
        }
    }

    public static ArrayList<Server> getAllServers() {
        return allServers;
    }

    public static void setAllServers(ArrayList<Server> allServers) {
        Server.allServers = allServers;
    }

    public ArrayList<ServerThread> getThisServerThreads() {
        return thisServerThreads;
    }

    public void setThisServerThreads(ArrayList<ServerThread> thisServerThreads) {
        this.thisServerThreads = thisServerThreads;
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }
}
