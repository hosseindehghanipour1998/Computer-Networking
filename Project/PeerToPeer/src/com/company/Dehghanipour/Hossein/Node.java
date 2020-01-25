package com.company.Dehghanipour.Hossein;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Node {
    public static ArrayList<Node> allNodes = new ArrayList<>();
    private static int idCounter = 0;
    private int nodeId;
    private String username;
    public  ArrayList<Node> followings = new ArrayList<>();
    public static ArrayList<Message> sendMessages = new ArrayList<>() ;
    public static ArrayList<Message> receivedMessages = new ArrayList<>();

    private String IPAddress = "localhost";
    private DataInputStream input = null;
    private DataOutputStream out = null;
    private Server server;
    private String portNo ;


    public Node(String username, String portNo) throws IOException {
        this.portNo = portNo ;
        this.username = username;
        this.nodeId = idCounter++;
        System.out.println(portNo);
        this.server = new Server(portNo);
        allNodes.add(this);
    }

    public static Node createNode(String username, String portNo) throws IOException {
        // Input Reader Like Scanner :
        Node n = null;
        try{

            n = new Node(username, portNo);
            System.out.println("created");
            //n.updateFollowings(bufferedReader);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return n;
    }

    public void printMsg(String msg) {
        System.out.println(msg);
    }

    public  void updateFollowings(String followings) throws Exception {
        //System.out.println("> enter (space separated) hostname:port#");
        //System.out.println(" peers to receive messages from (s to skip)");
        //String input = bufferedReader.readLine();
        String[] inputValues = followings.split(" ");

        //if (input.equals("s") == false){
            //  s stands for Skip -> if we had Skip it means there are followings for this peer.
            for (int i = 0; i < inputValues.length; i++) {
                String[] address = inputValues[i].split(":");
                Socket socket = null;

                try {
                    socket = new Socket(address[0], Integer.valueOf(address[1]));
                    ListenerThread newThread = new ListenerThread(socket, this);

                }
                catch(Exception e){
                    if (socket != null) socket.close(); // if the wanted following did not exist.
                    else System.out.println("invalid input , skipping to next step!");
                }
            }
        //}
        System.out.println("u followed sb");

        //communicate(bufferedReader,username);
    }

    public void printMessages(ArrayList<Message> al){
        for(Message a : al){
            System.out.println(a.getMessage());
        }
    }

    public void communicate(String message , String username){
        try{
            //System.out.println("> you can now communicate (e to exit , c to change , q to query)");
            //boolean connected = true;
            //while (true){
                //String message = bufferedReader.readLine();
                //if (message.equals("e") == true ){
                    // when we want to get disconnected.
                    //connected = false ;
                    //break;
                //}
                //if(message.equals("c")){
                //    updateFollowings("jjj");
               // }
                //else if ( message.equals("q") == true){
                //    System.out.println("Search the Word: ");
                  //  message = bufferedReader.readLine();
                  //  printMessages(ListenerThread.query(message));

               // }

                //else{
                    Message newMessageToSend = new Message(message,username) ;
                    server.sendMessage(newMessageToSend);

               // }
            //}
            //System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("In Peer Exception");
        }
    }

    public static ArrayList<Node> getAllNodes() {
        return allNodes;
    }

    public static void setAllNodes(ArrayList<Node> allNodes) {
        Node.allNodes = allNodes;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        Node.idCounter = idCounter;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public ArrayList<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(ArrayList<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public DataInputStream getInput() {
        return input;
    }

    public void setInput(DataInputStream input) {
        this.input = input;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }


}
