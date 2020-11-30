package ChatApp;

import java.io.*;
import java.net.Socket;

public class NetworkListener implements Runnable {

    Socket socket;

    public NetworkListener(Socket link) {
        this.socket=link;
    }


    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println("awaiting data...");
            String message;
            while ((message=in.readLine()) == null) {}
            System.out.println(message);
            socket.close();

        }catch(IOException e){
            System.err.println("Le server est déjà utlisé ! ");
        }

    }

}
