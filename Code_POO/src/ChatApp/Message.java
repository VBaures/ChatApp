package ChatApp;
import java.util.Date;
public class Message {
    protected Date time;
    protected User recipient;
    public Date getTime (){return this.time;}
    public User getRecipient() {
        return this.recipient;
    }
}