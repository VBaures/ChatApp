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
                    System.out.println("ok1");
                    String receive1 = new String(dataReceive1.getData(), StandardCharsets.UTF_8);
                    DatagramPacket dataReceive2 = new DatagramPacket(new byte[1024], 1024);
                    datagramSocket.receive(dataReceive2);
                    System.out.println("ok2");
                    ByteArrayInputStream in = new ByteArrayInputStream(dataReceive2.getData());
                    ObjectInputStream is = new ObjectInputStream(in);
                    System.out.println("ok3");
                    User receive2 = (User) is.readObject();
                    receive2.setPlace("local");
                    System.out.println("ok4");
                    if (receive1.trim().equals("Connection")) {
                        if (receive2.getID()!=serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User().getID()) {
                            sendUDP("RetourConnection", serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User(), receive2.getAddr_Ip());
                            System.out.println("Envoie retour connection");
                        }
                    } else if (receive1.trim().equals("RetourConnection")) {
                        //synchronized (this) {
                        System.out.println("Reception retour connection");
                        serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers().add(receive2);
                        System.out.println("Reception retour");
                        if (receive2.getPseudo().equals("notdefine")!=true){
                            serverHandler.getNetworkHandler().getAgent().getUsersWindows().jListSimple.Mise_a_jour(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());
                        }
                    }else if (receive1.trim().equals("Disconnect")) {
                        System.out.println("Deconnexion de: "+receive2.getPseudo());
                        serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers().remove(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().FindUser(receive2.getID()));
                        serverHandler.getNetworkHandler().getAgent().getUsersWindows().jListSimple.Mise_a_jour(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());

                    } else if (receive1.trim().equals("NewPseudo")){
                        if (receive2.getID()!=serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User().getID()) {
                            User user = serverHandler.getNetworkHandler().getAgent().getPseudoHandler().FindUser(receive2.getID());
                            if (user!=null) {
                                System.out.println("Modification pseudo: " + user);
                                serverHandler.getNetworkHandler().getAgent().UpdatePseudo(receive2.getPseudo(), user.getID());
                                serverHandler.getNetworkHandler().getAgent().getUsersWindows().jListSimple.Mise_a_jour(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());
                            }else{
                                serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers().add(receive2);
                                System.out.println("Ajout via local de "+receive2.getID());
                                serverHandler.getNetworkHandler().getAgent().getUsersWindows().jListSimple.Mise_a_jour(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());
                            }
                        }
                    }
                    System.out.println("Liste connected user");
                    for (int i = 0; i < serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers().size(); i++) {
                        System.out.println(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());
                    }
                    //serverHandler.getNetworkHandler().getAgent().getAffichage().Mise_a_jour(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());

                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Problème reception ! ");
            }
        }

    void sendUDP(String message, Object obj, InetAddress inetAddress) throws IOException {
        byte[] buffer1 = message.getBytes();
        DatagramPacket packet1 = new DatagramPacket(buffer1, buffer1.length, inetAddress, 1040);
        System.out.println(inetAddress);
        datagramSocket.send(packet1);
        System.out.println("Packet 1 envoyé");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        byte[] buffer2 = bos.toByteArray();
        DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, inetAddress, 1040);
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

