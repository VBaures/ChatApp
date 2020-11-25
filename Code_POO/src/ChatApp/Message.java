package ChatApp;
public class Message {
    protected Date time;
    protected User recipient;
    public Date getTime (){this.time;}

    public User getRecipient() {
        return this.recipient;
    }
}