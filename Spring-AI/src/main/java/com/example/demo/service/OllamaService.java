package com.example.demo.service;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {
	
	private final OllamaChatModel chatModel;
	
	// Spring 自動注入機制會利用建構子注入參數
	public OllamaService(OllamaChatModel chatModel) {
		this.chatModel = chatModel;
	}
	
	// 使用預設模型的 ask
	public String ask(String q) {
		return chatModel.call(q);
	}
	
	// 可以指定模型的 ask
	public String ask(String q, String useModel) {
		if(useModel == null) {
			return ask(q);
		}
		
		// 變更模型
		OllamaChatOptions options = OllamaChatOptions.builder()
				.model(useModel)
				.build();
		
		// 咒語
		Prompt prompt = new Prompt(q, options);
		
		return chatModel.call(prompt).getResult().getOutput().getText();
		
	}
	
	
}
