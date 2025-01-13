package com.mls.Markdown.Note_taking.App.Backend.note;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoteRequest {

    private String title;
    private String markdownContent;

}
