package peertopeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//this peers is responsible to snnif in other peers lives and send the data to his boss.
public class PeerThread extends Thread  {
    boolean connected = true;
    PrintWriter pw ;
    Peer boss ;
    private BufferedReader bufferedReader;
    public  Socket socket ;
    public PeerThread(Socket socket , Peer boss) throws IOException{
        this.socket = socket ;
        this.boss = boss ;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {

        while (connected){
            try{
                String latestMessage = bufferedReader.readLine();
                if ( latestMessage.equals("") == false){
                     String[] messageElements = latestMessage.split("\\|");
                    //System.out.println("[" + messageElements[1] + "][" + messageElements[2] + "]");
                    //pw = new PrintWriter(boss.socket.,true);
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
