package ollama.chat.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataFetcher {
	
	// 從 chat_prompts 資料表中讀取所有預設提示詞
	public static List<String> loadPromptsFromDB() {
		
		List<String> prompts = new ArrayList<>();
		String sql = "select prompt_text from chat_prompts";
		
		try(Connection conn = DatabaseUtil.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				prompts.add(rs.getString("prompt_text"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return prompts;
	}
	
}
