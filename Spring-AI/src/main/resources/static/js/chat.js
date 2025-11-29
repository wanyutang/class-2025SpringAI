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