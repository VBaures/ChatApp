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
    UsersWindows affichage;

    public Agent(){
        networkHandler = new NetworkHandler(this);
        currentChat = new ArrayList<ChatHandler>();
        pseudoHandler = new PseudoHandler();
        affichage= new UsersWindows(this);
        affichage.start();
    }

    public void StartAgent() throws IOException {
        networkHandler.StartServer();
    }

    public void StartChat(String pseudo) throws IOException {
        User recipient = pseudoHandler.FindUser(pseudo);
        ChatHandler chatHandler = new ChatHandler(recipient,this);
        System.out.println("Historique "+chatHandler.getMessageHistory());
        currentChat.add(chatHandler);
        networkHandler.StartChat(chatHandler);
    }

    public void StopChat(ChatHandler chatHandler){
        try {
            chatHandler.StopChat();
            currentChat.remove(chatHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UsersWindows getAffichage(){
        return  this.affichage;
    }

    public PseudoHandler getPseudoHandler() {
        return pseudoHandler;
    }

    public ArrayList<ChatHandler> getCurrentChat() {
        return currentChat;
    }

    public ChatHandler findChatHandler(String pseudo){
        ChatHandler chat = null;
        int i;
        for (i=0;i<this.currentChat.size();i++){
            if (this.currentChat.get(i).getRecipient().getPseudo().equals(pseudo)){
                chat=this.currentChat.get(i);
            }
        }
        return chat;
    }


}