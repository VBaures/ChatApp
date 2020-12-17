//author Alicia Calmet, si problème m'appeler
package ChatApp;

import java.util.ArrayList;

public class PseudoHandler {
    protected MainUser main_User;
    //a voir si la liste des personnes connectés n'est pas de type public
    protected ArrayList<User> connectedUsers;

    public PseudoHandler(Agent agent){
        this.connectedUsers = new ArrayList<User>();
            }

    public void UpdateConnectedUsers(User new_User) {
        this.connectedUsers.add(new_User);
    }

    public MainUser getMain_User() {
        return main_User;
    }

    public ArrayList<User> getConnectedUsers() {
        return connectedUsers;
    }

    //on va mettre a jour la liste des personnes connectés. Par exemple une personne vient d'activer
    // l'application, il faut donc le savoir pour avoir la possibilité de communiquer avec elle ultérieurement.
    public void UpdateConnectedUser(User new_User) {
    }

    //on regarde dans l'array. A la recherche de Bernard.....
    public boolean VerifyPseudo(String Pseudo) {
        boolean non_utilise = true;
        System.out.println("Users connectés:" + connectedUsers);
        for (int i = 0; i < connectedUsers.size(); i++) {
            if ((connectedUsers.get(i).getPseudo().trim().equals(Pseudo)) || Pseudo.equals("")) {
                non_utilise = false;
            }
        }
        return non_utilise;
    }
    //...on a trouvé Bernard

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

    //si le pseudo est ok alors l'utilisateur prend ce pseudo
    //cette fonction est utilise pour CHANGER LE PSEUDO aussi
    public void ChoosePseudo(String NewPseudo, int id) {
        FindUser(id).setPseudo(NewPseudo);

    }

    public boolean IsConnectedUser (User user) {
        boolean connecte = false;
        for (int i = 0; i < connectedUsers.size(); i++) {
            if (connectedUsers.get(i).username == user.username) {
                connecte = true;
            }
        }
        return connecte;
    }

    //TODO quand on aura fait la base de données
    public void VerifyLogin (String username,String password){}
    public void NotifyPseudoChange(){ }

    public void setMain_User(MainUser main_User) {
        this.main_User = main_User;
    }

    public User FindUserByPort(int port) {
        for (int i = 0; i < connectedUsers.size(); i++) {
            if (connectedUsers.get(i).getClientPort()==port) {
                return connectedUsers.get(i);
            }
        }
        return null;
    }
}
