package com.example.demo.service;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.retry.TransientAiException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Flux;

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
		
		if(useModel.contains("finance")) {
			useModel = "martain7r/finance-llama-8b:fp16";
		}
		
		// 變更模型
		OllamaChatOptions options = OllamaChatOptions.builder()
				.model(useModel)
				.build();
		
		// 咒語
		Prompt prompt = new Prompt(q, options);
		
		return chatModel.call(prompt).getResult().getOutput().getText();
		
	}
	
	// 使用預設模型的 stream
	public Flux<String> stream(String q) {
		return chatModel.stream(new Prompt(q))
				.map(chunk -> chunk.getResult().getOutput().getText());
	}
	
	// 可以指定模型的 stream
	public Flux<String> stream(String q, String useModel) {
		
		if (useModel == null || useModel.isBlank()) {
			return stream(q);
		}
		
		if(useModel.contains("finance")) {
			useModel = "martain7r/finance-llama-8b:fp16";
		}
		
		// 變更模型
		OllamaChatOptions options = OllamaChatOptions.builder()
				.model(useModel)
				.build();
		
		// 咒語
		Prompt prompt = new Prompt(q, options);
		
		return chatModel.stream(prompt)
				.map(chunk -> chunk.getResult().getOutput().getText())
				.onErrorResume(WebClientResponseException.class, e -> { // 模型錯誤
					return stream(q); // 改成使用預設模型
				})
				.onErrorResume(TransientAiException.class, e -> {
					return Flux.just("記憶體不足錯誤");
				})
				.onErrorResume(Exception.class, e -> {
					return Flux.just("其他錯誤");
				});
		
	}
	
	
}
