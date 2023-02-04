package org.kivilev.service;

import lombok.RequiredArgsConstructor;
import org.kivilev.dao.repository.AuthorRepository;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    @Override
    public Author getAuthor(String id) {
        var authorOptional = authorRepository.findById(id);
        return authorOptional.orElseThrow(ObjectNotFoundException::new);
    }
}
