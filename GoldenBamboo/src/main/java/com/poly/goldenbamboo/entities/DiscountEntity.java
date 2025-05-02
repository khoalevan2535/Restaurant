package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Discounts")
public class DiscountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable = false)
    private String name; 

    @JsonFormat(pattern = "dd/MM/yyyy")
    private String startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private String endDate;

    private boolean status;

    // Bi-directional many-to-one association to DiscountComboEntity
    @OneToMany(mappedBy="discount")
    @JsonIgnore
    private List<DiscountComboEntity> discountCombos;

    // Bi-directional many-to-one association to DiscountDishEntity
    @OneToMany(mappedBy="discount")
    @JsonIgnore
    private List<DiscountDishEntity> discountDishes;
}
