package ChatApp;/*
    Classe gérant le pseudonyme de l'utilisateur et l'unicité des pseudonymes.
 */

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class PseudoHandler {
    protected MainUser main_User;
    //a voir si la liste des personnes connectés n'est pas de type public
    protected ArrayList<User> connectedUsers;

    public PseudoHandler(Agent agent) throws UnknownHostException {
        this.connectedUsers = new ArrayList<User>();
        main_User = new MainUser("notdefine",getIpAddress(),-1);
        System.out.println("Addresse IP = "+getIpAddress());
    }

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

    //user[3].trim().equals("connected")


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

    //TODO quand on aura fait la base de données
    public void VerifyLogin (String username,String password){

    }
    public void NotifyPseudoChange(){ }
    public static InetAddress getIpAddress() throws UnknownHostException {
        UnknownHostException exception = new UnknownHostException("Failed to get IP") ;

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces() ;

            // For each interfaces
            while(interfaces.hasMoreElements()) {
                NetworkInterface nInterface = interfaces.nextElement() ;
                Enumeration<InetAddress> addresses = nInterface.getInetAddresses() ;

                if(nInterface.isLoopback() || ! nInterface.isUp()) {
                    continue ;
                }

                // For each addresses of the interface.
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement() ;

                    /*
                     * We are not using IPv6 addresses. It's a
                     * choice.
                     *
                     * || addr instanceof Inet6Address
                     */
                    if (addr instanceof Inet4Address) {
                        return InetAddress.getByName(addr.getHostAddress()) ;
                    }
                }
            }
        } catch (Exception e) {
            exception.initCause(e) ;
        }

        throw exception ;
    }

    public User FindUserByIP(String inetAddress) {
        for (int i = 0; i < connectedUsers.size(); i++) {
            System.out.println(connectedUsers.get(i).getAddr_Ip().toString());
            System.out.println(inetAddress);
            if (connectedUsers.get(i).getAddr_Ip().getHostAddress().equals(inetAddress)) {
                return connectedUsers.get(i);
            }
        }
        return null;
    }
}
