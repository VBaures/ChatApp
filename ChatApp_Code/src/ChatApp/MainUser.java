/*
This class is a specifiaction of the user calss and resprent the main user of the application

@author Vincent Baures and Alicia Calmet
@date 2021-02-13
*/

package ChatApp;

import java.net.InetAddress;

public class MainUser extends User {
    private String Place;

/*==========CONSTRUCTORS==========*/
    public MainUser (String pseudo, InetAddress addr_IP,int ID) {
        super(pseudo, addr_IP,ID);
    }

/*==========SETTERS AND GETTERS==========*/
    public void setPseudo (String pseudo) { super.pseudo=pseudo; }
    public String getPlace() { return this.Place; }
    public void setPlace(String place) { this.Place = place; }

}