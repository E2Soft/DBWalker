package walker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class DBConnection {

	private static Connection conn;

	public static Connection getConnection() {
		if (conn == null)
			try {
				open();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		return conn;
	}
	
	/**
	 * ucitaj iz default properties fajla
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void open() throws ClassNotFoundException, SQLException {
		if (conn != null)
			return;
		ResourceBundle bundle = PropertyResourceBundle.getBundle("DBConnection"); //ime fajla
		String driver = bundle.getString("driver"); //Ime parametara
		String url = bundle.getString("url");
		String username = bundle.getString("username");  
		String password = bundle.getString("password");
		
		open(driver, url, username, password);
	}
	
	public static void open(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException 
	{
		close(); // if already open close it
		
		Class.forName(driver); //Registrovanje drajvera
		conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);
		conn.setReadOnly(true);
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	}

	public static void close() {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
