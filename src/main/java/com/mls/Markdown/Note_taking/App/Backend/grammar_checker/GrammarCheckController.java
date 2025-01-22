package com.mls.Markdown.Note_taking.App.Backend.grammar_checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/grammar")
public class GrammarCheckController {

    @Autowired
    private GrammarCheckerService grammarCheckerService;

    @PostMapping("/check-grammar")
    public Map<String, Object> checkGrammar(@RequestParam String text, @RequestParam String language) {
        return grammarCheckerService.checkGrammar(text, language);
    }
}
