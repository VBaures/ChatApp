/*
This class is a specification of a message object for it to contain a file

@author Vincent Baures and Alicia Calmet
@date 2021-02-13
*/

package ChatApp;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class FileMessage extends Message implements Serializable {
    private byte[] content;
    private String fileName;

/*==========CONSTRUCTORS==========*/
    public FileMessage (User recipient, User sender, String filePath) throws IOException {
        super(recipient,sender);
        Path path = Paths.get(filePath);
        this.fileName = path.getFileName().toString();
        this.content= Files.readAllBytes(path);
    }
    public FileMessage (User recipient, User sender, String fileName, Date date, byte[] content) throws IOException {
        super(recipient,sender,date);
        this.fileName = fileName;
        this.content= content;
    }

/*==========GETTERS AND SETTERS==========*/
    public byte[] getContentFile (){ return this.content; }
    public String getFileName() { return this.fileName; }
}