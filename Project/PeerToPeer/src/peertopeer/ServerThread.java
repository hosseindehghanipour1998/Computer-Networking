package peertopeer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerThread extends Thread  {
    private ServerSocket serverSocket;
    public static  Set<ServerThreadThread> serverThreadThreads = new HashSet<ServerThreadThread>();

    //Constructor :
    public ServerThread(String portNumb) throws IOException{
        serverSocket = new ServerSocket(Integer.valueOf(portNumb));
    }

    @Override
    public void run() {
        try{
            while (true){
                // accept everything that is being sent to this server :
                ServerThreadThread serverThreadThread = new ServerThreadThread(serverSocket.accept() , this);
                serverThreadThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(Message sendingMessage){
        try {
            //serverThreadThreads.forEach(t->t.getPrintWriter().println(message));
            for(ServerThreadThread stt: serverThreadThreads ){
                stt.getPrintWriter().println(sendingMessage.printMessage());
            }
        }catch (Exception e){e.printStackTrace();
            System.out.println("In ServerThread Excpetion");}
    }

    public Set<ServerThreadThread> getServerThreadThreads() {
        return serverThreadThreads;
    }
}
