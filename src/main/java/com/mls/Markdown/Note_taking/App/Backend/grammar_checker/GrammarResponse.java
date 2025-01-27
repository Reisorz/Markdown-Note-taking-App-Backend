package com.mls.Markdown.Note_taking.App.Backend.grammar_checker;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrammarResponse implements Serializable {
    private String message;
    private List<String> replacements;
    private String sentence;
}
