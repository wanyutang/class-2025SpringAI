package ollama.generate;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TwseDataDownload {
	
	public static void main(String[] args) {
		String jsonString = getStringData();
		System.out.println(jsonString);
	}
	
	public static String getStringData() {
		String url = "https://www.twse.com.tw/rwd/zh/afterTrading/BWIBBU_d?response=json&date=20251107";
		
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
				.url(url)
				.build();
		
		try(Response response = client.newCall(request).execute()) {
			
			if(response.isSuccessful() && response.body() != null) {
				String jsonString = response.body().string();
				return jsonString;
			}
			
		} catch (IOException e) {
			System.err.println(e);
		}
		
		return null;
	}
	
}
