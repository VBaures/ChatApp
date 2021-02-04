package ChatApp;

import java.io.Serializable;

public class User implements Serializable {
    protected String pseudo;
    protected String addr_IP;
    protected int serverPort;
    protected int clientPort;
    protected int ID;

    public User(){};
    public User(String pseudo, String addr_IP, int serverPort, int clientPort, int ID ){
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

    public String getType() {return "User";}
    public String getAddr_Ip() {return addr_IP;}
    public int getID () {
        return this.ID;
    }
}