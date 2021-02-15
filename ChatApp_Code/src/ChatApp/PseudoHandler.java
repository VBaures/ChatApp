/*
This class handle all the information regarding the users connected and the main user

@author Vincent Baures and Alicia Calmet
@date 2021-02-13
*/

package ChatApp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PseudoHandler {
    private MainUser main_User;
    private ArrayList<User> connectedUsers;

/*==========CONSTRUCTOR==========*/
    public PseudoHandler(Agent agent) throws UnknownHostException {
        this.connectedUsers = new ArrayList<User>();
        main_User = new MainUser("notdefine",InetAddress.getLocalHost(),-1);
    }

/*Those functions add a user to the list if it does not exist or modify his information */
    public void UpdateUsers(String pseudo, InetAddress address,int ID) {
        User find = FindUser(ID);
        if (main_User.getID() != ID) {
            if (find==null) {
                User user = new User(pseudo, address, ID);
                connectedUsers.add(user);
            } else if (find.getPseudo().equals(pseudo)==false) {
                find.setPseudo(pseudo);
            }
        }
    }
    public void UpdateUsers(Object object) {
        User user = (User) object;
        User find = FindUser(user.getID());
        if (main_User.getID() != user.getID()) {
            if (find==null) {
                connectedUsers.add(user);
            } else if (find.getPseudo().equals(user.getPseudo())==false) {
                find.setPseudo(user.getPseudo());
            }
        }
    }

/* THose functions remove a user from the list if he is in it */
    public void RemoveUser(int ID){
        User find = FindUser(ID);
        if (find!=null) {
            connectedUsers.remove(find);
        }
    }
    public void RemoveUser(Object object){
        User user = (User) object;
        User find = FindUser(user.getID());
        if (find!=null) {
            connectedUsers.remove(find);
        }
    }

/* This function verify if a pseudo is already used by someone in the list*/
    public boolean VerifyPseudo(String Pseudo) {
        boolean non_utilise = true;
        for (int i = 0; i < connectedUsers.size(); i++) {
            if ((connectedUsers.get(i).getPseudo().trim().equals(Pseudo)) || Pseudo.equals("")) {
                non_utilise = false;
            }
        }
        return non_utilise;
    }

/*Those function find a user in the list via his pseudo, ID or IP address*/
    public User FindUser (String Pseudo){
        for (int i = 0; i < connectedUsers.size(); i++) {
            if (connectedUsers.get(i).getPseudo().equals(Pseudo)) {
                return connectedUsers.get(i);
            }
        }
        return(null);
    }
    public User FindUser (int id){
        for (int i = 0; i < connectedUsers.size(); i++) {
            if (connectedUsers.get(i).getID()==id) {
                return connectedUsers.get(i);
            }
        }
        return(null);
    }
    public User FindUserByIP(String inetAddress) {
        for (int i = 0; i < connectedUsers.size(); i++) {
            if (connectedUsers.get(i).getAddr_Ip().getHostAddress().equals(inetAddress)) {
                return connectedUsers.get(i);
            }
        }
        return null;
    }

/*==========GETTERS AND SETTERS==========*/
    public MainUser getMain_User() { return main_User; }
    public ArrayList<User> getConnectedUsers() { return connectedUsers; }

}
