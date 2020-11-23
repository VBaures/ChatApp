package ChatApp;

public class User {
	private String username;
	private String password;
	protected String pseudo;
	private int ID;
	
	String getUserName () {
		return this.username;
	}
	
	String getPassword () {
		return this.password;
	}
	
	String getPseudo () {
		return this.pseudo;
	}
	int getID () {
		 return this.ID;
	}
}
