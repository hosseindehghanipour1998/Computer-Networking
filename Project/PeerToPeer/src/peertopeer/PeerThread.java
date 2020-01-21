package peertopeer;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PeerThread extends Thread  {
    private BufferedReader bufferedReader;
    public PeerThread(Socket socket) throws IOException{
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    @Override
    public void run() {
        boolean connected = true;
        while (connected){
            try{
                //JsonObject jsonObject = Json.createReader(bufferedReader).readObject();
                String latestMessage = bufferedReader.readLine().toString();
                String username = null ;
                String receiverMessage = null ;
                if ( latestMessage != ""){
                     username = latestMessage.split(":")[0] ;
                    System.out.println("Last Message : " + latestMessage);
                     receiverMessage = latestMessage.split(":")[1];
                }

                if(username != null)
                    System.out.println("[" + username + "]: " + receiverMessage);
            } catch (Exception e){
                connected = false;
                interrupt();
                System.out.println("In PeerThread Excpetion");
                e.printStackTrace();
            }
        }
    }
}
