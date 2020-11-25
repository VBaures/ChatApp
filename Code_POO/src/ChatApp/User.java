package ChatApp;

public class User {
    private String username;
    private String password;
    protected String pseudo;
    private int ID;

    public String getUserName () {
        return this.username;
    }
    public String getPassword () {
        return this.password;
    }
    public String getPseudo () {
        return this.pseudo;
    }
    public int getID () {
        return this.ID;
    }
}