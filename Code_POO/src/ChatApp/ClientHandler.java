package ChatApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {

    public static synchronized void StartChat() {
        new Thread(() -> {
            try {
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Enter client port");
                Socket link = new Socket("localhost", keyboard.nextInt());
                ObjectOutputStream out = new ObjectOutputStream(link.getOutputStream());
                while (true) {
                    try {
                        Scanner clavier = new Scanner(System.in);
                        String envoie = clavier.nextLine();
                        out.writeObject(envoie);
                        if (envoie == null) {
                            link.close();
                        }
                    } catch (IOException e) {
                        System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
                    }
                }
            } catch (IOException e) {
                System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
            }
        }).start();
    }
}
