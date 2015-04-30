package hu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Util {
	static final String[] columnStr = { "aqi", "area", "co", "co_24h", "no2",
			"no2_24h", "o3", "o3_24h", "o3_8h", "o3_8h_24h", "pm10",
			"pm10_24h", "pm2_5", "pm2_5_24h", "position_name",
			"primary_pollutant", "quality", "so2", "so2_24h", "station_code",
			"time_point" };

	public String getHttp(String url) {

		return "";
	}

	public String loadJsonString() {// 获取返回的json数据字符串,初步实现从本地获取。改进：直接从网上down
		StringBuilder sb = new StringBuilder();
		// File file = new File("all_cities.json");
		// try {
		// BufferedReader br = new BufferedReader(new InputStreamReader(
		// new FileInputStream(file), "UTF-8"));// 构造一个BufferedReader类来读取文件
		// String str = null;
		// while ((str = br.readLine()) != null) {// 使用readLine方法，一次读一行
		// sb.append(str);
		// }
		// br.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return sb.toString();
	}

	public List<List<Object>> parseJson() {
		String jsonString = loadJsonString();
		// List里嵌套List，将每行数据保存为List！！
		List<Object> list = new ArrayList<Object>();
		List<List<Object>> totalList = new ArrayList<List<Object>>();
		JSONArray jsonArray = JSONArray.fromObject(jsonString);

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject temp = (JSONObject) jsonArray.get(i);
			Object[] o = new Object[21];
			for (int j = 0; j < columnStr.length; j++) {
				o[j] = temp.getString(columnStr[j]);
				list.add(o[j]);
			}

			totalList.add(i, list);
			list = new ArrayList<>();
		}
		return totalList;
	}

	// public static void main(String[] args) {
	// Util util = new Util();
	// String str = util.loadJsonString();
	//
	// List<List<Object>> list = util.parseJson(str);
	//
	// for (List<Object> l : list) {
	// System.out.println(l);
	// }
	// }
}