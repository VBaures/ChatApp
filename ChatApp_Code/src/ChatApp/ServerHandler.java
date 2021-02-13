/*
This class handle creation of UDP connection and TCP connection server side

@author Vincent Baures
@date 2021-02-13
*/

package ChatApp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler extends Thread {
    private NetworkHandler networkHandler;
    private int port=1040;
    private UDP udp;
    private TCP tcp;

/*==========CONSTRUCTOR==========*/
    public ServerHandler(NetworkHandler networkHandler) {
        this.networkHandler=networkHandler;
    }

/*==========RUN METHOD==========*/
    public void run() {
        try {
            System.out.println("Démarrage server");
            ServerSocket servSocketTCP = new ServerSocket(this.port);
            System.out.println("Addresse du TCP "+servSocketTCP);
            DatagramSocket datagramSocket = new DatagramSocket(this.port);
            this.udp= new UDP(this, datagramSocket);
            udp.start();
            while (true) {
                Socket linkTCP = servSocketTCP.accept();
                linkTCP.setKeepAlive(false);
                this.tcp = new TCP(this, linkTCP);
                tcp.start();
            }
        } catch (IOException e) {
            System.err.println("Le server est déjà utilisé ! ");
        }
    }

/*=========GETTERS AND SETTERS==========*/
    public UDP getUdp() { return udp; }
    public NetworkHandler getNetworkHandler(){ return this.networkHandler; }
}