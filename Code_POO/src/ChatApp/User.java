package ChatApp;

import java.io.Serializable;

public class User implements Serializable {
    protected String username;
    protected String password;
    protected String pseudo;
    protected String addr_IP;
    protected Integer port;
    protected int ID;

    public String getUserName () {
        return this.username;
    }
    public String getPassword () {
        return this.password;
    }
    public String getPseudo () {
        return this.pseudo;
    }
    public Integer getPort (){return port;}
    public String getType() {return "User";}
    public String getAddr_Ip() {return addr_IP;}
    public int getID () {
        return this.ID;
    }
}