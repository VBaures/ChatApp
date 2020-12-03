package ChatApp;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChatHandler {
    ObjectOutputStream output;
    User recipient;
    ArrayList<Message> messageHistory;

    public ChatHandler(User recipient, ObjectOutputStream out){
        this.recipient=recipient;
        this.output=out;
    }
    public ChatHandler(User user){
        this.recipient=user;
    }

    public void Send(Object object) {
        try {
            this.output.writeObject(object);
        } catch (IOException e) {
        }
    }

    void setOutput(ObjectOutputStream output){
        this.output=output;
    }

    ObjectOutputStream getOutput (){return this.output;}

    public User getRecipient(){
        return this.recipient;
    }

    boolean isEqual(ChatHandler chatHandler){
        boolean bool = false;
        if ((this.recipient == chatHandler.getRecipient())&(this.output == chatHandler.getOutput())) {
            bool=true;
        }
        return bool;
    }
}
