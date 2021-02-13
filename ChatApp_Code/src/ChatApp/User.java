/*
This class regroup all the information about a lambda user

@author Vincent Baures and Alicia Calmet
@date 2021-02-13
*/
package ChatApp;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {
    protected String pseudo;
    private InetAddress addr_IP;
    private int ID;

/*==========CONSTRUCTOR==========*/
    public User(String pseudo, InetAddress addr_IP,int ID ){
        this.pseudo = pseudo;
        this.addr_IP = addr_IP;
        this.ID=ID;
    }

/*==========GETTERS AND SETTERS==========*/
    public String getPseudo () { return this.pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public int getID () { return this.ID; }
    public void setID(int ID) { this.ID = ID; }
    public InetAddress getAddr_Ip() {return addr_IP;}
}