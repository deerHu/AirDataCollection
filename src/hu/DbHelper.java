package hu;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
			loadConfig(); // 加载配置文件
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

	public PreparedStatement getPstmt() {
		String sqlUpdate = "Insert ignore into airinfo"
				+ "(area,position_name,aqi,co,co_24h,no2,no2_24h,o3,o3_24h,o3_8h,"
				+ "o3_8h_24h,pm10,pm10_24h,pm2_5,pm2_5_24h,primary_pollutant,quality,so2,so2_24h,station_code,time_point)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement(sqlUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pstmt;
	}

	public void executeUpdate(List<List<Object>> gList) {
		pstmt = getPstmt();
		try {
			int count = 1;
			List<Object> list = new ArrayList<>();
			for (int i = 0; i < gList.size(); i++) {
				list = gList.get(i);
				for (int j = 0; j < Util.columnStr.length; j++) {
					pstmt.setObject(j + 1, list.get(j));
				}
				pstmt.addBatch();
				count++;
				if (count % 500 == 0)
					pstmt.executeBatch();

				list.clear();
			}
			pstmt.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeAll();
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

	public List<List<Object>> getDataFromDB() throws Exception {
		List<List<Object>> dataFromDBList = new ArrayList<List<Object>>();
		DbHelper db = new DbHelper();

		String sqlQuery = "select * from airinfo";
		ResultSet rs = db.executeQuery(sqlQuery);

		List<Object> list = new ArrayList<>();
		while (rs.next()) {
			for (int i = 0; i < 22; i++) {
				list.add(rs.getString(i + 1));
			}
			dataFromDBList.add(list);
			list = new ArrayList<Object>(); // 同一个错误！！list必须重新new，否则出错
		}
		return dataFromDBList;
	}
}
