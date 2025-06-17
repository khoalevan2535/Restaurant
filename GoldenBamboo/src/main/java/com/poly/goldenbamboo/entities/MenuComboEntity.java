package com.poly.goldenbamboo.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "menu_combos") 
public class MenuComboEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Liên kết với ComboEntity
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "combo_id", nullable = false) 
    @NotNull(message = "Combo không được để trống")
    @JsonIgnore
    private ComboEntity combo;

    // Liên kết với MenuEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull(message = "Menu không được để trống")
    @JsonIgnore
    private MenuEntity menu;
}