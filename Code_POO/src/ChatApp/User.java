package ChatApp;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {
    protected String pseudo;
    protected InetAddress addr_IP;
    protected int serverPort;
    protected int clientPort;
    protected String place;
    protected int ID;

    public User(){};
    public User(String pseudo, InetAddress addr_IP, int ID ){
        this.pseudo = pseudo;
        this.addr_IP = addr_IP;
        this.ID=ID;
    }

    public String getPseudo () {
        return this.pseudo;
    }
    public int getServerPort (){return this.serverPort;}
    public int getClientPort (){return this.clientPort;}

    public String getPlace() {
        return place;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public void setAddr_IP (InetAddress addr_IP){ this.addr_IP = addr_IP; };
    public void setID (int ID) { this.ID = ID; }
    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {return "User";}
    public InetAddress getAddr_Ip() {return addr_IP;}
    public int getID () {
        return this.ID;
    }
}