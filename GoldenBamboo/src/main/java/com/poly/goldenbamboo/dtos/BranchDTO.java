package com.poly.goldenbamboo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDTO {
	private int id;
	private String name;
	private String address;
	private String description;
	private Integer parentId;
	private boolean status;
}
