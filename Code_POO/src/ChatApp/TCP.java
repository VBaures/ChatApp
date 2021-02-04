package ChatApp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCP extends Thread {
    ServerHandler serverHandler;
    Socket link;


    public TCP(ServerHandler serverHandler, Socket link){
        this.serverHandler=serverHandler;
        this.link=link;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream outTCP = new ObjectOutputStream(link.getOutputStream());
            ObjectInputStream inTCP = new ObjectInputStream(link.getInputStream());
            User sender = serverHandler.getNetworkHandler().getAgent().getPseudoHandler().FindUserByIP(link.getInetAddress().getHostAddress());
            System.out.println("Sender is: " + link.getInetAddress().toString());
            serverHandler.getNetworkHandler().getAgent().getCurrentChat().add(new ChatHandler(sender, outTCP, link, serverHandler.getNetworkHandler().getAgent()));
            while(true){
                Object ObjectReceive=inTCP.readObject();
                if (ObjectReceive instanceof StringMessage) {
                    StringMessage receive = (StringMessage) ObjectReceive;
                    System.out.println("Message reçu :"+ receive.getContentString());
                    serverHandler.getNetworkHandler().getAgent().findChatHandler(receive.getSender().getID()).getMessageHistory().add(receive);
                    serverHandler.getNetworkHandler().getAgent().findChatHandler(receive.getSender().getID()).getChatPage().Mise_a_jour();
                } else if (ObjectReceive instanceof FileMessage) {
                    FileMessage receive = (FileMessage) ObjectReceive;
                    System.out.println("Message avec file reçu");
                    serverHandler.getNetworkHandler().getAgent().findChatHandler(receive.getSender().getID()).getMessageHistory().add(receive);
                    serverHandler.getNetworkHandler().getAgent().findChatHandler(receive.getSender().getID()).getChatPage().Mise_a_jour();

                } else if (ObjectReceive instanceof String) {
                    String receive = (String) ObjectReceive;
                    if ((receive.equals("StopChat"))&(serverHandler.getNetworkHandler().getAgent().findChatHandler(sender.getID())!=null)){
                        serverHandler.getNetworkHandler().getAgent().findChatHandler(sender.getID()).getChatPage().getFram().dispose();
                        serverHandler.getNetworkHandler().getAgent().getCurrentChat().remove(serverHandler.getNetworkHandler().getAgent().findChatHandler(sender.getID()));
                        break;
                    }
                }
            }
            link.close();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Problème reception ! ");
        }
    }
}
