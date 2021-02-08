package ChatApp;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class ChatHandler {
    Socket socket;
    ObjectOutputStream output;
    User recipient;
    ArrayList<Message> messageHistory = new ArrayList<Message>();
    Agent agent;
    ChatPage chatPage;
    int ID;

    public ChatHandler(User recipient, ObjectOutputStream out, Socket socket, Agent agent){
       try {
            this.ID = agent.getBddHandler().getIDConversation(agent.getPseudoHandler().getMain_User().getID(), recipient.getID());
            System.out.println("ID conversation = "+ID);
        }catch (SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
        this.recipient=recipient;
        System.out.println("User = "+this.recipient);
        this.output=out;
        this.socket=socket;
        this.agent=agent;
        try {
            messageHistory = agent.getBddHandler().getHistoriqueMessages(ID);
            System.out.println("Message history:" +messageHistory);
        }catch (SQLException | IOException | ParseException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public ChatHandler(User recipient, Agent agent){
        try {
            agent.getBddHandler().insertConversation(agent.getPseudoHandler().getMain_User().getID(), recipient.getID());
            ID = agent.getBddHandler().getIDConversation(agent.getPseudoHandler().getMain_User().getID(), recipient.getID());
            System.out.println("ID conversation = "+ID);
        }catch (SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
        this.recipient=recipient;
        this.agent=agent;
        try {
            messageHistory = agent.getBddHandler().getHistoriqueMessages(ID);
            System.out.println("Message history:" +messageHistory);
        }catch (SQLException | IOException | ParseException e){
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println(messageHistory);
    }

    public void StartPage(){
        chatPage =  new ChatPage(this.agent, this);
        chatPage.start();
    }

    public void Receive(StringMessage message){
            this.messageHistory.add(message);
            this.chatPage.Mise_a_jour();
    }
    public void Receive(FileMessage message){
        this.messageHistory.add(message);
        this.chatPage.Mise_a_jour();
    }

    public void Send(Object object) {
        try {
            if (object instanceof  String) {
                String content = (String) object;
                StringMessage message = new StringMessage(recipient, agent.getPseudoHandler().getMain_User(), content);
                this.output.writeObject(message);
                this.getMessageHistory().add(message);
                System.out.println("Message History :" + messageHistory);
                chatPage.Mise_a_jour();
                System.out.println("Mise à jour ok");
                agent.getBddHandler().insertMessage(message.sender, message.recipient, ID, message.getContentString(), message.getFormatTime().toString());
            } else if (object instanceof File){
                File content = (File) object;
                System.out.println("Envoie d'un fichier");
                FileMessage message = new FileMessage(recipient, agent.getPseudoHandler().getMain_User(), content.getPath());
                this.output.writeObject(message);
                this.getMessageHistory().add(message);
                chatPage.Mise_a_jour();
                agent.getBddHandler().insertMessage(message.sender, message.recipient, ID, message.getFileName()+" was sent but is no more available", message.getTime().toString());
            }
        } catch (IOException e) {
        }
    }

    public void StopChat() throws IOException {
        output.writeObject("StopChat");
        socket.close();
        chatPage.getFram().dispose();
    }

    void setOutput(ObjectOutputStream output){
        this.output=output;
    }

    void setSocket(Socket socket){
        this.socket=socket;
    }


    public User getRecipient(){
        return this.recipient;
    }

    public ArrayList<Message> getMessageHistory (){return this.messageHistory;}
}
