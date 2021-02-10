package ChatApp;

import java.io.*;
import java.net.*;

public class ServerHandler extends Thread {
    protected NetworkHandler networkHandler;
    protected int port=1040;
    protected UDP udp;
    protected TCP tcp;


    public ServerHandler(NetworkHandler networkHandler) {
        this.networkHandler=networkHandler;
    }

    ;

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

    public UDP getUdp() {
        return udp;
    }

    public NetworkHandler getNetworkHandler(){
        return this.networkHandler;
    }
}