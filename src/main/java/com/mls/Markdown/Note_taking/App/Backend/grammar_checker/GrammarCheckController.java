package com.mls.Markdown.Note_taking.App.Backend.grammar_checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grammar")
@CrossOrigin(origins = "http://localhost:4200")
public class GrammarCheckController {

    @Autowired
    private GrammarCheckerService grammarCheckerService;

    @PostMapping("/check-grammar")
    public List<GrammarResponse> checkGrammar(@RequestBody GrammarRequest request) {
        return grammarCheckerService.checkGrammar(request.getText(), request.getLanguage());
    }
}
