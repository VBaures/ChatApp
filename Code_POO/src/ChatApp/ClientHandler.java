package ChatApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{
    NetworkHandler networkHandler;
    int port;

    public ClientHandler(NetworkHandler networkHandler, int port) throws IOException {
        this.networkHandler = networkHandler;
        this.port = port;
        System.out.println("Port aprés initialisation du Clien Handler: "+this.port);
    }

    public void run() {
        new Thread(() -> {
            try {
                //Scanner keyboard = new Scanner(System.in);
                //System.out.println("Enter client port");
                System.out.println("TCP Client thread démarre");
                System.out.println("Creation socket par client vers port" + this.port);
                Socket link = new Socket("localhost", this.port);
                System.out.print("Socket créé");
                ObjectOutputStream out = new ObjectOutputStream(link.getOutputStream());
                System.out.print("TCP Client créé");
                networkHandler.getAgent().getCurrentChat().get(networkHandler.getAgent().getCurrentChat().size()-1).setOutput(out);
                ObjectInputStream in = new ObjectInputStream(link.getInputStream());
                System.out.print("TCP Client écoute");
                while (true) {
                    try {
                        Object receive = (String) in.readObject();
                        System.out.println(receive);
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
