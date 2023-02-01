package org.kivilev.ui;

import lombok.RequiredArgsConstructor;
import org.kivilev.service.BookCommentService;
import org.kivilev.service.io.input.InputBookCommentService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookCommentUiServiceImpl implements BookCommentUiService {

    private final BookCommentService bookCommentService;
    private final InputBookCommentService inputBookCommentService;

    @Override
    public void addBookComment() {
        var newData = inputBookCommentService.getNewComment();
        bookCommentService.addComment(newData);
    }

    @Override
    public void removeBookComment() {
        var removeId = inputBookCommentService.getRemoveId();
        bookCommentService.removeComment(removeId);
    }
}
