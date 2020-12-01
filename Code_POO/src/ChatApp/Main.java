package ChatApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        networkHandler = new NetworkHandler(this);
        currentChat = new ArrayList<ChatHandler>();
        bddHandler = new BDDHandler();
        //User qui sera envoyé
        MainUser user = new MainUser();
        user.setPseudo("Lucas");
        //String qui sera envoyé
        String string = "Coucou";
        //Message qui sera envoyé
        StringMessage message = new StringMessage();
        message.setContent("Ceci est un message");
        //Simulation lancement de l'agent et création du server
        ClientHandler client = new ClientHandler(1234,this);
        client.start();
        Scanner keyboard = new Scanner(System.in);
        client.Send(keyboard.nextLine());
        //ServerHandler server = new ServerHandler(networkHandler,1234);
        //server.start();
        //Scanner keyboard = new Scanner(System.in);
        //server.Send(keyboard.nextLine(),networkHandler.currentChats.get(0));

        //Simulation demande de connection avec un autre utilisateur
    }

}
