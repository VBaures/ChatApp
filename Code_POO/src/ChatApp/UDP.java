package ChatApp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UDP extends Thread {
        ServerHandler serverHandler;
        DatagramSocket datagramSocket;


        public UDP(ServerHandler serverHandler, DatagramSocket datagramSocket) {
            this.serverHandler = serverHandler;
            this.datagramSocket = datagramSocket;

        }

        @Override
        public void run() {
            try {
                broadcastUDP("Connection",serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User());
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
                        //synchronized (this) {
                            sendUDP("RetourConnection", serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User(), receive2.getAddr_Ip());
                            serverHandler.getNetworkHandler().getAgent().getUsersWindows().jListSimple.Mise_a_jour(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());
                        //}
                    } else if (receive1.trim().equals("RetourConnection")) {
                        //synchronized (this) {
                        serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers().add(receive2);
                        System.out.println("Reception retour");
                        serverHandler.getNetworkHandler().getAgent().getUsersWindows().jListSimple.Mise_a_jour(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());
                        //}
                    }else if (receive1.trim().equals("Disconnect")) {
                        System.out.println("Deconnexion de: "+receive2.getPseudo());
                        serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers().remove(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().FindUser(receive2.getID()));
                        serverHandler.getNetworkHandler().getAgent().getUsersWindows().jListSimple.Mise_a_jour(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());

                    } else if (receive1.trim().equals("NewPseudo")){
                        User user = serverHandler.getNetworkHandler().getAgent().getPseudoHandler().FindUser(receive2.getID());
                        if (user==null){
                            serverHandler.getNetworkHandler().getAgent().getPseudoHandler().UpdateConnectedUsers(receive2);
                        } else {
                            serverHandler.getNetworkHandler().getAgent().UpdatePseudo(receive2.getPseudo(), user.getID());
                        }
                        serverHandler.getNetworkHandler().getAgent().getUsersWindows().jListSimple.Mise_a_jour(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());
                    }
                    System.out.println("Liste connected user");
                    for (int i = 0; i < serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers().size(); i++) {
                        System.out.println(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers().get(i).getPseudo());
                    }
                    //serverHandler.getNetworkHandler().getAgent().getAffichage().Mise_a_jour(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());

                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Problème reception ! ");
            }
        }

    void sendUDP(String message, Object obj, InetAddress inetAddress) throws IOException {
        byte[] buffer1 = message.getBytes();
        DatagramPacket packet1 = new DatagramPacket(buffer1, buffer1.length, inetAddress, 1050);
        System.out.println(inetAddress);
        datagramSocket.send(packet1);
        System.out.println("Packet 1 envoyé");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        byte[] buffer2 = bos.toByteArray();
        DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, inetAddress, 1050);
        datagramSocket.send(packet2);
        System.out.println("Packet 2 envoyé " + packet1.getLength());
    }

    void broadcastUDP(String message, Object obj) throws IOException {
        InetAddress ip = serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User().getAddr_Ip();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);
        List<InterfaceAddress> list = networkInterface.getInterfaceAddresses();
        sendUDP(message, obj, list.get(0).getBroadcast());
        System.out.println("broadcast ok");
    }
    }

