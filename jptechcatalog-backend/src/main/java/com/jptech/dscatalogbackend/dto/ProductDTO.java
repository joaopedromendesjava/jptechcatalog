package com.jptech.dscatalogbackend.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.jptech.dscatalogbackend.entities.Category;
import com.jptech.dscatalogbackend.entities.Product;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo requerido")
	private String name;
	
	@Column(columnDefinition = "TEXT")
	@NotBlank(message = "Campo requerido")
	private String description;
	
	@Positive(message = "Valor deve ser positivo")
	private Double price;
	
	private String imgUrl;
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO() {
	
	}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}
	
	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.imgUrl = product.getImgUrl();
	}
	
	public ProductDTO(Product product, Set<Category> categories) {
		this(product); //chama o construtor de cima
		categories.forEach(c -> this.categories.add(new CategoryDTO(c)));
		
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public List<CategoryDTO> getcategories() {
		return categories;
	}
}
