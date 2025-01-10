package com.mls.Markdown.Note_taking.App.Backend.note;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
@CrossOrigin(value = "http://localhost:4200")
public class NoteController {

    @Autowired
    private NoteService noteService;

    //Needed endpoints
    //1- Provide an endpoint to check the grammar of the note.
    //2- Provide an endpoint to save the note that can be passed in as Markdown text from the body, and save it as .md file.
    //3- Return the HTML version of the Markdown note (rendered note) through another endpoint.
    //4- (Optional) Provide an endpoint to upload


    @GetMapping("/list-all-notes")
    public List<NoteEntity> listAllNotes(){
        return noteService.listAllNotes();
    }

    @PostMapping("/save-note")
    public ResponseEntity<NoteEntity> saveNote(@RequestBody NoteEntity note) {

        return null;
    }
}
