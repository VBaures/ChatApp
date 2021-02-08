package ChatApp;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileMessage extends Message implements Serializable {
    protected byte[] content;
    protected String fileName;

    public FileMessage (User recipient, User sender, String filePath) throws IOException {
        super(recipient,sender);
        Path path = Paths.get(filePath);
        this.fileName = path.getFileName().toString();
        this.content= Files.readAllBytes(path);
    }


    public byte[] getContentFile (){return this.content;}

    public String getFileName() {
        return this.fileName;
    }
}