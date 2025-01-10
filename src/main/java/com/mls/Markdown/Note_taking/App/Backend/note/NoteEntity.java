package com.mls.Markdown.Note_taking.App.Backend.note;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "notes")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String filePath;
    @Lob
    private String markdownContent;
    @Lob
    private String htmlContent;

}
