package org.kivilev.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "book_comment")
public class BookComment {
    @Id
    String id;
    String text;
    LocalDateTime createDateTime;
    String bookId;

    public BookComment(String text, LocalDateTime createDateTime, String bookId) {
        this.text = text;
        this.createDateTime = createDateTime;
        this.bookId = bookId;
    }
}
