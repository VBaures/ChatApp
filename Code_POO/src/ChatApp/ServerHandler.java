package ChatApp;

import java.io.IOError;
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
<<<<<<< HEAD
            this.port=keyboard.nextInt();
            ServerSocket servSocket = new ServerSocket(this.port);
            while (true) {
                Socket link = servSocket.accept();
                new Thread(() -> {
                    try {
                        ObjectInputStream in = new ObjectInputStream(link.getInputStream());
                        while (true) {
                            Object ObjectReceive = in.readObject();
                            if (ObjectReceive instanceof Message){
                                Message receive = (Message) ObjectReceive;
                            }else if (ObjectReceive instanceof User){
                                User receive = (User) ObjectReceive;
                            }else if (ObjectReceive instanceof String){
                                String receive = (String) ObjectReceive;
                                if (receive=="Stop") {
                                    link.close();
                                }else{
                                System.out.println(receive);
                            }
                            }
                        }
=======
            ServerSocket servSocket= new ServerSocket(keyboard.nextInt());
            Socket link = servSocket.accept();
            while(true){
            NetworkListener r1 = new NetworkListener(link);
            Thread t1 = new Thread(r1);
            t1.start();}

>>>>>>> parent of 63856b5... Merge branch 'master' of https://github.com/VBaures/Projet-COO

        }catch(IOException e){
            System.err.println("Le server est déjà utlisé ! ");
        }
    }
}
