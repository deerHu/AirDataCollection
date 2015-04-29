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
	List<String> list;

	public String getHttp(String url) {

		return "";
	}

	public String loadJsonString() {
		File file = new File("all_cities.json");
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));// 构造一个BufferedReader类来读取文件
			String str = null;
			while ((str = br.readLine()) != null) {// 使用readLine方法，一次读一行
				sb.append(str);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public List<List<String>> parseJson(String jsonString) {
		/*
		 * List里嵌套List，将每行数据保存为List！！
		 */
		list = new ArrayList<String>();
		List<List<String>> totalList = new ArrayList<List<String>>();
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject temp = (JSONObject) jsonArray.get(i);
			String aqi = temp.getString("aqi");
			String area = temp.getString("area");
			String co = temp.getString("co");
			String co_24h = temp.getString("co_24h");
			String no2 = temp.getString("no2");
			String no2_24h = temp.getString("no2_24h");
			String o3 = temp.getString("o3");
			String o3_24h = temp.getString("o3_24h");
			String o3_8h = temp.getString("o3_8h");
			String o3_8h_24h = temp.getString("o3_8h_24h");
			String pm10 = temp.getString("pm10");
			String pm10_24h = temp.getString("pm10_24h");
			String pm2_5 = temp.getString("pm2_5");
			String pm2_5_24 = temp.getString("pm2_5_24h");
			String position_name = temp.getString("position_name");
			String primary_pollutant = temp.getString("primary_pollutant");
			String quality = temp.getString("quality");
			String so2 = temp.getString("so2");
			String so2_24h = temp.getString("so2_24h");
			String station_code = temp.getString("station_code");
			String time_point = temp.getString("time_point");

			list.add(aqi);
			list.add(area);
			list.add(co);
			list.add(co_24h);
			list.add(no2);
			list.add(no2_24h);
			list.add(o3);
			list.add(o3_24h);
			list.add(o3_8h);
			list.add(o3_8h_24h);
			list.add(pm10);
			list.add(pm10_24h);
			list.add(pm2_5);
			list.add(pm2_5_24);
			list.add(position_name);
			list.add(primary_pollutant);
			list.add(quality);
			list.add(so2);
			list.add(so2_24h);
			list.add(station_code);
			list.add(time_point);

			totalList.add(list);
			// System.out.println(aqi + " " + area + " " + co + " " + co_24h +
			// " "
			// + no2 + " " + no2_24h + " " + o3 + " " + o3_24h + " "
			// + o3_8h + " " + o3_8h_24h + " " + pm10 + " " + pm10_24h
			// + " " + pm2_5 + " " + pm2_5_24 + " " + position_name + " "
			// + primary_pollutant + " " + quality + " " + so2 + " "
			// + so2_24h + " " + station_code + " " + time_point);
		}
		return totalList;
	}

	public static void main(String[] args) {
		Util util = new Util();
		String str = util.loadJsonString();

		// System.out.println(str);
		List<List<String>> list = util.parseJson(str);
		for (List<String> l : list) {
			System.out.println(l);
		}
	}
}