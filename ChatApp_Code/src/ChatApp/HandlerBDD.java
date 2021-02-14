/*
This class handle the connection to the database and the reqests

@author Vincent Baures and Arnaud Bouisson
@date 2021-02-13
*/

package ChatApp;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HandlerBDD {
    private String login = "tp_servlet_011";
	private String password = "cei6neiJ";
	private String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_servlet_011?useSSL=false";
	private Agent agent;
	private Connection connection;

/*==========CONSTRUCTORS==========*/
	public HandlerBDD(Agent agent){
		this.agent=agent;
		OpenConnection();
	}

/*==========CONNECTION HANDLERS==========*/
	public void OpenConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				connection=DriverManager.getConnection(url,login,password);
			}
			catch(SQLException e) {
				System.err.println(e);
				e.printStackTrace();
			}
		}
		catch (ClassNotFoundException e) {
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

/*This function return the history of a conversation*/
	public ArrayList<Message> getHistoriqueMessages(int ID_Conversation) throws SQLException, IOException, ParseException {
		ArrayList<Message> messages = new ArrayList<>();
		String getMessageRequest = "SELECT * FROM message WHERE id_conversation=? ";
		PreparedStatement PrepStatement = this.connection.prepareStatement(getMessageRequest);
		PrepStatement.setInt(1,ID_Conversation);
		ResultSet res = PrepStatement.executeQuery();
		while (res.next()) {
			if (res.getInt("id_sender") == agent.getPseudoHandler().getMain_User().getID()) {
				User recipient = agent.getPseudoHandler().FindUser(res.getInt("id_receiver"));
				if (res.getBytes("file")==null) {
					StringMessage mess = new StringMessage(recipient, agent.getPseudoHandler().getMain_User(), new String(res.getBytes("content")), new SimpleDateFormat("dd MMMMMMMMM yyyy - HH:mm", Locale.FRANCE).parse(res.getString("date")));
					messages.add(mess);
				}else{
					FileMessage mess = new FileMessage(recipient, agent.getPseudoHandler().getMain_User(), new String(res.getBytes("content")), new SimpleDateFormat("dd MMMMMMMMM yyyy - HH:mm", Locale.FRANCE).parse(res.getString("date")),res.getBytes("file"));
					messages.add(mess);
				}

			} else {
				User sender = agent.getPseudoHandler().FindUser(res.getInt("id_sender"));
				if (res.getBytes("file")==null) {
					StringMessage mess = new StringMessage(agent.getPseudoHandler().getMain_User(), sender, new String(res.getBytes("content")), new SimpleDateFormat("dd MMMMMMMMM yyyy - HH:mm", Locale.FRANCE).parse(res.getString("date")));
					messages.add(mess);
				}else{
					FileMessage mess = new FileMessage(agent.getPseudoHandler().getMain_User(), sender, new String(res.getBytes("content")), new SimpleDateFormat("dd MMMMMMMMM yyyy - HH:mm", Locale.FRANCE).parse(res.getString("date")), res.getBytes("file"));
					messages.add(mess);
				}
			}
		}
		return messages;
	}

/*This function return the ID of a user and also verify if it exists*/
	public int getIDUser(String username, String password) throws SQLException {
		String getIDRequest = "SELECT id FROM user WHERE username = ? AND password = ?";
		PreparedStatement PrepStatement = this.connection.prepareStatement(getIDRequest);
		PrepStatement.setString(1, username);
		PrepStatement.setString(2, password);
		ResultSet res = PrepStatement.executeQuery();

		if (res.next()) {
			return res.getInt("id");
		}
		return -1;

	}

/*This function insert a new user in the database only if it does not already exists*/
	public int insertUser(String username, String password) throws SQLException {
		String insertUserRequest = "INSERT INTO user (username, password) SELECT ? , ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE username=? LIMIT 1)";
		PreparedStatement PrepStatement = this.connection.prepareStatement(insertUserRequest);
		PrepStatement.setString(1, username);
		PrepStatement.setString(2, password);
		PrepStatement.setString(3, username);
		int nb = PrepStatement.executeUpdate();
		return nb;
	}

/*This function return the ID of the conversation between two users*/
	public int getIDConversation(int idUser1, int idUser2) throws SQLException {
		String getIDRequest = "SELECT id_conversation FROM conversation WHERE (id_emetteur = ? AND id_recepteur = ?) OR (id_emetteur = ? AND id_recepteur = ?)";
		PreparedStatement PrepStatement = this.connection.prepareStatement(getIDRequest);
		PrepStatement.setInt(1, idUser1);
		PrepStatement.setInt(2, idUser2);
		PrepStatement.setInt(3, idUser2);
		PrepStatement.setInt(4, idUser1);
		ResultSet res = PrepStatement.executeQuery();
		if (res.next()) {
			return res.getInt("id_conversation");
		}
		return -1;
	}

/*This function insert a new conversation in the database only if it does not already exists*/
	public void insertConversation(int idUser1, int idUser2) throws SQLException {
		String insertConversationRequest = "INSERT INTO conversation (id_emetteur, id_recepteur) SELECT ? , ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM conversation WHERE ((id_emetteur=? AND id_recepteur=?)OR(id_emetteur=? AND id_recepteur=?)) LIMIT 1)";
		PreparedStatement PrepStatement = this.connection.prepareStatement(insertConversationRequest);
		PrepStatement.setInt(1, idUser1);
		PrepStatement.setInt(2, idUser2);
		PrepStatement.setInt(3, idUser1);
		PrepStatement.setInt(4, idUser2);
		PrepStatement.setInt(5, idUser2);
		PrepStatement.setInt(6, idUser1);

	}

/*This function insert a new message in the database*/
	public void insertMessage(User sender, User receiver, int idConversation, String content, String date, byte[] file) {
		try {
			int idUser1 = sender.getID();
			int idUser2 = receiver.getID();
			String insertMessageRequest = "INSERT INTO message(id_conversation, content, date, id_sender, id_receiver, file) "
					+ "VALUES (?, ?, ?, ?, ?,?);";
			PreparedStatement PrepStatement = this.connection.prepareStatement(insertMessageRequest);
			System.out.println("Time out: " + PrepStatement.getQueryTimeout());
			PrepStatement.setInt(1, idConversation);
			PrepStatement.setString(2, content);
			PrepStatement.setString(3, date);
			PrepStatement.setInt(4, idUser1);
			PrepStatement.setInt(5, idUser2);
			PrepStatement.setBytes(6, file);
			PrepStatement.setQueryTimeout(30);
		} catch (SQLException e) {
		}
	}
}