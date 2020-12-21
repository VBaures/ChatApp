package ChatApp;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class Message implements Serializable {
    protected Date time;
    protected DateFormat dateFormat = new SimpleDateFormat("dd MMMMMMMMM yyyy - HH:mm ",Locale.FRANCE) ;
    protected User recipient;
    protected User sender;

    public Message(User recipient, User sender){
        this.recipient=recipient;
        this.sender=sender;
        this.time= new Date();
    }

    public Date getTime (){return this.time;}
    public String getFormatTime(){return this.dateFormat.format(this.time);}
    public String getType(){return "Message";}
    public User getRecipient() {
        return this.recipient;
    }
    public User getSender() {
        return this.sender;
    }
}