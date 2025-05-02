package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Categories")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	private boolean status;

	//bi-directional many-to-one association to DishEntity
	@OneToMany(mappedBy="category")
	@JsonIgnore
	private List<DishEntity> dishes;

	
}