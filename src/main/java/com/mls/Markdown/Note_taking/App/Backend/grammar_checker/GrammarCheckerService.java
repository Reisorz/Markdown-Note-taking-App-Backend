package com.mls.Markdown.Note_taking.App.Backend.grammar_checker;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GrammarCheckerService {

    private final WebClient webClient;

    public GrammarCheckerService(){
        this.webClient = WebClient.create("https://api.languagetool.org/v2");
    }

    public Map<String, Object> checkGrammar(String text, String language) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/check")
                        .queryParam("text", text)
                        .queryParam("language", language)
                        .build())
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
