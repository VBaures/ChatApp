package ChatApp;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ChatHandler {
    ObjectOutputStream output;

    public ChatHandler(ObjectOutputStream output){
        this.output=output;
    }
    public  ObjectOutputStream getOutput(){
        return this.output;
    }
}
