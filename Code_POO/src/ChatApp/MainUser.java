package ChatApp;

import java.net.InetAddress;

public class MainUser extends User {
    protected String Place;

    public MainUser (String pseudo,int ID) { super(pseudo, null ,ID);
    }

    public void setPseudo (String pseudo) {
        super.pseudo=pseudo;
    }

    public String getPlace() {
        return Place;
    }
    public void setPlace(String place) {
        Place = place;
    }

}