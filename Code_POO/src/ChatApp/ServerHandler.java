package ChatApp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerHandler extends Thread {
    protected int port;
    protected NetworkHandler networkHandler;
    protected ServerSocket servSocketTCP;
    protected DatagramSocket datagramSocket;
    protected UDP udp;
    protected TCP tcp;


    public ServerHandler(NetworkHandler networkHandler, int port) {
        this.port=port;
        this.networkHandler=networkHandler;
    }

    ;

    public void run() {
        try {
            System.out.println("Démarrage server");
            this.servSocketTCP = new ServerSocket(this.port);
            System.out.println("TCP server ok");
            this.datagramSocket = new DatagramSocket(this.port);
            System.out.println("TCP server ok 1");
            this.udp = new UDP(this, datagramSocket);
            System.out.println("TCP server ok 2");
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