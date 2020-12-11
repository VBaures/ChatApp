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

    public ChatHandler(User recipient, ObjectOutputStream out, Socket socket){
        this.recipient=recipient;
        this.output=out;
        this.socket=socket;
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

    boolean isEqual(ChatHandler chatHandler){
        boolean bool = false;
        if ((this.recipient == chatHandler.getRecipient())&(this.output == chatHandler.getOutput())) {
            bool=true;
        }
        return bool;
    }
}
