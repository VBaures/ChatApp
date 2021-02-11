package ChatApp;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {
    protected String pseudo;
    protected InetAddress addr_IP;
    protected int ID;

    public User(String pseudo, InetAddress addr_IP,int ID ){
        this.pseudo = pseudo;
        this.addr_IP = addr_IP;
        this.ID=ID;
    }

    public String getPseudo () {
        return this.pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getID () {
        return this.ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public InetAddress getAddr_Ip() {return addr_IP;}
    public void setAddr_IP(InetAddress IP) { this.addr_IP=IP; }
}