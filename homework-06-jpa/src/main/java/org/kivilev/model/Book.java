package org.kivilev.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book",
        indexes = {
                @Index(name = "book_author_id_idx", columnList = "author_id"),
                @Index(name = "book_genre_id_idx", columnList = "genre_id")
        })
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "created_year", nullable = false)
    private Integer createdYear;
    @OneToOne(targetEntity = Author.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "book_author_fk"))
    private Author author;
    @OneToOne(targetEntity = Genre.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id", nullable = false, foreignKey = @ForeignKey(name = "book_genre_fk"))
    private Genre genre;
    @OneToMany(targetEntity = BookComment.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "book_comment_fk"))
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 2)
    private List<BookComment> comments;
}