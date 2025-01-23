package com.mls.Markdown.Note_taking.App.Backend.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;


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

    public NoteEntity createNote(NoteRequest noteRequest) throws IOException {

        //Create the .md file
        String baseFileName = noteRequest.getTitle().replaceAll("\\s+", "_");
        String fileName = baseFileName + ".md";
        Path filePath = Paths.get(uploadsDirectory, fileName);

        //Make sure uploads directory exist
        if(!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        //Check duplicated file names
        int count = 0;
        while (Files.exists(filePath)) {
            count++;
            fileName = baseFileName + "_" + count + ".md";
            filePath = Paths.get(uploadsDirectory, fileName);
        }


        //Write the content in the .md file
        Files.writeString(filePath, noteRequest.getMarkdownContent());

        //Convert Markdonw content into HTML
        String htmlContent = convertMarkdownToHtml(noteRequest.getMarkdownContent());

        //Create note and save it in the database
        NoteEntity note = new NoteEntity();
        if (count != 0) {
            note.setTitle(noteRequest.getTitle() + " " + count);
        } else {
            note.setTitle(noteRequest.getTitle());
        }
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

    public NoteEntity updateNote (NoteEntity note) throws IOException {

        // Find the existing note by ID
        NoteEntity existingNote = noteRepository.findById(note.getId()).orElse(null);

        // Get the old file path
        Path oldFilePath = Paths.get(existingNote.getFilePath());

        // New file name
        String newBaseFileName = note.getTitle().replaceAll("\\s+", "_");
        String newFileName = newBaseFileName + ".md";
        Path newFilePath = Paths.get(uploadsDirectory, newFileName);

        // Deletes old file
        Files.delete(oldFilePath);

        //Check duplicated file names
        int count = 0;
        while (Files.exists(newFilePath)) {
            count++;
            newFileName = newBaseFileName + "_" + count + ".md";
            newFilePath = Paths.get(uploadsDirectory, newFileName);
        }

        //Creates new file and writes markdown content
        Files.writeString(newFilePath, note.getMarkdownContent());

        // Convert it to HTML
        String htmlContent = convertMarkdownToHtml(note.getMarkdownContent());

        // Update the Note entity in DB
        if (count != 0) {
            existingNote.setTitle(note.getTitle() + " " + count);
        } else {
            existingNote.setTitle(note.getTitle());
        }
        existingNote.setMarkdownContent(note.getMarkdownContent());
        existingNote.setHtmlContent(htmlContent);
        existingNote.setFilePath(newFilePath.toString());
        return noteRepository.save(existingNote);
    }

    public NoteEntity uploadNote(MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename();
        String fileName = originalFileName.substring(0, originalFileName.length() - 3);
        String fileContent = new String(file.getBytes());

        //Create the .md file
        String baseFileName = fileName.replaceAll("\\s+", "_");
        String newFileName = baseFileName + ".md";
        Path filePath = Paths.get(uploadsDirectory, newFileName);

        //Make sure uploads directory exist
        if(!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        //Check duplicated file names
        int count = 0;
        while (Files.exists(filePath)) {
            count++;
            newFileName = baseFileName + "_" + count + ".md";
            filePath = Paths.get(uploadsDirectory, newFileName);
        }

        //Write the content in the .md file
        Files.writeString(filePath, fileContent);

        //Convert Markdonw content into HTML
        String htmlContent = convertMarkdownToHtml(fileContent);

        //Create note and save it in the database
        NoteEntity note = new NoteEntity();
        if (count != 0) {
            note.setTitle(fileName + " " + count);
        } else {
            note.setTitle(fileName);
        }
        note.setHtmlContent(htmlContent);
        note.setMarkdownContent(fileContent);
        note.setFilePath(filePath.toString());
        return noteRepository.save(note);
    }

    public void deleteNote(NoteEntity note) throws IOException {
        noteRepository.delete(note);
        Path filePath = Paths.get(note.getFilePath());
        Files.delete(filePath);
    }


}
