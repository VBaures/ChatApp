/*
This class handle a UDP server connection to broadcast information or notify connection status to local users

@author Vincent Baures
@date 2021-02-13
*/

package ChatApp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UDP extends Thread {
    ServerHandler serverHandler;
    DatagramSocket datagramSocket;

/*===========CONSTRUCTOR==========*/
    public UDP(ServerHandler serverHandler, DatagramSocket datagramSocket) {
        this.serverHandler = serverHandler;
        this.datagramSocket = datagramSocket;
    }

/*===========RUN METHOD==========*/
    @Override
    public void run() {
        try {
            broadcastUDP("Connection", serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User());
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
                Object receive2 = is.readObject();
                System.out.println("ok4");
                if (receive1.trim().equals("Connection")) {
                    System.out.println("Reception Connection de"+dataReceive2.getAddress());
                    if (dataReceive2.equals(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User().getAddr_Ip())==false) {
                        sendUDP("RetourConnection", serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User(), dataReceive2.getAddress());
                        System.out.println("Envoie retour connection");
                    }
                } else if (receive1.trim().equals("RetourConnection")) {
                    serverHandler.getNetworkHandler().getAgent().UpdateUsers(receive2);
                    System.out.println("Ajout via local");
                }else if (receive1.trim().equals("Disconnect")) {
                    System.out.println("Deconnexion");
                    serverHandler.getNetworkHandler().getAgent().RemoveUser(receive2);
                } else if (receive1.trim().equals("NewPseudo")){
                    serverHandler.getNetworkHandler().getAgent().UpdateUsers(receive2);
                    System.out.println("Ajout ou modif via local");
                }
                System.out.println("Liste connected user");
                for (int i = 0; i < serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers().size(); i++) {
                    System.out.println(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getConnectedUsers());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Problème reception ! ");
            e.printStackTrace();
        }
    }

/*This function send two udp message a header and a content*/
    private void sendUDP(String message, Object obj, InetAddress inetAddress) throws IOException {
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
/*This function broadcast two udp message a header and a content*/
    public void broadcastUDP(String message, Object obj) throws IOException {
        sendUDP(message, obj, getBroadcastAddress());
        System.out.println("broadcast ok");
    }

/*This find the broacast adress of the local network*/
    private InetAddress getBroadcastAddress() throws SocketException {
        InetAddress ip = serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User().getAddr_Ip();
        System.out.println("broadcast 5");
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);
        System.out.println("broadcast 2");
        List<InterfaceAddress> list = networkInterface.getInterfaceAddresses();
        System.out.println("broadcast 1");
        for (int i=0; i<list.size();i++){
            if (list.get(i).getAddress().equals(ip)){
                System.out.println("Broadcast =" + list.get(i).getBroadcast());
                return list.get(i).getBroadcast();
            }
        }
        return null;
    }

}

