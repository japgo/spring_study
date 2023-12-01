package com.smyr.showmeyourrecipe.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String content;

    public PostRequest( String title, String content ) {
        this.title = title;
        this.content = content;
    }
}