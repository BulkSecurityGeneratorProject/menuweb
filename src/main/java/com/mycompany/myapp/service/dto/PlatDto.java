package com.mycompany.myapp.service.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mycompany.myapp.domain.enumeration.TypePlat;

/**
 * 
 * A Dto representig a plat with its Tags and Its User
 *
 */
public class PlatDto {

	private Long id;

	@NotNull
	@Size(min = 5, max = 50)
	private String name;

	@NotNull
	private TypePlat type;

	@Size(max = 255)
	private String description;

	@NotNull
	private UserDTO createur;

	private Set<String> tags = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypePlat getType() {
		return type;
	}

	public void setType(TypePlat type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserDTO getCreateur() {
		return createur;
	}

	public void setCreateur(UserDTO createur) {
		this.createur = createur;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

}
