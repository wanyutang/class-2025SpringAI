package com.example.demo.config;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
	
	private String ollamaApiURL = "http://localhosy:11434";
	private String defaultModel = "llama3.1:8b";
	
	@Bean
	public OllamaApi ollamaApi() {
		return OllamaApi.builder()
				.baseUrl(ollamaApiURL)
				.build();
	}
	
	
	@Bean
	public OllamaChatModel chatModel(OllamaApi ollamaApi) {
		return OllamaChatModel.builder()
				.ollamaApi(ollamaApi)
				.defaultOptions(
						OllamaChatOptions.builder()
							.model(defaultModel)   // 指定模型
							.build()
				)
				.build();
	}
	
}
