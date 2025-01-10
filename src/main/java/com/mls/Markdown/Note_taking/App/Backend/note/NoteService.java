package com.mls.Markdown.Note_taking.App.Backend.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<NoteEntity> listAllNotes() {
        return noteRepository.findAll();
    }

    public NoteEntity addNote(NoteRequest noteRequest){

        return null;
    }
}
