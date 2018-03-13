package bdconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSpaceConnection {

	private final String DATEBASEDRIVER = "com.mysql.jdbc.Driver";
	private final String DATEBASEURL = "jdbc:mysql://127.0.0.1:3306/test?";
	private final String DATEBASEUSER = "root";
	private final String DATEBASEDUSERPASSWORD = "root";
	private Connection conn = null;

	public DBSpaceConnection() {
		try {
			Class.forName(DATEBASEDRIVER);
			this.conn = DriverManager.getConnection(DATEBASEURL, DATEBASEUSER, DATEBASEDUSERPASSWORD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		return this.conn;
	}

}
