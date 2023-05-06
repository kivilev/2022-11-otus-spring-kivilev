/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class BookChangeRequestDto {
    @NotNull
    private String id;
    @NotBlank
    private String title;
    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    private Integer createdYear;
    @NotNull
    @Positive
    private String authorId;
    @NotNull
    @Positive
    private String genreId;
}
