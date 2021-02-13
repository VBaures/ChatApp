/*
This class handle each conversation the user will start

@author Vincent Baures
@date 2021-02-13
*/

package ChatApp;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class ChatHandler {
    private Socket socket;
    private ObjectOutputStream output;
    private User recipient;
    private ArrayList<Message> messageHistory = new ArrayList<Message>();
    private Agent agent;
    private ChatPage chatPage;
    int ID;

/*==========CONSTRUCTORS==========*/
    public ChatHandler(User recipient, ObjectOutputStream out, Socket socket, Agent agent){
        this.agent=agent;
        //Recipient specification
        this.recipient=recipient;
        //Link via network to the recipient
        this.output=out;
        this.socket=socket;
        //Message history reception
        try {
            this.ID = agent.getBddHandler().getIDConversation(agent.getPseudoHandler().getMain_User().getID(), recipient.getID());
            messageHistory = agent.getBddHandler().getHistoriqueMessages(ID);
        }catch (SQLException | IOException | ParseException e){
            System.out.println(e);
            e.printStackTrace();
        }
        //Display the conversation windows
        StartPage();
    }
    public ChatHandler(User recipient, Agent agent){
        this.agent=agent;
        //Recipient specification
        this.recipient=recipient;
        //Message history reception
        try {
            agent.getBddHandler().insertConversation(agent.getPseudoHandler().getMain_User().getID(), recipient.getID());
            ID = agent.getBddHandler().getIDConversation(agent.getPseudoHandler().getMain_User().getID(), recipient.getID());
            messageHistory = agent.getBddHandler().getHistoriqueMessages(ID);
        }catch (SQLException | IOException | ParseException e){
            System.out.println(e);
            e.printStackTrace();
        }
        //Display the conversation windows
        StartPage();
    }

/* This function create and display a windows to caht with the recipient*/
    private void StartPage(){
        chatPage =  new ChatPage(this.agent, this);
        chatPage.start();
    }

/* Those functions handle the reception of a new message */
    public void Receive(StringMessage message){
            this.messageHistory.add(message);
            this.chatPage.Mise_a_jour();
    }
    public void Receive(FileMessage message){
        this.messageHistory.add(message);
        this.chatPage.Mise_a_jour();
    }

/* This function handle the sending of a new message */
    public void Send(Object object) {
        try {
            //Sending of a string message
            if (object instanceof  String) {
                String content = (String) object;
                StringMessage message = new StringMessage(recipient, agent.getPseudoHandler().getMain_User(), content);
                //Send the message trough the network
                this.output.writeObject(message);
                //Add the message to the history and to the database
                this.getMessageHistory().add(message);
                agent.getBddHandler().insertMessage(message.sender, message.recipient, ID, message.getContentString(), message.getFormatTime().toString(),null);
                //Update of the chat windows
                chatPage.Mise_a_jour();
            //Sending of a file message
            } else if (object instanceof File){
                File content = (File) object;
                FileMessage message = new FileMessage(recipient, agent.getPseudoHandler().getMain_User(), content.getPath());
                //Send the message trough the network
                this.output.writeObject(message);
                //Add the message to the history and to the database
                this.getMessageHistory().add(message);
                agent.getBddHandler().insertMessage(message.sender, message.recipient, ID, message.getFileName(), message.getFormatTime().toString(), message.getContentFile());
                //Update of the chat windows
                chatPage.Mise_a_jour();
            }
        } catch (IOException e) {
        }
    }

/* This function handle the end of the chat */
    public void StopChat() throws IOException {
        //Notify the recipient
        output.writeObject("StopChat");
        //Close network connections
        socket.close();
        //Close the chat window
        chatPage.getFram().dispose();
    }


/*==========GETTERS AND SETTERS==========*/
    void setOutput(ObjectOutputStream output){ this.output=output; }

    void setSocket(Socket socket){ this.socket=socket; }

    public User getRecipient(){ return this.recipient; }

    public ArrayList<Message> getMessageHistory (){ return this.messageHistory; }
}
