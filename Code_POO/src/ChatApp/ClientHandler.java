package ChatApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{
    int port;
    Agent agent;

    public ClientHandler(int port, throws IOException {
        this.port = port;
    }

    public void run() {
        new Thread(() -> {
            try {
                //Scanner keyboard = new Scanner(System.in);
                //System.out.println("Enter client port");
                Socket link = new Socket("localhost", this.port);
                ObjectOutputStream out = new ObjectOutputStream(link.getOutputStream());
                agent.getCurrentChats().add(new ChatHandler(out));
                ObjectInputStream in = new ObjectInputStream(link.getInputStream());
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
