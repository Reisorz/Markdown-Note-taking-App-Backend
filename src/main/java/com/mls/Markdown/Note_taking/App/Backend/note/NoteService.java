package com.mls.Markdown.Note_taking.App.Backend.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;


@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Value("${uploads.directory}")
    private String uploadsDirectory;

    public List<NoteEntity> listAllNotes() {
        return noteRepository.findAll();
    }

    public NoteEntity getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public NoteEntity addNote(NoteRequest noteRequest) throws IOException {

        //Create the .md file
        String fileName = noteRequest.getTitle().replaceAll("\\s+", "_") + ".md";
        Path filePath = Paths.get(uploadsDirectory, fileName);

        //Make sure uploads directory exist
        if(!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        //Write the content in the .md file
        Files.writeString(filePath, noteRequest.getMarkdownContent());

        //Convert Markdonw content into HTML
        String htmlContent = convertMarkdownToHtml(noteRequest.getMarkdownContent());

        //Create note and save it in the database
        NoteEntity note = new NoteEntity();
        note.setTitle(noteRequest.getTitle());
        note.setHtmlContent(htmlContent);
        note.setMarkdownContent(noteRequest.getMarkdownContent());
        note.setFilePath(filePath.toString());
        return noteRepository.save(note);

    }

    public String convertMarkdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(parser.parse(markdown));
    }


}
