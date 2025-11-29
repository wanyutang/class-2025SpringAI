const chatBox = document.getElementById("chat-box");
const questionInput = document.getElementById("question");

// 一次性回答: 呼叫 /ollama/ask
function ask() {
	const question = questionInput.value.trim();
	
	if(!question) return;
	
	chatBox.innerText += `You: ${question}\n`;
	questionInput.value = '';
	
	fetch(`http://localhost:8080/ollama/ask?q=${encodeURIComponent(question)}`)
		.then(res => res.text())
		.then(answer => {
			chatBox.innerText += `AI: ${answer}\n\n`;
			chatBox.scrollTo = chatBox.scrollHeight;
		})
		.catch(err => {
			chatBox.innerText += "錯誤呼叫\n";
			console.error(err);
		});
}

// 逐字回答: 呼叫 /ollama/stream
// 透過 EventSource 可以不間斷的持續收到 Server 端傳來的訊息直到 close 為止
// 每收到一個訊息就會觸發 onmessage 方法
function stream() {
	const question = questionInput.value.trim();
		
	if(!question) return;
	
	chatBox.innerText += `You: ${question}\n`;
	questionInput.value = '';
	
	// 建立 SSE 連線(Server-Sent Events)
	// - /ollama/stream => 後端串流 API
	const eventSource = new EventSource(`/ollama/stream?q=${encodeURIComponent(question)}`);
	
	// 先在 chatBox 上印出 AI: 當作前綴字
	chatBox.innetText += 'AI: ';
	
	// 都後端送出一個事件資料(chunk)時, 就會觸發 onmessage
	eventSource.onmessage = function(event) {
		chatBox.innerText += event.data;
		// 讓卷軸能自動滾到最底下, 確保使用者能看到最新內容
		chatBox.scrollTo = chatBox.scrollHeight;
	};
	
	// 當連線失敗或後端關閉時就會觸發 onerror
	eventSource.onerror = function(err) {
		// 關閉 EventSource 連線
		eventSource.close();
		
		chatBox.innerText += "\n[Stream end]\n\n";
	};
	
}
