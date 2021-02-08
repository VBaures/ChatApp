package ChatApp;

import java.io.Serializable;
import java.util.Date;

public class StringMessage extends Message implements Serializable {
    protected String Content;

    public StringMessage(User recipient, User sender, String content){
        super(recipient,sender);
        this.Content=content;
    }

    public StringMessage(User recipient, User sender, String content, Date time){
        super(recipient,sender, time);
        this.Content=content;
    }

    public String getContentString (){return this.Content ;}
    public void setContent (String string) {this.Content=string;}


}
