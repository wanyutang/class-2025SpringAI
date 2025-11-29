package com.example.demo.controller;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OllamaService;

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
	public String ask(@RequestParam String q) {
		return ollamaService.ask(q);
	}
	
}
