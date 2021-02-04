package ChatApp;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {
    protected String pseudo;
    protected InetAddress addr_IP;
    protected int serverPort;
    protected int clientPort;
    protected int ID;
    protected String Place;

    public User(){};
    public User(String pseudo, InetAddress addr_IP, int serverPort, int clientPort, int ID ){
        this.pseudo = pseudo;
        this.addr_IP = addr_IP;
        this.serverPort = serverPort;
        this.clientPort = clientPort;
        this.ID=ID;
    }

    public String getPseudo () {
        return this.pseudo;
    }
    public int getServerPort (){return this.serverPort;}
    public int getClientPort (){return this.clientPort;}

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getPlace() {
        return Place;
    }

    public String getType() {return "User";}
    public InetAddress getAddr_Ip() {return addr_IP;}
    public int getID () {
        return this.ID;
    }
}