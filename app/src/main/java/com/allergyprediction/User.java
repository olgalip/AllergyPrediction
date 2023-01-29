package com.allergyprediction;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private final String userEmail;
    private final ArrayList<Product> productsAllergy;
    private final ArrayList<Product> productsNoAllergy;
    private final HashMap<Ingredient, Integer> ingredientAllergyWeightList;
    private final HashMap<Ingredient, Integer> ingredientUnknownAllergyWeightList;
    private final ArrayList<Product> productsUnknownAllergy;
    private final ArrayList<String> productsUnknownAllergyStatus;
    private final HashMap<Product, HashMap<Ingredient, Integer>> productsUnknownAllergyList;
    private final HashMap<Product, String> productsUnknownAllergyStatusList;

    User (String email) {
        this.userEmail = email;
        this.productsAllergy = new ArrayList<Product>();
        this.productsNoAllergy = new ArrayList<Product>();
        this.ingredientAllergyWeightList = new HashMap<Ingredient, Integer>();
        this.productsUnknownAllergy = new ArrayList<Product>();
        this.productsUnknownAllergyStatus = new ArrayList<String>();
        this.ingredientUnknownAllergyWeightList = new HashMap<Ingredient, Integer>();
        this.productsUnknownAllergyList = new HashMap<Product, HashMap<Ingredient, Integer>>();
        this.productsUnknownAllergyStatusList = new HashMap<Product, String>();
    }

    String getUserEmail () {
        return userEmail;
    }
    ArrayList<Product> getProductsNoAllergy () {
        return productsNoAllergy;
    }
    ArrayList<Product> getProductsAllergy () {
        return productsAllergy;
    }
    HashMap<Ingredient, Integer> getIngredientAllergyWeightList() {
        return ingredientAllergyWeightList;
    }
    ArrayList<Product> getProductsUnknownAllergy () {
        return productsUnknownAllergy;
    }
    ArrayList<String> getProductsUnknownAllergyStatus() {
        return productsUnknownAllergyStatus;
    }
    HashMap<Ingredient, Integer> getIngredientUnknownAllergyWeightList() {
        return ingredientUnknownAllergyWeightList;
    }
    HashMap<Product, HashMap<Ingredient, Integer>> getProductsUnknownAllergyList() {
        return productsUnknownAllergyList;
    }
    HashMap<Product, String> getProductsUnknownAllergyStatusList() {
        return productsUnknownAllergyStatusList;
    }
    Product getProductByName(String productName) {
        Product product = null;
        for(Map.Entry<Product, String> entry : productsUnknownAllergyStatusList.entrySet()) {
            if (entry.getKey().getProductName().contains(productName)) {
                product = entry.getKey();
            }
        }
        return product;
    }


    public void addProduct(Product product, boolean isAllergy) {
        assert product != null;
        if(isAllergy) {
            productsAllergy.add(product);
            System.out.println("Продукт "+product.getProductName()+" добавлен в список Аллергия");
        } else {
            productsNoAllergy.add(product);
            System.out.println("Продукт "+product.getProductName()+" добавлен в список Нет аллергии");
        }

    }
    public void addProductUnknownAllergy(Product product, String status) {
        assert product != null;
        productsUnknownAllergy.add(product);
        productsUnknownAllergyStatus.add(status);
        productsUnknownAllergyStatusList.put(product, status);
        System.out.println(product.getProductName()+" - product added in List UnknownAllergy");
        productsUnknownAllergyStatusList.put(product, PREDICT);
    }

    public void delProduct(Product product, boolean isAllergy) {
        if(isAllergy) {
            productsAllergy.remove(product);
            System.out.println("Аллергия, удален продукт: "+product.getProductName());
        } else {
            productsNoAllergy.remove(product);
            System.out.println("Нет аллергии, удален продукт: "+product.getProductName());
        }
    }
    public void delProductUnknownAllergy(Product product) {
        productsUnknownAllergy.remove(product);
        System.out.println("Аллергия неизвестна, удален продукт: "+product.getProductName());
    }

    boolean isProductInList(String barcode) {
        for (int i=0; i<productsAllergy.size(); i++) {
            if (barcode.contentEquals(productsAllergy.get(i).getProductBarcode())) { return true; }
        }
        for (int i=0; i<productsNoAllergy.size(); i++) {
            if (barcode.contentEquals(productsNoAllergy.get(i).getProductBarcode())) { return true; }
        }
        for (int i=0; i<productsUnknownAllergy.size(); i++) {
            if (barcode.contentEquals(productsUnknownAllergy.get(i).getProductBarcode())) { return true; }
        }
        return false;
    }


    public void calculateUserAllergyList() {
        //TODO: change logic - depends of ingredients position
        ingredientAllergyWeightList.clear();
        for (Product prod : productsAllergy) {
            for (Map.Entry<String, Ingredient> entry : prod.getProductIngredients().entrySet()) {
                Ingredient ing = entry.getValue();
                if (ingredientAllergyWeightList.containsKey(ing)) {
                    ingredientAllergyWeightList.put(ing, ingredientAllergyWeightList.get(ing)+1);
                    System.out.println(ing.getIngredientName() + " - recover with Weight - " + ingredientAllergyWeightList.get(ing));
                } else {
                    ingredientAllergyWeightList.put(ing, 1);
                    System.out.println(ing.getIngredientName() + " - added with Weight - " + ingredientAllergyWeightList.get(ing));
                }
            }
        }

        for (Product prod : productsNoAllergy) {
            for (Map.Entry<String, Ingredient> entry : prod.getProductIngredients().entrySet()) {
                Ingredient ing = entry.getValue();
                if (ingredientAllergyWeightList.containsKey(ing)) {
                    ingredientAllergyWeightList.put(ing, 0);
                    System.out.println(ing.getIngredientName() + " - recover with Weight - " + ingredientAllergyWeightList.get(ing));
                } else {
                    ingredientAllergyWeightList.put(ing, 0);
                    System.out.println(ing.getIngredientName() + " - added with Weight - " + ingredientAllergyWeightList.get(ing));
                }
            }
        }

    }

    public void calculateUserProductUnknownAllergy(@NotNull Product productUnknownAllergy) {
        System.out.println(productUnknownAllergy.getProductName() + " - productUnknownAllergy");

        for (Map.Entry<String, Ingredient> entry : productUnknownAllergy.getProductIngredients().entrySet()) {
            Ingredient ing = entry.getValue();
            System.out.println(ing.getIngredientName() + " - ing = entry.getValue()");
            System.out.println(ingredientAllergyWeightList.size() + " - ingredientAllergyWeightList.size()");
            if (ingredientAllergyWeightList.containsKey(ing)) {
                System.out.println("ingredientAllergyWeightList.containsKey(ing) - true");
                ingredientUnknownAllergyWeightList.put(ing, ingredientAllergyWeightList.get(ing));
                productsUnknownAllergyList.put(productUnknownAllergy, ingredientUnknownAllergyWeightList);
                System.out.println(ing.getIngredientName() + " - added in *ingredientUnknownAllergyWeightList* with Weight - " + ingredientUnknownAllergyWeightList.get(ing));
            } else {
                System.out.println("ingredientAllergyWeightList.containsKey(ing) - false");
                ingredientUnknownAllergyWeightList.put(ing, 100);
                productsUnknownAllergyList.put(productUnknownAllergy, ingredientUnknownAllergyWeightList);
                System.out.println(ing.getIngredientName() + " - added in *ingredientUnknownAllergyWeightList* with Weight - " + ingredientUnknownAllergyWeightList.get(ing));
            }
        }

        ArrayList<Ingredient> ingredientYesAllergy = new ArrayList<Ingredient>(); // ing with 100% allergy
        ArrayList<Ingredient> ingredientAllergy = new ArrayList<Ingredient>(); // ing with 0% < allergy < 100%
        ArrayList<Ingredient> ingredientNoAllergy = new ArrayList<Ingredient>(); // ing with 0% allergy
        ArrayList<Ingredient> ingredientUnknownAllergy = new ArrayList<Ingredient>();
        System.out.println(ingredientUnknownAllergyWeightList.size()+" - ingredientUnknownAllergyWeightList.size()");
        System.out.println(productsAllergy.size() +" - productsAllergy.size()");

        for (Map.Entry<Ingredient, Integer> entry : ingredientUnknownAllergyWeightList.entrySet()) {
            int allergyPercent = entry.getValue()*100/productsAllergy.size();
            System.out.println(allergyPercent +" - allergyPercent");
            if (allergyPercent==0) {
                ingredientNoAllergy.add(entry.getKey());
            } else if (allergyPercent == 100) {
                ingredientYesAllergy.add(entry.getKey());
            } else if (entry.getValue()==100) {
                ingredientUnknownAllergy.add(entry.getKey());
            } else {
                ingredientAllergy.add(entry.getKey());
            }
        }
        System.out.println(ingredientNoAllergy.size() +" - ingredientNoAllergy.size()");
        for (int i=0; i<ingredientNoAllergy.size(); i++) {
            System.out.println(ingredientNoAllergy.get(i).getIngredientName());
        }
        System.out.println(ingredientYesAllergy.size() +" - ingredientYesAllergy.size()");
        for (int i=0; i<ingredientYesAllergy.size(); i++) {
            System.out.println(ingredientYesAllergy.get(i).getIngredientName());
        }
        System.out.println(ingredientUnknownAllergy.size() +" - ingredientUnknownAllergy.size()");
        for (int i=0; i<ingredientUnknownAllergy.size(); i++) {
            System.out.println(ingredientUnknownAllergy.get(i).getIngredientName());
        }
        System.out.println(ingredientAllergy.size() +" - ingredientAllergy.size()");
        for (int i=0; i<ingredientAllergy.size(); i++) {
            System.out.println(ingredientAllergy.get(i).getIngredientName());
        }


        if (ingredientYesAllergy.size()>0) {
            productsUnknownAllergyStatusList.put(productUnknownAllergy, ALLERGY_YES);
        } else if (ingredientAllergy.size()>0) {
            productsUnknownAllergyStatusList.put(productUnknownAllergy, ALLERGY_PROBABLY_YES);
        } else if ((ingredientNoAllergy.size()+ingredientUnknownAllergy.size()) == productUnknownAllergy.getProductIngredients().size()) {
            productsUnknownAllergyStatusList.put(productUnknownAllergy, ALLERGY_PROBABLY_NO);
        } else if (ingredientNoAllergy.size() == productUnknownAllergy.getProductIngredients().size()) {
            productsUnknownAllergyStatusList.put(productUnknownAllergy, ALLERGY_NO);
        } else {
            productsUnknownAllergyStatusList.put(productUnknownAllergy, ALLERGY_UNKNOWN);
        }



        for (Map.Entry<Product, String> entry : productsUnknownAllergyStatusList.entrySet()) {
            productsUnknownAllergyStatus.add(entry.getValue());
            System.out.println(entry.getValue() +" - productsUnknownAllergyStatus.add(entry.getValue())");
        }

    }

    static final String PREDICT = "Click to Predict Allergy"; //white
    static final String ALLERGY_YES = "Allergy Yes"; // red
    static final String ALLERGY_PROBABLY_YES = "Allergy Probably Yes"; //light red
    static final String ALLERGY_PROBABLY_NO = "Allergy Probably No"; //light yellow
    static final String ALLERGY_NO = "Allergy No"; //light green
    static final String ALLERGY_UNKNOWN = "Allergy Unknown. Please add more products";

}
