package ChatApp;
import java.util.Date;
public class Message {
    protected Date time;
    protected User recipient;
    public Date getTime (){return this.time;}
<<<<<<< HEAD
    public String getType(){return "Message";}
=======
>>>>>>> parent of 63856b5... Merge branch 'master' of https://github.com/VBaures/Projet-COO
    public User getRecipient() {
        return this.recipient;
    }
}