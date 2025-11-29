package com.example.demo.controller;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OllamaService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ollama")
public class OllamaController {
	
	private final OllamaService ollamaService;
	private final OllamaChatModel chatModel;
	
	public OllamaController(OllamaService ollamaService, OllamaChatModel chatModel) {
		this.ollamaService = ollamaService;
		this.chatModel = chatModel;
	}
	
	// http://localhost:8080/ollama/ask?q=台灣在哪裡
	@GetMapping("/ask")
	public String ask(@RequestParam String q, @RequestParam(required = false) String model) {
		try {
			return ollamaService.ask(q, model);
		} catch (Throwable e) {
			return ollamaService.ask(q); // 使用預設模型
		}
		
	}
	
	// Prompt: 咒語(要問 AI 的問題)
	// Flux<T>: 一串會陸續出現的資料(一個字一個字的印出)
	// Chunk: 區塊
	// 利用 Flux 將 Chunk 一個一個吐出來
	// http://localhost:8080/ollama/stream?q=台灣在哪裡
	@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> stream(@RequestParam String q, @RequestParam(required = false) String model) {
		return ollamaService.stream(q, model);
	}
	
}
