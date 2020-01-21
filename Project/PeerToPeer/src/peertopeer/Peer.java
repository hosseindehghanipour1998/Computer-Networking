package peertopeer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Peer {

    public static ArrayList<Peer> allPeers = new ArrayList<>() ;
    public ArrayList<Peer> followings = new ArrayList<>() ;
    public static void main(String[] args) throws Exception {
        // Input Reader Like Scanner :
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // Get the desired port of the current peer.
        System.out.println("> enter username & port # for this peer:");
        String[] setupValues = bufferedReader.readLine().split(" ");
        //create a connection in the server Thread for this peer and add it to the server database
        ServerThread serverThread = new ServerThread(setupValues[1]);
        serverThread.start();
        // Update this peers FOLLOWINGS
        new Peer().updateListenToPeers(bufferedReader,setupValues[1],serverThread);
    }
    // This functions updates the followings of this peer -> whom this peer trusts.
    public void updateListenToPeers(BufferedReader bufferedReader , String username , ServerThread serverThread) throws Exception{
        System.out.println("> enter (space separated) hostname:port#");
        System.out.println(" peers to receive messages from (s to skip)");
        String input = bufferedReader.readLine();
        String[] inputValues = input.split(" ");

        if(!input.equals("s")) //  s stands for Skip -> if we had Skip it means there are followings for this peer.
            for (int i = 0 ; i <inputValues.length ; i++){
                String[] address = inputValues[i].split(":");
                Socket socket = null;
                try{
                    // Socket ( IP Address , portNo )
                    socket = new Socket(address[0],Integer.valueOf(address[1]));
                    new PeerThread(socket).start(); //  activate the peer whom this peer is following -> make him listen.
                }catch (Exception e){
                    if(socket != null) socket.close(); // if the wanted following did not exist.
                    else System.out.println("invalid input , skipping to next step!");
                }
            }
        communicate(bufferedReader,username,serverThread);
    }

    public void communicate(BufferedReader bufferedReader , String username , ServerThread serverThread){
        try{
            System.out.println("> you can now communicate (e to exit , c to change)");
            boolean connected = true;
            while (connected){
                // while the peer wants to be connected get the input message he wants to send.
                String message = bufferedReader.readLine();
                if (message.equals("e")){
                    // when we want to get disconnected.
                    connected = false;
                    break;
                }else if(message.equals("c")){
                    // when we want to change the followings list.
                    updateListenToPeers(bufferedReader,username,serverThread);
                }else{
                    //When we want to send message to other peers that follow us.
                    StringWriter stringWriter = new StringWriter();
                    Message newMessageToSend = new Message(message,username) ;
                    serverThread.sendMessage(newMessageToSend);
                }

            }
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("In Peer Exception");
        }
    }
}
