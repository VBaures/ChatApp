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
    private ServerHandler serverHandler;
    private DatagramSocket datagramSocket;

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
                String receive1 = new String(dataReceive1.getData(), StandardCharsets.UTF_8);
                DatagramPacket dataReceive2 = new DatagramPacket(new byte[1024], 1024);
                datagramSocket.receive(dataReceive2);
                ByteArrayInputStream in = new ByteArrayInputStream(dataReceive2.getData());
                ObjectInputStream is = new ObjectInputStream(in);
                Object receive2 = is.readObject();
                if (receive1.trim().equals("Connection")) {
                    if (dataReceive2.equals(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User().getAddr_Ip())==false) {
                        sendUDP("RetourConnection", serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User(), dataReceive2.getAddress());
                    }
                } else if (receive1.trim().equals("RetourConnection")) {
                    serverHandler.getNetworkHandler().getAgent().UpdateUsers(receive2);
                }else if (receive1.trim().equals("Disconnect")) {
                    serverHandler.getNetworkHandler().getAgent().RemoveUser(receive2);
                } else if (receive1.trim().equals("NewPseudo")){
                    serverHandler.getNetworkHandler().getAgent().UpdateUsers(receive2);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
        }
    }

/*This function send two udp message a header and a content*/
    private void sendUDP(String message, Object obj, InetAddress inetAddress) throws IOException {
        byte[] buffer1 = message.getBytes();
        DatagramPacket packet1 = new DatagramPacket(buffer1, buffer1.length, inetAddress, 1040);
        datagramSocket.send(packet1);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        byte[] buffer2 = bos.toByteArray();
        DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, inetAddress, 1040);
        datagramSocket.send(packet2);
    }
/*This function broadcast two udp message a header and a content*/
    public void broadcastUDP(String message, Object obj) throws IOException {
        sendUDP(message, obj, getBroadcastAddress());
    }

/*This find the broacast adress of the local network*/
    private InetAddress getBroadcastAddress() throws SocketException {
        InetAddress ip = serverHandler.getNetworkHandler().getAgent().getPseudoHandler().getMain_User().getAddr_Ip();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);
        List<InterfaceAddress> list = networkInterface.getInterfaceAddresses();
        for (int i=0; i<list.size();i++){
            if (list.get(i).getAddress().equals(ip)){
                return list.get(i).getBroadcast();
            }
        }
        return null;
    }

}

