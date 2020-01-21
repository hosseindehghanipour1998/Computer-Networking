package peertopeer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//this class is responsible to get the messages that are sent to it's boss server
public class MailManWorkers extends Thread {
    private PeerMailMan myBoss;
    private Socket socket;
    private PrintWriter printWriter;


    public MailManWorkers(Socket socket, PeerMailMan myBoss){
        this.myBoss = myBoss;
        this.socket = socket;
        myBoss.getAllServerThreads().add(this);
    }


    @Override
    public void run() {
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(),true);

            while (true) {
                String sendingMsg = bufferedReader.readLine() ;
                String tempUserName , tempMsg ;
                int tempId ;
                String[] msgParts = sendingMsg.split("|");
                tempId = Integer.valueOf(msgParts[0]);
                tempUserName = msgParts[1] ;
                tempMsg = msgParts[2] ;
                Message newMessage = new Message(tempMsg , tempUserName , tempId) ;
                myBoss.sendMessage(newMessage);
            }
        }catch (Exception e){
            myBoss.getAllServerThreads().remove(this);
            e.printStackTrace();
            System.out.println("In Server-Thread-Thread Exception");
        }
    }

    public PrintWriter getPrintWriter(){return printWriter;}
}
