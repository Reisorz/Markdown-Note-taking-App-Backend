package com.mls.Markdown.Note_taking.App.Backend.grammar_checker;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrammarRequest {
    private String text;
    private String language;
}
