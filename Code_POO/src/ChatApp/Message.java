package ChatApp;
import java.util.Date;
public class Message {
    protected Date time;
    protected User recipient;
    public Date getTime (){return this.time;}
<<<<<<< Updated upstream
=======
    public String getType(){return "Message";}
>>>>>>> Stashed changes
    public User getRecipient() {
        return this.recipient;
    }
}