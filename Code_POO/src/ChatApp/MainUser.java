package ChatApp;

public class MainUser extends User {
    public MainUser(String username, String pseudo, String addr_IP, int port, int ID) {
        super(username, pseudo, addr_IP, port, ID);
    }

    public void setPseudo (String pseudo) {
        super.pseudo=pseudo;
    }
}