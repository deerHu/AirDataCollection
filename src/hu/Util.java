package hu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Util {
	static final String[] columnStr = { "area", "position_name", "aqi", "co",
			"co_24h", "no2", "no2_24h", "o3", "o3_24h", "o3_8h", "o3_8h_24h",
			"pm10", "pm10_24h", "pm2_5", "pm2_5_24h", "primary_pollutant",
			"quality", "so2", "so2_24h", "station_code", "time_point" };

	public String getJsonFromHttp() {
		String strUrl = "http://pm25.in/api/querys/all_cities.json?token=cD83yNNiFmduANwX8CUs";
		StringBuilder sb = new StringBuilder();

		URL url;
		try {
			url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream(), "UTF-8"));// ����һ��BufferedReader������ȡ�ļ�
			String str = null;
			while ((str = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				sb.append(str);
			}
			br.close();
			System.out.println(sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// File file = new File("all_cities.json");
		// try {
		// BufferedReader br = new BufferedReader(new InputStreamReader(
		// new FileInputStream(file), "UTF-8"));// ����һ��BufferedReader������ȡ�ļ�
		// String str = null;
		// while ((str = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
		// sb.append(str);
		// }
		// br.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return sb.toString();
	}

	public String loadJsonString() {// ��ȡ���ص�json�����ַ���,����ʵ�ִӱ��ػ�ȡ���Ľ���ֱ�Ӵ�����down
		StringBuilder sb = new StringBuilder();
		File file = new File("all_cities.json");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));// ����һ��BufferedReader������ȡ�ļ�
			String str = null;
			while ((str = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				sb.append(str);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public List<List<Object>> parseJson() {
		String jsonString = getJsonFromHttp();// �����϶�ȡjson���������浽���ݿ�
		// List��Ƕ��List����ÿ�����ݱ���ΪList����
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
}