package com.renegz.pnccontroller.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SaveBookDTO {

    private String ISBN;

    @NotEmpty( message = "El titulo no puede estar vacio")
    private String title;

    @NotEmpty
    @Pattern(regexp = "^978-[0-9]-[0-9]{5}-[0-9]{3}-[0-9]$")
    private String category;
}
