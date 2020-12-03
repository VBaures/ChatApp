package ChatApp;

import java.io.File;
import java.io.Serializable;

public class StringMessage extends Message implements Serializable {
    protected String Content;

    public StringMessage(User recipient, User sender, String content){
        super(recipient,sender);
        this.Content=content;

    }

    public String getContent (){return this.Content ;}
    public void setContent (String string) {this.Content=string;}


}
