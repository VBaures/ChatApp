package ChatApp;

import java.io.InputStream;
import java.net.Socket;

public class NetworkListener implements Runnable {
    final Socket link;

    public NetworkListener(Socket link) {
        this.link = link;
    }

    @Override
    public void run() {
        System.out.println("Thread started");
        try {
            InputStream in = link.getInputStream();
            int rcv = 0;
            while ((rcv = in.read()) != 0) {
                System.out.println("Received: " + rcv);
            }
            System.out.println("Finishing thread");
            in.close();
            link.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
