package com.example.db.bean;

import com.example.db.entity.Ingredient;
import com.example.db.entity.Raw;
import com.example.db.entity.ReadyProduct;
import com.example.db.service.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@Component
@ManagedBean
@SessionScoped
@Getter
@Setter
public class IngredientBean {
    private Long readyProductId;
    private Long rawId;
    private Integer amount;
    private Ingredient ingredient;
    private List<Ingredient> ingredients;
    private List<Raw> raws;
    private List<ReadyProduct> readyProducts;

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RawService rawService;
    @Autowired
    private ReadyProductService readyProductService;

    public void init() {
        ingredients = ingredientService.getAll();
        raws = rawService.getAll();
        readyProducts = readyProductService.getAll();
    }

    public void create() {
        Ingredient ingredient = new Ingredient();
        ingredient.setReadyProduct(readyProductService.getById(readyProductId));
        ingredient.setRaw(rawService.getById(rawId));
        ingredient.setAmount(amount);
        ingredientService.save(ingredient);
        clean();
    }

    public void delete(Long id) {
        ingredientService.deleteById(id);
    }

    public String navigateToUpdate(Long id) {
        ingredient = ingredientService.getById(id);
        return "ingredient_update.xhtml?faces-redirect=true";
    }

    public void update() {
        ingredient.setReadyProduct(readyProductService.getById(readyProductId));
        ingredient.setRaw(rawService.getById(rawId));
        ingredientService.update(ingredient);
        ingredient.setAmount(null);
        ingredient.setRaw(null);
        ingredient.setReadyProduct(null);
    }

    public void clean() {
        readyProductId = null;
        rawId = null;
        amount = null;
    }
}
