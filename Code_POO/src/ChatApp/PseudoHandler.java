//author Alicia Calmet, si problème m'appeler
package ChatApp;

import java.util.ArrayList;

public class PseudoHandler {
    protected MainUser main_User;
    //a voir si la liste des personnes connectés n'est pas de type public
    protected ArrayList<User> connectedUsers;

    public void UpdateConnectedUsers(User new_User) {
        //this.connectedUsers;
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
        boolean deja_utilise = false;
        for (int i = 0; i < connectedUsers.size(); i++) {
            if (connectedUsers.get(i).pseudo == Pseudo) {
                deja_utilise = true;
            }
        }
        return deja_utilise;
    }
    //...on a trouvé Bernard

    public User FindUser (String Pseudo){
        User userTrouve = new User();
        for (int i = 0; i < connectedUsers.size(); i++) {
            if (connectedUsers.get(i).pseudo == Pseudo) {
                userTrouve=connectedUsers.get(i);
            }
        }
        return userTrouve;
    }

    //si le pseudo est ok alors l'utilisateur prend ce pseudo
    //cette fonction est utilise pour CHANGER LE PSEUDO aussi
    public void ChoosePseudo(String Pseudo) {
        if (!VerifyPseudo(Pseudo)) {
            main_User.pseudo = Pseudo;
        }
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
}
