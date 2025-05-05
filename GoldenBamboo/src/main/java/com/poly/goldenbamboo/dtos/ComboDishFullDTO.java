package com.poly.goldenbamboo.dtos;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboDishFullDTO {
    private int id;
    private int comboId;
    private String comboName;
    private int dish1Id;
    private String dish1Name;
    private int dish2Id;
    private String dish2Name;

}
