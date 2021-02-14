/*
This class is a specification of a message object for it to contain a string

@author Vincent Baures and Alicia Calmet
@date 2021-02-13
*/

package ChatApp;

import java.io.Serializable;
import java.util.Date;

public class StringMessage extends Message implements Serializable {
    private String Content;

/*==========CONSTRUCTORS==========*/
    public StringMessage(User recipient, User sender, String content){
        super(recipient,sender);
        this.Content=content;
    }
    public StringMessage(User recipient, User sender, String content, Date time){
        super(recipient,sender, time);
        this.Content=content;
    }

/*==========GETTERS AND SETTERS==========*/
    public String getContentString (){return this.Content ;}


}
