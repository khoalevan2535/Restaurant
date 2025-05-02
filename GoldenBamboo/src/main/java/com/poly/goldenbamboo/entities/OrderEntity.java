package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Orders")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="order_date")
	private Timestamp orderDate;

	@Column(name = "payment_method")
    private String paymentMethod;

	private BigDecimal prepay;

	private boolean status;

	@Column(name="total_amount")
	private BigDecimal totalAmount;

	//bi-directional many-to-one association to OrderDetailEntity
	@OneToMany(mappedBy="order")
	@JsonIgnore
	private List<OrderDetailEntity> orderDetails;

	//bi-directional many-to-one association to AccountEntity
	@ManyToOne
	@JoinColumn(name = "account_id")
	@JsonIgnore
	private AccountEntity account;

	//bi-directional many-to-one association to BranchEntity
	@ManyToOne
	@JsonIgnore
	private BranchEntity branch;

	//bi-directional many-to-one association to TableEntity
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "table_id")
	private TableEntity table;

	

}