package ChatApp;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class HandlerBDD {
    // Connect to your database.
    private String name = "tp_servlet_011";
	private String password = "cei6neiJ";
	private String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_servlet_011?useSSL=false";
	private Agent agent;
	private Connection connection;
	
	public HandlerBDD(Agent agent) throws SQLException {
		this.agent=agent;
		OpenConnection();
		createTableUser();
	}

	public void OpenConnection() {
		try {
			/*Load driver*/
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				connection=DriverManager.getConnection(url,name,password);
				System.out.println("Connection established");
			}
			catch(SQLException e) {
				System.err.println(e);
				e.printStackTrace();
			}
		}
		catch (ClassNotFoundException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public void CloseConnection() {
		try {
			if(connection != null) {
				connection.close();
			}
		}
		catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void createTableUser() throws SQLException {
		String createTableUser = "CREATE TABLE IF NOT EXISTS user (" +
				"id INTEGER NOT NULL AUTO_INCREMENT,"
				+"username TEXT NOT NULL," +
				"PRIMARY KEY (id))";

		Statement statement = this.connection.createStatement();
		statement.execute(createTableUser);
	}
	
	private void createTableMessage() throws SQLException {
		String createTableMessage = "CREATE TABLE IF NOT EXISTS message (\r\n"
				+ "    id_message      INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
				+ "    id_conversation INTEGER REFERENCES conversation (id_conversation) \r\n" + "                            NOT NULL,\r\n"
				+ "    content         BLOB,\r\n"
				+ "    date            INTEGER NOT NULL\r\n" + ");";

		Statement statement = this.connection.createStatement();
		statement.execute(createTableMessage);
	}
	
	private void createTableConversation() throws SQLException {
		String createTableConversation = "CREATE TABLE IF NOT EXISTS conversation (\r\n"
				+ "    id_conversation INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
				+ "    id_emetteur     INTEGER REFERENCES user (id) \r\n" + "                            NOT NULL,\r\n"
				+ "    id_recepteur    INTEGER REFERENCES user (id) \r\n" + "                            NOT NULL\r\n" + ");";

		Statement statement = this.connection.createStatement();
		statement.execute(createTableConversation);

	}
	
	public ArrayList<Message> getHistoriqueMessages(String idUser2) {
		String idUser1 = Integer.toString(agent.getPseudoHandler().getMain_User().getID());
		String str = "datetime(d1,'unixepoch','localtime')";
		return null;
	}
	
	public int getIDUser(String username) throws SQLException {
		String getIDRequest = "SELECT id" + "FROM user" + "WHERE username = ? ;";
		PreparedStatement PrepStatement = this.connection.prepareStatement(getIDRequest);
		PrepStatement.setString(1, username);
		ResultSet res = PrepStatement.executeQuery();

		if (res.next()) {
			return res.getInt("id");
		}
		return -1;
	}


	public void insertUser(String username) throws SQLException {
		String insertUserRequest = "INSERT INTO user (username)" + "VALUES (?);";
		PreparedStatement PrepStatement = this.connection.prepareStatement(insertUserRequest);
		PrepStatement.setString(1, username);
		int nb = PrepStatement.executeUpdate();

		System.out.println("Nombre de ligne(s) insérée(s) : " + nb);

	}
	
	private int getIDConversation(int idUser1, int idUser2) throws SQLException {
		String getIDRequest = "SELECT id_conversation " + "FROM conversation" + "WHERE id_emetteur = ?" + "AND id_recepteur = ? ;";
		PreparedStatement PrepStatement = this.connection.prepareStatement(getIDRequest);
		PrepStatement.setInt(1, idUser1);
		PrepStatement.setInt(2, idUser2);
		ResultSet res = PrepStatement.executeQuery();

		if (res.next()) {
			return res.getInt("id_conversation");
		}
		return -1;
	}
	
	private void insertConversation(int idUser1, int idUser2) throws SQLException {
		String insertConversationRequest = "INSERT INTO conversation (id_emetteur, id_recepteur)" + "VALUES (?, ?);";
		PreparedStatement PrepStatement = this.connection.prepareStatement(insertConversationRequest);
		PrepStatement.setInt(1, idUser1);
		PrepStatement.setInt(2, idUser2);
		int nb = PrepStatement.executeUpdate();

		System.out.println("Nombre de ligne(s) insérée(s) : " + nb);
	}
	
	private byte[] processMessageContent(Message m) {
			StringMessage mTxt = (StringMessage) m;
			String mContent = mTxt.getContentString();
			byte[] encodedContent = Base64.getEncoder().encode(mContent.getBytes());
			return encodedContent;
	}
	
	public int insertMessage(String user1, String user2, int idConversation, Message m) {
		
		try {
	
//			int idUser1 = this.getIDUser(user1);
//			int idUser2 = this.getIDUser(user2);
//			int idConversation = this.getIDConversation(idUser1, idUser2);
			String dateMessage = m.getFormatTime();
			byte[] content = this.processMessageContent(m);
			String insertMessageRequest = "INSERT INTO message(id_conversation, content, date) "
					+ "VALUES (?, ?, ?);";
			
			PreparedStatement PrepStatement = this.connection.prepareStatement(insertMessageRequest);
			PrepStatement.setInt(1, idConversation);
			PrepStatement.setBytes(2, content);
			PrepStatement.setString(3, dateMessage);
			
			int nb = PrepStatement.executeUpdate();

			System.out.println("Nombre de ligne(s) insérée(s) : " + nb);
		}
		catch(SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return 1;
	}
	
	
}