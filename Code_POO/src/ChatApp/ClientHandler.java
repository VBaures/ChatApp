package ChatApp;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{
    NetworkHandler networkHandler;
    ChatHandler chatHandler;

    public ClientHandler(NetworkHandler networkHandler, ChatHandler chatHandler) throws IOException {
        this.networkHandler = networkHandler;
        this.chatHandler = chatHandler;
    }

    public void run() {
            try {
                //Scanner keyboard = new Scanner(System.in);
                //System.out.println("Enter client port");

                Socket link = new Socket(InetAddress.getByName("localhost"), chatHandler.getRecipient().getServerPort(), InetAddress.getByName("localhost"), networkHandler.getAgent().getPseudoHandler().getMain_User().getClientPort());

                ObjectOutputStream out = new ObjectOutputStream(link.getOutputStream());

                ObjectInputStream in = new ObjectInputStream(link.getInputStream());
                networkHandler.getAgent().findChatHandler(chatHandler.getRecipient().getID()).setOutput(out);
                networkHandler.getAgent().findChatHandler(chatHandler.getRecipient().getID()).setSocket(link);
                System.out.println(chatHandler.getMessageHistory());
                while (true) {
                    try {
                        StringMessage receive = (StringMessage) in.readObject();
                        chatHandler.setOutput(out);
                        chatHandler.setSocket(link);
                        chatHandler.getMessageHistory().add(receive);
                        chatHandler.getChatPage().Mise_a_jour();
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
