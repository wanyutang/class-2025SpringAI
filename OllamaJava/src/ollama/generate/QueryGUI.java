package ollama.generate;

import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class QueryGUI extends JFrame {
	// 視覺元件
	private JComboBox<String> modelCombo;
	private JTextField symbolField;
	private JTextField askField;
	private JButton queryBtn;
	private JTextArea resultArea;
	
	// 動畫
	private Timer animTimer;
	private String[] loadingFrames = {"查詢中   ", "查詢中.  ", "查詢中.. ", "查詢中..."};
	private int frameIndex = 0;
	
	// 建立 QueryExecutor 實例
	private QueryExecutor queryExecutor = new QueryExecutor();
	
	private static final String[] MODEL_NAMES = {
			"llama3.1:8b", "qwen3:4b", "qwen3:0.6b", "martain7r/finance-llama-8b:fp16"
	};
	
	private QueryGUI() {
		initUI();
	}
	
	// 初始 UI 配置
	private void initUI() {
		setTitle("我的 AI 財經顧問");
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 按下關閉即結束
		setSize(700, 550);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new QueryGUI().setVisible(true);
		});
	}
	
}
