package ChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {

    public void run(){
        StartChat(); // c'est la fonction d'extraction qui est dans une autre classe
    }

    private static synchronized void StartChat() {
        new Thread(() -> {
            try {
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Enter client port");
                Socket link = new Socket("localhost", keyboard.nextInt());
                System.out.println("ok");
                BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                PrintWriter out = new PrintWriter(link.getOutputStream(), true);
                System.out.println(in.readLine());
                Scanner clavier = new Scanner(System.in);
                String envoie = clavier.nextLine();
                out.println(envoie);
                link.close();
            } catch (IOException e) {
                System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
            }
        }).start();
    }
}
