package com.mls.Markdown.Note_taking.App.Backend.grammar_checker;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GrammarCheckerService {

    private final WebClient webClient;

    public GrammarCheckerService(){
        this.webClient = WebClient.create("https://api.languagetool.org/v2");
    }

    public List<GrammarResponse> checkGrammar(String text, String language) {
        LanguageToolEntity response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/check")
                        .queryParam("text", text)
                        .queryParam("language", language)
                        .build())
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(LanguageToolEntity.class)
                .block();

        List<GrammarResponse> matches = new ArrayList<GrammarResponse>();
        for (MatchesEntity match: response.getMatches()) {

            List<String> replacementValues = match.getReplacements().stream()
                    .map(MatchesEntity.Replacement::getValue)
                    .toList();

            GrammarResponse matchToAdd = GrammarResponse.builder()
                    .message(match.getMessage())
                    .sentence(match.getSentence())
                    .replacements(replacementValues)
                    .build();
            matches.add(matchToAdd);
        }

        return matches;
    }
}
