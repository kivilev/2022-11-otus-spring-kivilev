/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewBookRequestDto {
    @NotBlank
    private String title;
    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    private Integer createdYear;
    @NotNull
    private String authorId;
    @NotNull
    private String genreId;
}
