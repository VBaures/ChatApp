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
            serverHandler.getNetworkHandler().getAgent().getCurrentChat().add(new ChatHandler(serverHandler.getNetworkHandler().getAgent().getPseudoHandler().FindUser(link.getPort()), outTCP, link, serverHandler.getNetworkHandler().getAgent()));
            while (true) {
                Object ObjectReceive = inTCP.readObject();
                if (ObjectReceive instanceof StringMessage) {
                    StringMessage receive = (StringMessage) ObjectReceive;
                    System.out.println("Message reçu :"+ receive.getContent());
                    serverHandler.getNetworkHandler().getAgent().findChatHandler(receive.getSender().getID()).getMessageHistory().add(receive);
                    serverHandler.getNetworkHandler().getAgent().findChatHandler(receive.getSender().getID()).getChatPage().Mise_a_jour();
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
}
