package com.mls.Markdown.Note_taking.App.Backend.note;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/note")
@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping("/create-note")
    public ResponseEntity<NoteEntity> createNote(@RequestBody NoteRequest noteRequest) {
        NoteEntity note = new NoteEntity();
        try {
            note = noteService.createNote(noteRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(note);
    }

    @PutMapping("/update-note")
    public ResponseEntity<NoteEntity> updateNote(@RequestBody NoteEntity note) {
        NoteEntity existingNote = new NoteEntity();
        try {
            existingNote = noteService.updateNote(note);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(existingNote);
    }

    @PostMapping("/upload-note")
    public ResponseEntity<NoteEntity> uploadNote(@RequestParam("file")MultipartFile file) {
        NoteEntity uploadedNote = new NoteEntity();
        try {
            uploadedNote = noteService.uploadNote(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(uploadedNote);
    }

    @GetMapping("/get-note-by-id/{id}")
    public ResponseEntity<NoteEntity> getNoteById (@PathVariable Long id) {
        NoteEntity note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/delete-note/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteNoteById (@PathVariable Long id) {
        NoteEntity note = noteService.getNoteById(id);
        if (note == null){
            throw new RuntimeException("Note with id " + id + " not found.");
        }
        try {
            noteService.deleteNote(note);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
