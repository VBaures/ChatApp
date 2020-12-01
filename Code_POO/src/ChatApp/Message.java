package ChatApp;
import java.io.Serializable;
import java.util.Date;
public class Message implements Serializable {
    protected Date time;
    protected User recipient;
    public Date getTime (){return this.time;}
    public String getType(){return "Message";}
    public User getRecipient() {
        return this.recipient;
    }
}