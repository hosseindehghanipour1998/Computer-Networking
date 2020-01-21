package peertopeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Peer {
    private static int idCounter = 0 ;
    private int id ;
    private String socketPort ;
    private String username;
    private String peerIPAddress ;
    public static ArrayList<Peer> allPeers = new ArrayList<>() ;

    public ArrayList<Peer> followingsPeers = new ArrayList<>() ;
    public ArrayList<PeerThread> followingPeersThreds = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        Peer.createPeer();

    }

    public Boolean socketExists(String socketPort){
        for( PeerThread pt : followingPeersThreds){
            if ( socketPort.equals(String.valueOf(pt.socket.getPort())) ){
                return  true ;
            }
        }
        return false ;
    }




    // This functions updates the followings of this peer -> whom this peer trusts.
    public void updateFollowings(BufferedReader bufferedReader , String username , PeerMailMan peerMailMan) throws Exception{
        System.out.println("> enter (space separated) hostname:port#");
        System.out.println(" peers to receive messages from (s to skip)");
        String input = bufferedReader.readLine();
        String[] inputValues = input.split(" ");

        if(!input.equals("s")) //  s stands for Skip -> if we had Skip it means there are followings for this peer.
            for (int i = 0 ; i <inputValues.length ; i++){
                String[] address = inputValues[i].split(":");
                Socket socket = null;

                try{
                    String socketPortNumber = address[1];
                    String socketIpAddress = address[0];
                    if ( socketExists(socketPortNumber) == false ){
                        // Socket ( IP Address , portNo )
                        socket = new Socket(socketIpAddress,Integer.valueOf(socketPortNumber));// for each following add a socket
                        addTheFollowingToDatabase(socketPortNumber , socketIpAddress);
                        PeerThread newFollowingThread = new PeerThread(socket) ; //  we create a thread for each following
                        this.followingPeersThreds.add(newFollowingThread);
                        newFollowingThread.start(); // start listening on the following's actions
                        //upgradeThisPeersFollowersFollowings(this.socketPort);

                    }

                }catch (Exception e){
                    if(socket != null) socket.close(); // if the wanted following did not exist.
                    else System.out.println("invalid input , skipping to next step!");
                }
            }
        communicate(bufferedReader,username, peerMailMan);
    }


    public void communicate(BufferedReader bufferedReader , String username , PeerMailMan peerMailMan){
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

                    updateFollowings(bufferedReader,username, peerMailMan);
                }else{
                    //When we want to send message to other peers that follow us.
                    StringWriter stringWriter = new StringWriter();
                    Message newMessageToSend = new Message(message,username) ;
                    peerMailMan.sendMessage(newMessageToSend);
                }

            }
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("In Peer Exception");
        }
    }

    public static void createPeer() throws Exception {
        // Input Reader Like Scanner :
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // Get the desired port of the current peer.
        System.out.println("> enter username & port # for this peer:");
        String[] setupValues = bufferedReader.readLine().split(" ");

        //create a connection in the server Thread for this peer and add it to the server database
        String peerPortNo = setupValues[1] ;
        PeerMailMan peerMailMan = new PeerMailMan(peerPortNo);
        peerMailMan.start();
        Peer p = new Peer(bufferedReader,setupValues[1], peerMailMan, setupValues[0]);

    }

    private void addTheFollowingToDatabase(String socketPort , String ipAddress){
        for(Peer p : Peer.allPeers){
            if( p.getSocketPort().equals(socketPort)){
                p.peerIPAddress = peerIPAddress ;
                this.getFollowingsPeers().add(p);
            }
        }
    }

    public Peer(BufferedReader bufferedReader , String socketPort , PeerMailMan peerMailMan, String username ) throws Exception {
        this.id = idCounter++ ;
        // Update this peers FOLLOWINGS
        this.updateFollowings(bufferedReader,socketPort, peerMailMan);
        this.socketPort = socketPort ;
        this.username = username ;
        Peer.allPeers.add(this);

    }


    public String getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(String socketPort) {
        this.socketPort = socketPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static ArrayList<Peer> getAllPeers() {
        return allPeers;
    }

    public static void setAllPeers(ArrayList<Peer> allPeers) {
        Peer.allPeers = allPeers;
    }

    public ArrayList<Peer> getFollowingsPeers() {
        return followingsPeers;
    }

    public void setFollowingsPeers(ArrayList<Peer> followingsPeers) {
        this.followingsPeers = followingsPeers;
    }
}
