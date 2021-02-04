package ChatApp;

public class MainUser extends User {
    public MainUser (String pseudo, String addr_IP, int serverPort, int clientPort, int ID) {
        super(pseudo, addr_IP, serverPort, clientPort, ID);
    }

    public void setPseudo (String pseudo) {
        super.pseudo=pseudo;
    }
}