package ChatApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler extends Thread {
    public void run(){
        Connect(); // c'est la fonction d'extraction qui est dans une autre classe
    }

    private static void Connect() {
        try {
            
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter server port");
            ServerSocket servSocket= new ServerSocket(keyboard.nextInt());
            Socket link = servSocket.accept();
            while(true){
            NetworkListener r1 = new NetworkListener(link);
            Thread t1 = new Thread(r1);
            t1.start();}


        }catch(IOException e){
            System.err.println("Le server est déjà utlisé ! ");
        }
    }
}
