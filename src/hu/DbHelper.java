package hu;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DbHelper {
	Properties prop;
	InputStream is;
	String url;
	String user;
	String password;
	String driver = "com.mysql.jdbc.Driver";

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	private void loadConfig() {
		prop = new Properties();
		is = getClass().getResourceAsStream("/db.properties");

		try {
			prop.load(is);

			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			System.out.println(url + " " + user + " " + password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Connection getConnection() {
		try {
			Class.forName(driver);
			loadConfig(); // º”‘ÿ≈‰÷√Œƒº˛
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public ResultSet executeQuery(String sql) {
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public void executeUpdate(String sql, Object[] obj) {
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setObject(0, obj);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeAll() {
		try {
			if (null != rs) {
				rs.close();
			}
			if (null != pstmt) {
				pstmt.close();
			}
			if (null != conn) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String sql = "select * from city";

		DbHelper db = new DbHelper();
		Connection conn = db.getConnection();
		ResultSet rs = db.executeQuery(sql);

		while (rs.next()) {
			String name = rs.getString("Name");
			String countryCode = rs.getString("CountryCode");
			String district = rs.getString("District");
			String population = rs.getString("Population");

			System.out.println(name + " " + countryCode + " " + district + " "
					+ population);
		}
	}
}
