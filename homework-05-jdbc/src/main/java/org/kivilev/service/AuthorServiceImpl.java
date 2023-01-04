package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.kivilev.dao.AuthorDao;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    @Override
    public Author getAuthor(Long id) {
        var authorOptional = authorDao.getAuthor(id);
        return authorOptional.orElseThrow(ObjectNotFoundException::new);
    }
}
