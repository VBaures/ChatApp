package ChatApp;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ChatHandler {
    ObjectOutputStream output;

    public ChatHandler(ObjectOutputStream output){
        this.output=output;
    }

    public void Send(Object object) {
        try {
            this.output.writeObject(object);
        } catch (IOException e) {
        }
    }
}
