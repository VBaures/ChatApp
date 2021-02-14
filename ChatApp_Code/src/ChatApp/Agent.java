/*
This class is the principal class of our application.
His purpose is to do the link between all the sides of our application

@author Vincent Baures and Alicia Calmet
@date 2021-02-13
*/
package ChatApp;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;

public class Agent {
    private NetworkHandler networkHandler;
    private ArrayList<ChatHandler> currentChat;
    private PseudoHandler pseudoHandler;
    private AuthentificationPage authentificationPage;
    private PseudoPage pseudoPage;
    private UsersWindows usersWindows;
    private BDDpage bddpage;
    private HandlerBDD bddHandler;

/*======================CONSTRUCTOR======================*/
    public Agent() throws IOException {
        currentChat = new ArrayList<ChatHandler>();
        pseudoHandler = new PseudoHandler(this);
        bddHandler = new HandlerBDD(this);
        authentificationPage = new AuthentificationPage(this);
        pseudoPage = new PseudoPage(this);
        usersWindows = new UsersWindows(this);
        bddpage = new BDDpage(this);
    }

/* This fonction starts all the servers that means the TCP, UDP and HTTP servers */
    public void StartServers() {
        networkHandler = new NetworkHandler(this);
    }

/* This fonction starts a chat by creating a chat handler associated to a recipient user */
    public void StartChat(String pseudo) throws IOException {
        User recipient = pseudoHandler.FindUser(pseudo);
        ChatHandler chatHandler = new ChatHandler(recipient, this);
        currentChat.add(chatHandler);
        networkHandler.StartChat(chatHandler);
    }

/* Those functions stop a chat by closing the chat handler of the chat */
    public void StopChat(int SenderID) {
        ChatHandler chatHandler = findChatHandler(SenderID);
        networkHandler.StopChat(chatHandler);
        try {
            chatHandler.StopChat();
            currentChat.remove(chatHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void StopChat(ChatHandler chatHandler) {
        networkHandler.StopChat(chatHandler);
        try {
            chatHandler.StopChat();
            currentChat.remove(chatHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/* This function are used when one of the servers intercept an incoming message */
    public void ReceiveMessage(StringMessage message) {
        findChatHandler(message.getSender().getID()).Receive(message);
    }
    public void ReceiveMessage(FileMessage message) {
        findChatHandler(message.getSender().getID()).Receive(message);
    }

/* Those function are used when user's information are received from the UDP or HTTP server */
    public void UpdateUsers(Object object) {
        pseudoHandler.UpdateUsers(object);
        usersWindows.getjListSimple().Mise_a_jour(pseudoHandler.getConnectedUsers());
    }
    public void UpdateUsers(String pseudo, InetAddress address, int ID) {
        pseudoHandler.UpdateUsers(pseudo, address, ID);
        usersWindows.getjListSimple().Mise_a_jour(pseudoHandler.getConnectedUsers());
    }

/* This function are used when we receive a disconnection signal from a user */
    public void RemoveUser(int ID) {
        pseudoHandler.RemoveUser(ID);
        usersWindows.getjListSimple().Mise_a_jour(pseudoHandler.getConnectedUsers());
    }
    public void RemoveUser(Object object) {
        pseudoHandler.RemoveUser(object);
        usersWindows.getjListSimple().Mise_a_jour(pseudoHandler.getConnectedUsers());
    }

/* This function verify if the specify username and password are valid or not */
    public boolean LogIn(String username, String password) {
        int id = -1;
        try {
            id = bddHandler.getIDUser(username, password);
        } catch (SQLException throwables) {
        }
        if (id != -1) {
            pseudoHandler.getMain_User().setID(id);
            return true;
        } else {
            return false;
        }
    }

/* This function add a new account to the database */
    public boolean CreateAccount(String username, String password) {
        int nb = -1;
        try {
            nb = bddHandler.insertUser(username, password);
        } catch (SQLException e) {
        }
        return (nb != 0);
    }

/* This function occurs when we quit the application, notify the server a well as all the local user and close all the chat still open*/
    public void Disconnect() throws IOException {
        for (int i = 0; i < currentChat.size(); i++) {
            StopChat(currentChat.get(i));
        }
        if (pseudoHandler.getMain_User().getPlace().equals("indoor")) {
            networkHandler.getRemoteHandler().NotifyDisconnection();
            networkHandler.getServerHandler().getUdp().broadcastUDP("Disconnect", pseudoHandler.getMain_User());
        } else if (pseudoHandler.getMain_User().getPlace().equals("remote")) {
            networkHandler.getRemoteHandler().NotifyDisconnection();
        }
        bddHandler.CloseConnection();
    }

/* This function find the chat handler corresponding to a recipient ID*/
    public ChatHandler findChatHandler(int id) {
        ChatHandler chat = null;
        int i;
        for (i = 0; i < this.currentChat.size(); i++) {
            if (this.currentChat.get(i).getRecipient().getID() == id) {
                chat = this.currentChat.get(i);
            }
        }
        return chat;
    }

/*============GETTERS AND SETTERS============*/
    public PseudoHandler getPseudoHandler() {
        return pseudoHandler;
    }

    public ArrayList<ChatHandler> getCurrentChat() {
        return currentChat;
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public PseudoPage getPseudoPage() {
        return this.pseudoPage;
    }

    public BDDpage getBddpage() { return this.bddpage; }

    public HandlerBDD getBddHandler() {
        return bddHandler;
    }

    public UsersWindows getUsersWindows() {
        return this.usersWindows;
    }
}