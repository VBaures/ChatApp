package ChatApp;
import java.io.Serializable;
import java.util.Date;
public abstract class Message implements Serializable {
    protected Date time;
    protected User recipient;
    protected User sender;

    public Message(User recipient, User sender){
        this.recipient=recipient;
        this.sender=sender;
    }

    public abstract String getContent();
    public Date getTime (){return this.time;}
    public String getType(){return "Message";}
    public User getRecipient() {
        return this.recipient;
    }
    public User getSender() {
        return this.sender;
    }
}