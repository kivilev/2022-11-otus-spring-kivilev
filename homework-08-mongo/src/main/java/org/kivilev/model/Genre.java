package org.kivilev.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "genre")
public class Genre {
    @Id
    private String id;
    private String name;
}
