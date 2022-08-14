package com.smk.blog.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	@NotEmpty
	@Size(min =3,max = 18)
	private String categoryTitle;
	@NotEmpty
	@Size(min = 10, max =50)
	private String categoryDescription;
}
