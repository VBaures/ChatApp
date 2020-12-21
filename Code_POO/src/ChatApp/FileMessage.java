package ChatApp;
import java.io.File;
public class FileMessage extends Message {
    protected byte[] content = new byte[8192];

    public FileMessage (User recipient, User sender, String filePath){
        super(recipient,sender);
        this.Content=content;
    }

    public Object getContent (){return this.Content ;}
    public void setContent (File fichier) {
        this.Content =fichier;
    }
}