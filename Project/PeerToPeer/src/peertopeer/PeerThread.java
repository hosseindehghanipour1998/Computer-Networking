package peertopeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
//this peers is responsible to snnif in other peers lives and send the data to his boss.
public class PeerThread extends Thread  {
    boolean connected = true;
    private BufferedReader bufferedReader;
    public  Socket socket ;
    public PeerThread(Socket socket) throws IOException{
        this.socket = socket ;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {

        while (connected){
            try{
                String latestMessage = bufferedReader.readLine();
                if ( latestMessage.equals("") == false){
                     String[] messageElements = latestMessage.split("\\|");
                    System.out.println("[" + messageElements[1] + "][" + messageElements[2] + "]");
                }
            } catch (Exception e){
                connected = false;
                interrupt();
                System.out.println("In PeerThread Excpetion");
                e.printStackTrace();
            }
        }
    }
}
