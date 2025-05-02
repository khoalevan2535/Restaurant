package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Accounts")
public class AccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="create_at")
	private Timestamp createAt;

	private String name;

	private String password;

	private String phone;

	private boolean status;

	@Column(name="update_at")
	private Timestamp updateAt;

	//bi-directional many-to-one association to BranchEntity
	@ManyToOne
	@JsonIgnore
	private BranchEntity branch;

	//bi-directional many-to-one association to RoleEntity
	@ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private RoleEntity role;

	//bi-directional many-to-one association to OrderEntity
	@OneToMany(mappedBy="account")
	@JsonIgnore
	private List<OrderEntity> orders;

	//bi-directional many-to-one association to ReservationEntity
	@OneToMany(mappedBy="account")
	@JsonIgnore
	private List<ReservationEntity> reservations;

}