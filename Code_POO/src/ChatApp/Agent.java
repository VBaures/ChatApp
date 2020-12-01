package ChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ref.Cleaner;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Agent {

    protected NetworkHandler networkHandler;
    protected ArrayList <ChatHandler> currentChat;
    protected PseudoHandler pseudoHandler;

    public Agent(){
        networkHandler = new NetworkHandler(this);
        currentChat = new ArrayList<ChatHandler>();
        pseudoHandler = new PseudoHandler();
    }

    public void StartAgent(){
        networkHandler.StartServer();
    }

    public void StartChat(String pseudo) throws IOException {
        System.out.println(pseudoHandler.connectedUsers.get(0).getPseudo());
        User recipient = pseudoHandler.FindUser(pseudo);
        currentChat.add(new ChatHandler(recipient));
        System.out.println("Nom du destinataire:" + recipient.getUserName());
        System.out.println("Port du destinataire:" + recipient.getPort());
        networkHandler.StartChat(recipient.getPort());

    }

    public PseudoHandler getPseudoHandler() {
        return pseudoHandler;
    }

    public ArrayList<ChatHandler> getCurrentChat() {
        return currentChat;
    }


}