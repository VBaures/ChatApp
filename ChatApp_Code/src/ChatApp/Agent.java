package ChatApp;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;

public class Agent {

    private NetworkHandler networkHandler;
    private ArrayList <ChatHandler> currentChat;
    private PseudoHandler pseudoHandler;
    private AuthentificationPage authentificationPage;
    private PseudoPage pseudoPage;
    private UsersWindows usersWindows;
    private BDDpage bddpage;
    private HandlerBDD bddHandler;

    public Agent() throws IOException { ;
        currentChat = new ArrayList<ChatHandler>();
        pseudoHandler = new PseudoHandler(this);
        bddHandler = new HandlerBDD(this);
        authentificationPage = new AuthentificationPage(this);
        pseudoPage = new PseudoPage(this);
        usersWindows = new UsersWindows(this);
        bddpage=new BDDpage(this);
    }

    public void StartServers(){
        networkHandler = new NetworkHandler(this);
    }

    public void StartChat(String pseudo) throws IOException {
        User recipient = pseudoHandler.FindUser(pseudo);
        ChatHandler chatHandler = new ChatHandler(recipient,this);
        chatHandler.StartPage();
        System.out.println("Historique "+chatHandler.getMessageHistory());
        currentChat.add(chatHandler);
        networkHandler.StartChat(chatHandler);
    }

    public void StopChat(int SenderID){
        ChatHandler chatHandler = findChatHandler(SenderID);
        try {
            chatHandler.StopChat();
            currentChat.remove(chatHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void StopChat(ChatHandler chatHandler){
        try {
            chatHandler.StopChat();
            currentChat.remove(chatHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReceiveMessage(StringMessage message){
        findChatHandler(message.getSender().getID()).Receive(message);
    }
    public void ReceiveMessage(FileMessage message){
        findChatHandler(message.getSender().getID()).Receive(message);
    }

    public void UpdateUsers(Object object) {
        pseudoHandler.UpdateUsers(object);
        usersWindows.jListSimple.Mise_a_jour(pseudoHandler.getConnectedUsers());
    }

    public void UpdateUsers(String pseudo, InetAddress address, int ID) {
        pseudoHandler.UpdateUsers(pseudo,address,ID);
        usersWindows.jListSimple.Mise_a_jour(pseudoHandler.getConnectedUsers());
    }

    public void RemoveUser(int ID) {
        pseudoHandler.RemoveUser(ID);
        usersWindows.jListSimple.Mise_a_jour(pseudoHandler.getConnectedUsers());
    }
    public void RemoveUser(Object object) {
        pseudoHandler.RemoveUser(object);
        usersWindows.jListSimple.Mise_a_jour(pseudoHandler.getConnectedUsers());
    }

    public boolean LogIn(String username, String password) {
        int id = -1;
        try {
            id = bddHandler.getIDUser(username, password);
            System.out.println("ID=" + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (id != -1) {
            pseudoHandler.getMain_User().setID(id);
            System.out.println(pseudoHandler.getMain_User());
            return true;
        }else{
            return false;
        }
    }

    public boolean CreateAccount(String username, String password){
        int nb=-1;
        try {
            nb = bddHandler.insertUser(username, password);
        }catch (SQLException e){}
        return (nb != 0);
    }

    public void Disconnect() throws IOException {
        for (int i =0 ; i<currentChat.size();i++){
            StopChat(currentChat.get(i));
        }
        if (pseudoHandler.getMain_User().getPlace().equals("indoor")) {
            networkHandler.getRemoteHandler().NotifyDisconnection();
            networkHandler.getServerHandler().getUdp().broadcastUDP("Disconnect", pseudoHandler.getMain_User());
        } else if (pseudoHandler.getMain_User().getPlace().equals("remote")){
            networkHandler.getRemoteHandler().NotifyDisconnection();
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

    public HandlerBDD getBddHandler() {
        return bddHandler;
    }

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