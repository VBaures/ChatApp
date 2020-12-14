package ChatApp;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static User user;
    static MainUser mainUser;
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Quel agent veux-tu créer?");
        Scanner keyboard = new Scanner(System.in);
        String people = keyboard.nextLine();
        if (people.equals("Alicia")) {
            //Creation d'un main user
            mainUser = new MainUser("Alicia", "alic02", "localhost", 1235,1245, 2);
            //user = new User("Lucas", "luc01", "localhost", 1234, 1);
        }else if (people.equals("Lucas")) {
            mainUser = new MainUser("Lucas", "luc01", "localhost", 1234, 1244, 1);
            //user = new User("Alicia", "alic02", "localhost", 1235, 2);
        }else if (people.equals("Marcel")) {
            mainUser = new MainUser("Marcel", "marco01", "localhost", 1236,1246, 3);
            //user = new User("Alicia", "alic02", "localhost", 1235, 2);
        }else if (people.equals("Vincent")) {
            mainUser = new MainUser("Vincent", "vince01", "localhost", 1237,1247, 3);
            //user = new User("Alicia", "alic02", "localhost", 1235, 2);
        }
        Agent agent = new Agent();
        agent.getPseudoHandler().setMain_User(mainUser);
        agent.StartAgent();
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
