package ChatApp;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChatHandler {
    Socket socket;
    ObjectOutputStream output;
    User recipient;
    ArrayList<Message> messageHistory;
    Agent agent;
    ChatPage chatPage;

    public ChatHandler(User recipient, ObjectOutputStream out, Socket socket, Agent agent){
        this.recipient=recipient;
        this.output=out;
        this.socket=socket;
        this.agent=agent;
        messageHistory=new ArrayList<Message>();
        chatPage =  new ChatPage(this.agent, this);
        chatPage.affichage();
    }
    public ChatHandler(User user){
        this.recipient=user;
    }

    public void Send(String object) {
        try {

            StringMessage message = new StringMessage(recipient, agent.getPseudoHandler().getMain_User(), object);
            this.output.writeObject(message);
            this.getMessageHistory().add(message);
            chatPage.Mise_a_jour();
        } catch (IOException e) {
        }
    }

    public void StopChat() throws IOException {
        socket.close();
    }

    void setOutput(ObjectOutputStream output){
        this.output=output;
    }

    void setSocket(Socket socket){
        this.socket=socket;
    }

    ObjectOutputStream getOutput (){return this.output;}

    public User getRecipient(){
        return this.recipient;
    }

    public ChatPage getChatPage(){return this.chatPage;}

    public ArrayList<Message> getMessageHistory (){return this.messageHistory;}


    boolean isEqual(ChatHandler chatHandler){
        boolean bool = false;
        if ((this.recipient == chatHandler.getRecipient())&(this.output == chatHandler.getOutput())) {
            bool=true;
        }
        return bool;
    }
}
