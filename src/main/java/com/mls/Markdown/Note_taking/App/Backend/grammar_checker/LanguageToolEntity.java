package com.mls.Markdown.Note_taking.App.Backend.grammar_checker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageToolEntity {
    @JsonProperty("matches")
    private List<MatchesEntity> matches;
}
