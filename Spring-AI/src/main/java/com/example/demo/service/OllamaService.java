package com.example.demo.service;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {
	
	private OllamaChatModel chatModel;
	
	// Spring 自動注入機制會利用建構子注入參數
	public OllamaService(OllamaChatModel chatModel) {
		this.chatModel = chatModel;
	}
	
	public String ask(String q) {
		return chatModel.call(q);
	}
}
