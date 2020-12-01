package ChatApp;

import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler extends Thread {
    protected int port;
    protected NetworkHandler networkHandler;
    ObjectOutputStream out;

    public ServerHandler(NetworkHandler networkHandler, int port) {
        this.port=port;
        this.networkHandler=networkHandler;
    }

    ;

    public void run() {
        try {
            ServerSocket servSocket = new ServerSocket(this.port);
            System.out.println("TCP Server créé");
            while (true) {
                Socket link = servSocket.accept();
                System.out.println("TCP Client connecté");
                ObjectOutputStream out = new ObjectOutputStream(link.getOutputStream());
                networkHandler.getAgent().getCurrentChat().add(new ChatHandler(out));

                new Thread(() ->{
                    ReceptionWaiting(link);
                }).start();
            }
        } catch (IOException e) {
            System.err.println("Le server est déjà utlisé ! ");
        }
    }

    public void ReceptionWaiting(Socket link) {
        try {
            System.out.println("TCP Server écoute");
            ObjectInputStream in = new ObjectInputStream(link.getInputStream());
            while (true) {
                Object ObjectReceive = in.readObject();
                if (ObjectReceive instanceof StringMessage) {
                    StringMessage receive = (StringMessage) ObjectReceive;
                    System.out.println("Un message vient d'être reçu");
                    System.out.println("Content: ");
                    System.out.println(receive.getContent());
                } else if (ObjectReceive instanceof MainUser) {
                    MainUser receive = (MainUser) ObjectReceive;
                    System.out.println("Les informations d'un utilisateur viennent d'être reçu");
                    System.out.println("Pseudo: " + receive.pseudo);
                    System.out.println();
                } else if (ObjectReceive instanceof String) {
                    String receive = (String) ObjectReceive;
                    System.out.println(receive);
                    if (receive == "Stop") {
                        link.close();
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Problème reception ! ");
        }
    }

    public void Send(Object object, ChatHandler chatHandler) {
        try {
            chatHandler.output.writeObject(object);
        } catch (IOException e) {
        }
    }
}