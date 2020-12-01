package ChatApp;

import java.io.File;
import java.io.Serializable;

public class StringMessage implements Serializable {
    protected String Content;
    public String getContent (){return this.Content ;}
    public void setContent (String string) {this.Content=string;}
}
