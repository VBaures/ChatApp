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
    AuthentificationPage authentificationPage;
    PseudoPage pseudoPage;
    UsersWindows usersWindows;
    BDDpage bddpage;

    public Agent(){
        networkHandler = new NetworkHandler(this);
        currentChat = new ArrayList<ChatHandler>();
        pseudoHandler = new PseudoHandler(this);
        authentificationPage = new AuthentificationPage(this);
        pseudoPage = new PseudoPage(this);
        usersWindows = new UsersWindows(this);
        bddpage=new BDDpage(this);
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
            System.out.println("Chat trouvé: "+findChatHandler(chatHandler.getRecipient().getID()));
            chatHandler.StopChat();
            networkHandler.StopChat(chatHandler);
            currentChat.remove(chatHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public PseudoHandler getPseudoHandler() {
        return pseudoHandler;
    }

    public ArrayList<ChatHandler> getCurrentChat() {
        return currentChat;
    }

    public ChatHandler findChatHandler(int id){
        ChatHandler chat = null;
        int i;
        for (i=0;i<this.currentChat.size();i++){
            if (this.currentChat.get(i).getRecipient().getID()==id){
                chat=this.currentChat.get(i);
            }
        }
        return chat;
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public PseudoPage getPseudoPage(){
        return this.pseudoPage;
    }

    public BDDpage getBddpage(){return this.bddpage;}

    public UsersWindows getUsersWindows() {
        return this.usersWindows;
    }

    public void UpdatePseudo(String NewPseudo, int id){
        pseudoHandler.ChoosePseudo(NewPseudo, id);
        if (findChatHandler(id)!=null) {
            findChatHandler(id).getRecipient().setPseudo(NewPseudo);
            for (int i = 0; i < findChatHandler(id).getMessageHistory().size(); i++) {
                if (findChatHandler(id).getMessageHistory().get(i).getRecipient().getID() == id) {
                    findChatHandler(id).getMessageHistory().get(i).getRecipient().setPseudo(NewPseudo);
                } else if (findChatHandler(id).getMessageHistory().get(i).getSender().getID() == id) {
                    findChatHandler(id).getMessageHistory().get(i).getSender().setPseudo(NewPseudo);
                }
            }
        }
    }
}