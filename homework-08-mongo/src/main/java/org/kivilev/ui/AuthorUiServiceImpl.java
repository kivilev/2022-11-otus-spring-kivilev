package org.kivilev.ui;

import lombok.RequiredArgsConstructor;
import org.kivilev.service.AuthorService;
import org.kivilev.service.io.print.AuthorPrintService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorUiServiceImpl implements AuthorUiService {
    private final AuthorService authorService;
    private final AuthorPrintService authorPrintService;

    @Override
    public void showAllAuthors() {
        authorPrintService.print(authorService.getAllAuthors());
    }
}
