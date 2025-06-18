package com.poly.goldenbamboo.services;


import com.poly.goldenbamboo.repositories.ComboDishJPA;
import com.poly.goldenbamboo.repositories.ComboJPA;
import com.poly.goldenbamboo.repositories.DishJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ComboDishService {

    @Autowired
    private ComboDishJPA comboDishJPA;

    @Autowired
    private ComboJPA comboJPA;

    @Autowired
    private DishJPA dishJPA;

    
}
