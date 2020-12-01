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

    public ChatHandler(ObjectOutputStream out){
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
}
