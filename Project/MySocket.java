package sample;


import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class MySocket {
    private Socket door;
    private OutputStream OutToServer;
    private DataOutputStream out;
    private InputStream InFromServer;
    private DataInputStream in;

    public MySocket() {
        String serverName = "127.0.0.1";
        int port = 54000;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            door = new Socket(serverName, port);

            System.out.println("Just connected to " + door.getRemoteSocketAddress());

            OutToServer = door.getOutputStream();

            out = new DataOutputStream(OutToServer);

            InFromServer = door.getInputStream();

            in = new DataInputStream(InFromServer);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void send(String message) {
        try {
            out.write(message.getBytes(StandardCharsets.US_ASCII));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String recv() {
        try {
            byte[] arr = new byte[50];

            int a = in.read(arr);

            String st = new String(arr, 0, a);

            return st;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void close() {
        try {
            door.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
