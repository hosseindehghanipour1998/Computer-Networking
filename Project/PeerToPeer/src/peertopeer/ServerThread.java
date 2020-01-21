package peertopeer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerThread extends Thread  {
    private ServerSocket serverSocket;
    public static  Set<ServerThreadThread> serverThreadThreads = new HashSet<ServerThreadThread>();
    public ServerThread(String portNumb) throws IOException{
        serverSocket = new ServerSocket(Integer.valueOf(portNumb));
    }

    @Override
    public void run() {
        try{
            while (true){
                ServerThreadThread serverThreadThread = new ServerThreadThread(serverSocket.accept() , this);
                serverThreadThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String message){
        try {
            //serverThreadThreads.forEach(t->t.getPrintWriter().println(message));
            for(ServerThreadThread stt: serverThreadThreads ){
                stt.getPrintWriter().println(message);
                System.out.println(stt.socketPort);
            }
            System.out.println("Server Received : [" + message + "]");
        }catch (Exception e){e.printStackTrace();
            System.out.println("In ServerThread Excpetion");}
    }

    public Set<ServerThreadThread> getServerThreadThreads() {
        return serverThreadThreads;
    }
}
