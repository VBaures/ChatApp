/*
This class is a abstract that class that represent a message without entering in the details of its content

@author Vincent Baures and Alicia Calmet
@date 2021-02-13
*/

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

/*==========CONSTRUCTORS==========*/
    public Message(User recipient, User sender){
        this.recipient=recipient;
        this.sender=sender;
        this.time= new Date();
    }
    public Message(User recipient, User sender , Date time){
        this.recipient=recipient;
        this.sender=sender;
        this.time= time;
    }

/*==========GETTERS AND SETTERS==========*/
    public String getFormatTime(){return this.dateFormat.format(this.time);}
    public User getRecipient() { return this.recipient; }
    public User getSender() { return this.sender; }
}