package com.renegz.pnccontroller.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SaveCategoryDTO {

	@NotEmpty
	@Pattern(regexp = "^978-[0-9]-[0-9]{5}-[0-9]{3}-[0-9]$") //Inicia con 978 seguido de un digito del 0 al 9 seguido de 5 digitos del 0 al 9 etc...
	private String code;
	
	@NotEmpty
    private String name;
	
}
