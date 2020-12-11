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
        new Thread(() -> {
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
                        System.out.println("Un message vient d'être reçu");
                        System.out.println("From: "+receive.getSender().getUserName());
                        System.out.println("Content: "+receive.getContent());
                    } catch (IOException e) {
                        System.err.println("Problème envoie ! ");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                } catch(IOException e){
                    System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
                }
        }).start();
    }
}
