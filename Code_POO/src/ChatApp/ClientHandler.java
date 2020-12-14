package ChatApp;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{
    NetworkHandler networkHandler;
    int port;

    public ClientHandler(NetworkHandler networkHandler, int port) throws IOException {
        this.networkHandler = networkHandler;
        this.port = port;
    }

    public void run() {
            try {
                //Scanner keyboard = new Scanner(System.in);
                //System.out.println("Enter client port");

                Socket link = new Socket(InetAddress.getByName("localhost"), this.port, InetAddress.getByName("localhost"), networkHandler.getAgent().getPseudoHandler().getMain_User().getClientPort());

                ObjectOutputStream out = new ObjectOutputStream(link.getOutputStream());

                networkHandler.getAgent().getCurrentChat().get(networkHandler.getAgent().getCurrentChat().size()-1).setOutput(out);
                networkHandler.getAgent().getCurrentChat().get(networkHandler.getAgent().getCurrentChat().size()-1).setSocket(link);
                ObjectInputStream in = new ObjectInputStream(link.getInputStream());
                ;
                while (true) {
                    try {

                        StringMessage receive = (StringMessage) in.readObject();
                        networkHandler.getAgent().findChatHandler(receive.getSender().getPseudo()).setOutput(out);
                        networkHandler.getAgent().findChatHandler(receive.getSender().getPseudo()).setSocket(link);
                        networkHandler.getAgent().findChatHandler(receive.getSender().getPseudo()).getMessageHistory().add(receive);
                        networkHandler.getAgent().findChatHandler(receive.getSender().getPseudo()).getChatPage().Mise_a_jour();
                    } catch (IOException e) {
                        System.err.println("Problème envoie ! ");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                } catch(IOException e){
                    System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
                }

    }
}
