package ChatApp;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static User user;
    static MainUser mainUser;
    public static void main(String[] args) throws IOException, InterruptedException {

        Agent agent = new Agent();
        /*while(true) {
            System.out.println("Avec qui voulez-vous communiquer?");
            String destinataire = keyboard.nextLine();
            ChatHandler chatHandler = agent.findChatHandler(destinataire);
            if (chatHandler == null) {
                agent.StartChat(destinataire);
            }
            System.out.println("Quel message voulez-vous envoyer?");
            String content = keyboard.nextLine();
            StringMessage message = new StringMessage(agent.findChatHandler(destinataire).getRecipient(), agent.getPseudoHandler().getMain_User(), content);
            agent.findChatHandler(destinataire).Send(message);
        }*/
    }
}
