package com.allergyprediction;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private final String productBarcode;
    private final String productName;
    private final HashMap<String, Ingredient> productIngredients;

    public Product (String productBarcode, String productName) {
        this.productBarcode = productBarcode;
        this.productName = productName;
        this.productIngredients = new HashMap<String, Ingredient>();
    }

    public String getProductBarcode() {
        return productBarcode;
    }
    public String getProductName() {
        return productName;
    }
    public HashMap<String, Ingredient> getProductIngredients() {
        return productIngredients;
    }

    ArrayList<String> getProductIngredientsNames() {
        ArrayList<String> productIngredientsNames = new ArrayList<String>();
        for (String ingredientName : productIngredients.keySet()) {
            productIngredientsNames.add(ingredientName);
        }
        return productIngredientsNames;
    }

    public void addIngredient(String name, Ingredient ingredient) {
        assert ingredient != null;
        productIngredients.put(name, ingredient);
    }
    public void delIngredient(String name) {
        productIngredients.remove(name);
    }
}
