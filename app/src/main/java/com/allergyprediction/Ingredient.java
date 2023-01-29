package com.allergyprediction;

import java.util.ArrayList;

public class Ingredient {
    private String ingredientName;
    private ArrayList<String> ingredientList = new ArrayList<String>();

    Ingredient (String ingredientINCI) {
        String ingredientUpperCase = ingredientINCI.toUpperCase();
        this.ingredientName = ingredientUpperCase;
        ingredientList.add(ingredientUpperCase);
    }

    String getIngredientName() {
        return ingredientName;
    }
    ArrayList<String> getIngredientList() {
        return ingredientList;
    }

    public void addIngredientVariant(String ingredientVariant) {
        assert ingredientVariant != null;
        ingredientList.add(ingredientVariant);
    }
    public void delIngredientVariant(String ingredientVariant) {
        ingredientList.remove(ingredientVariant);
    }
}
