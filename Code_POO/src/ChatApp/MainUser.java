package ChatApp;

import java.net.InetAddress;

public class MainUser extends User {
    public MainUser(String pseudo, InetAddress addr_IP, int ID) {
        super(pseudo, addr_IP, ID);
    }

    public void setPseudo (String pseudo) {
        super.pseudo=pseudo;
    }
}