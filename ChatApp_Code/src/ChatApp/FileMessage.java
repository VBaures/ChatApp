package ChatApp;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class FileMessage extends Message implements Serializable {
    protected byte[] content;
    protected String fileName;

    public FileMessage (User recipient, User sender, String filePath) throws IOException {
        super(recipient,sender);
        Path path = Paths.get(filePath);
        this.fileName = path.getFileName().toString();
        this.content= Files.readAllBytes(path);
        System.out.println("Date message "+this.getFormatTime());
    }

    public FileMessage (User recipient, User sender, String fileName, Date date, byte[] content) throws IOException {
        super(recipient,sender,date);
        this.fileName = fileName;
        this.content= content;
    }


    public byte[] getContentFile (){return this.content;}

    public String getFileName() {
        return this.fileName;
    }
}