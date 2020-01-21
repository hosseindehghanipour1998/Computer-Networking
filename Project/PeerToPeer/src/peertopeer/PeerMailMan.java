package peertopeer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class PeerMailMan extends Thread  {
    private ServerSocket serverSocket;
    public Set<MailManWorkers> allHiredWorkers = new HashSet<MailManWorkers>();

    //Constructor :
    public PeerMailMan(String peerPortNo) throws IOException{
        // set the thread to  listen on a specific port.
        serverSocket = new ServerSocket(Integer.valueOf(peerPortNo));
    }

    @Override
    public void run() {
        try{
            while (true){
                // accept everything that is being sent to this server :
                MailManWorkers mailManWorkers = new MailManWorkers(serverSocket.accept() , this);// server adds a thread to sniff in that peer.
                mailManWorkers.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(Message sendingMessage){
        try {
            //serverThreadThreads.forEach(t->t.getPrintWriter().println(message));
            for(MailManWorkers stt: allHiredWorkers){
                stt.getPrintWriter().println(sendingMessage.printMessage());
            }
        }catch (Exception e){e.printStackTrace();
            System.out.println("In ServerThread Excpetion");}
    }

    public Set<MailManWorkers> getAllServerThreads() {
        return allHiredWorkers;
    }
}
