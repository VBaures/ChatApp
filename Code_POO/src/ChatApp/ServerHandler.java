package ChatApp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler {
    int port;

    void StartServer() {
        try {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter server port");
            this.port=keyboard.nextInt();
            ServerSocket servSocket = new ServerSocket(this.port);
            while (true) {
                Socket link = servSocket.accept();
                new Thread(() -> {
                    try {
                        ObjectInputStream in = new ObjectInputStream(link.getInputStream());
                        while (true) {
                            Object ObjectReceive = in.readObject();
                            if (ObjectReceive istanceof Message)
                            String message = ObjectReceive.toString();
                            System.out.println(message);
                            if (message=="Stop") {
                                link.close();
                            }                                                     }

                    } catch (IOException | ClassNotFoundException e) {
                        System.err.println("Le server est déjà utlisé 2 ! ");
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("Le server est déjà utlisé 2 ! ");
        }
    }

}
