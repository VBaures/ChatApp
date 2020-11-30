package ChatApp;

import java.util.ArrayList;

public class PseudoHandler {
    protected MainUser main_User;
    //a voir si la liste des personnes connectés n'est pas de type public
    protected ArrayList<User> connectedUsers;

    public void UpdateConnectedUsers(User new_User){
        //this.connectedUsers;
    }
    public MainUser getMain_User() {return main_User;}

    public ArrayList<User> getConnectedUsers() {return connectedUsers;}

    //on va mettre a jour la liste des personnes connectés. Par exemple une personne vient d'activer
    // l'application, il faut donc le savoir pour avoir la possibilité de communiquer avec elle ultérieurement.
    public void UpdateConnectedUser (User new_User) {}

    //on regarde dans l'array'
    public boolean VerifyPseudo (String Pseudo) {}
    public void ChoosePseudo (String Pseudo){
        if (VerifyPseudo(Pseudo)) {

        }
    }
    public void ChangePseudo (String Pseudo) {}

}