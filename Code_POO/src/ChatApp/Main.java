package ChatApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static User user;
    static MainUser mainUser;
    public static void main(String[] args) throws IOException, InterruptedException {
        //Creation d'un main user
        mainUser=new MainUser ("Alicia","alic02","localhost",1235,2);
        user= new User("Lucas","luc01","localhost",1234,1);
        Agent agent = new Agent();
        agent.getPseudoHandler().setMain_User(mainUser);
        agent.getPseudoHandler().UpdateConnectedUsers(user);
        System.out.println(agent.getPseudoHandler().FindUser("luc01").getUserName());
        agent.StartAgent();
        System.out.println(agent.getPseudoHandler().getConnectedUsers().get(0).getUserName());
        Scanner keyboard = new Scanner(System.in);
        System.out.println("A qui veux-tu parler?");
        String destinataire =keyboard.nextLine();
        System.out.println("Le destinaire est: "+ destinataire);
        agent.StartChat(destinataire);
        System.out.println(agent.getCurrentChat().get(0).recipient.getUserName());
        System.out.println("Que veux-tu envoyer?");
        String message = keyboard.nextLine();
        agent.getCurrentChat().get(0).Send(keyboard.nextLine());
    }

    /*
     //Creation de l'interface réseau
        NetworkHandler networkHandler = new NetworkHandler();
        //User qui sera envoyé
        MainUser user = new MainUser();
        user.setPseudo("Lucas");
        //String qui sera envoyé
        String string = "Coucou";
        //Message qui sera envoyé
        StringMessage message = new StringMessage();
        message.setContent("Ceci est un message");
        //Simulation lancement de l'agent et création du server
        ClientHandler client = new ClientHandler(1234,networkHandler);
        client.start();
        Scanner keyboard = new Scanner(System.in);
        client.Send(keyboard.nextLine());
        //ServerHandler server = new ServerHandler(networkHandler,1234);
        //server.start();
        //Scanner keyboard = new Scanner(System.in);
        //server.Send(keyboard.nextLine(),networkHandler.currentChats.get(0));

        //Simulation demande de connection avec un autre utilisateur
     */
}
