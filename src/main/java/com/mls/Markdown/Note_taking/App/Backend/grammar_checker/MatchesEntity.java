package com.mls.Markdown.Note_taking.App.Backend.grammar_checker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchesEntity {

    @JsonProperty("message")
    private String message;
    @JsonProperty("sentence")
    private String sentence;
    @JsonProperty("replacements")
    private List<Replacement> replacements;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Replacement {
        private String value;
    }

}
