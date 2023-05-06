/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.dao.repository;

import org.kivilev.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
