package ChatApp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerHandler extends Thread {
    protected int port;
    protected NetworkHandler networkHandler;
    protected DatagramSocket datagramSocket;

    public ServerHandler(NetworkHandler networkHandler, int port) {
        this.port=port;
        this.networkHandler=networkHandler;
    }

    ;

    public void run() {
        try {
            ServerSocket servSocketTCP = new ServerSocket(this.port);
            datagramSocket = new DatagramSocket(this.port);
            while (true) {
                new Thread(()->{
                    try {
                        UDPReceptionWaiting();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }).start();
                Socket linkTCP = servSocketTCP.accept();
                ObjectOutputStream outTCP = new ObjectOutputStream(linkTCP.getOutputStream());
                networkHandler.getAgent().getCurrentChat().add(new ChatHandler(networkHandler.getAgent().getPseudoHandler().getConnectedUsers().get(0),outTCP));
                new Thread(() ->{
                    TCPReceptionWaiting(linkTCP);
                }).start();
            }
        } catch (IOException e) {
            System.err.println("Le server est déjà utIlisé ! ");
        }
    }

    public void TCPReceptionWaiting(Socket link) {
        try {
            ObjectInputStream inTCP = new ObjectInputStream(link.getInputStream());
            while (true) {
                Object ObjectReceive = inTCP.readObject();
                if (ObjectReceive instanceof StringMessage) {
                    StringMessage receive = (StringMessage) ObjectReceive;
                    System.out.println("Un message vient d'être reçu");
                    System.out.println("From: "+receive.getSender().getUserName());
                    System.out.println("Content: "+receive.getContent());
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

    void UDPReceptionWaiting() throws IOException, ClassNotFoundException {
        while (true) {
            DatagramPacket dataReceive1 = new DatagramPacket(new byte[1024], 1024);
            datagramSocket.receive(dataReceive1);
            String receive1 = new String(dataReceive1.getData(), StandardCharsets.UTF_8);
            DatagramPacket dataReceive2 = new DatagramPacket(new byte[1024], 1024);
            datagramSocket.receive(dataReceive2);
            ByteArrayInputStream in = new ByteArrayInputStream(dataReceive2.getData());
            ObjectInputStream is = new ObjectInputStream(in);
            User receive2 = (User) is.readObject();
            if (receive1.trim().equals("Connection")) {
                networkHandler.getAgent().getPseudoHandler().getConnectedUsers().add(receive2);
                sendUDP("RetourConnection", networkHandler.getAgent().getPseudoHandler().getMain_User(), receive2.getPort());
            } else if (receive1.trim().equals("RetourConnection")) {
                networkHandler.getAgent().getPseudoHandler().getConnectedUsers().add(receive2);
            }
            System.out.println("Liste connected user");
            for (int i = 0; i < networkHandler.getAgent().getPseudoHandler().getConnectedUsers().size(); i++) {
                System.out.println(networkHandler.getAgent().getPseudoHandler().getConnectedUsers().get(i).getUserName());
            }
        }
    }

    void sendUDP(String message, Object obj, int port) throws IOException {
        byte[] buffer1 = message.getBytes();
        DatagramPacket packet1 = new DatagramPacket(buffer1, buffer1.length, InetAddress.getByName("localhost") , port);
        datagramSocket.send(packet1);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        byte[] buffer2 = bos.toByteArray();
        DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, InetAddress.getByName("localhost") , port);
        datagramSocket.send(packet2);
    }

    void broadcastUDP(String message, Object obj) throws IOException {
        for (int port = 1234; port <= 1237; port++) {
            if (port!=networkHandler.getAgent().getPseudoHandler().getMain_User().getPort()) {
                sendUDP(message, obj, port);
            }
        }
    }
}