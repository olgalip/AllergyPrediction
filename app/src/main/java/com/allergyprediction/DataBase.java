package com.allergyprediction;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {
    private static HashMap<String, Ingredient> ingredientsDataBase;
    private static HashMap<String, Product> productsDataBase;
    private static HashMap<String, User> userDataBase;
    private static Context ctx;

    DataBase (Context context) {
        ingredientsDataBase = new HashMap<String, Ingredient>();
        productsDataBase = new HashMap<String, Product>();
        userDataBase = new HashMap<String, User>();
        ctx = context.getApplicationContext();
    }

    HashMap<String, Ingredient> getIngredientsDataBase() {
        return ingredientsDataBase;
    }
    HashMap<String, Product> getProductsDataBase() {
        return productsDataBase;
    }
    HashMap<String, User> getUserDataBase() {
        return userDataBase;
    }

    ArrayList<String> getIngredientsArray() {
        ArrayList<String> ingredientsArray = new ArrayList<String>();
        for (String ingredient : ingredientsDataBase.keySet()) {
            ingredientsArray.add(ingredient);
        }
        return ingredientsArray;
    }
    ArrayList<String> getBarcodesArray() {
        ArrayList<String> barcodesArray = new ArrayList<String>();
        for (String barcode : productsDataBase.keySet()) {
            barcodesArray.add(barcode);
        }
        return barcodesArray;
    }
    Product getProductByBarcode(String barcode) {
        return productsDataBase.get(barcode);
    }


    void addIngredientInDataBase(Ingredient ingredient) {
        for (String key : ingredient.getIngredientList()) {
            if (isIngredientInDataBase(key)) {
                System.out.println(key + " - ингредиент уже есть в базе");
            } else {
                ingredientsDataBase.put(key, ingredient);
            }
        }
    }

    void addProductInDataBase(Product product) {
        if (productsDataBase.containsKey(product.getProductBarcode())) {
            System.out.println(product.getProductBarcode() + " - продукт с таким штрих-кодом уже есть в базе");
        } else {
            productsDataBase.put(product.getProductBarcode(), product);
        }
    }

    void addUserInDataBase(User user) {
        if (userDataBase.containsKey(user.getUserEmail())) {
            System.out.println(user.getUserEmail() + " - пользователь с таким email уже есть в базе");
        } else {
            userDataBase.put(user.getUserEmail(), user);
        }
    }


    private boolean isIngredientInDataBase (String ingredientName) {
        String ingredientUpperCase = ingredientName.toUpperCase();
        return ingredientsDataBase.containsKey(ingredientUpperCase);
    }
    void addIngredientVariants(Ingredient ingredient, String ingredientVariant) {
        String ingredientUpperCase = ingredientVariant.toUpperCase();
        if (isIngredientInList(ingredient, ingredientUpperCase)) {
            System.out.println(ingredientVariant + " синоним уже есть в карточке ингредиента");
        } else {
            ingredient.addIngredientVariant(ingredientUpperCase);
            ingredientsDataBase.put(ingredientUpperCase, ingredient);
        }
    }
    private boolean isIngredientInList(Ingredient ingredient, String ingredientVariant) {
        String ingredientUpperCase = ingredientVariant.toUpperCase();
        for (String name : ingredient.getIngredientList()) {
            if(name.equals(ingredientUpperCase)) {
                return true;
            }
        }
        return false;
    }

    void addIngredientInProduct(Product product, String ingredientINCI) {

        if (isIngredientInProduct(product, ingredientINCI)) {
            System.out.println(ingredientINCI + " - ингредиент уже добавлен в - " + product.getProductName());
        }
        else if (isIngredientInDataBase(ingredientINCI)) {
            product.addIngredient(ingredientINCI, ingredientsDataBase.get(ingredientINCI));
        } else {
            Ingredient newIngredient = new Ingredient(ingredientINCI);
            product.addIngredient(ingredientINCI, newIngredient);
            ingredientsDataBase.put(ingredientINCI, newIngredient);
        }
    }
    boolean isIngredientInProduct(Product product, String ingredientName) {
        Ingredient ingredient = null;
        if(ingredientsDataBase.containsKey(ingredientName)) {
            ingredient = ingredientsDataBase.get(ingredientName);
        } else return false;
        return product.getProductIngredients().containsValue(ingredient);
    }

    void addProductInUser(User user, Product product, boolean isAllergy) {
        user.addProduct(product, isAllergy);
    }
    void addProductsUnknownAllergyInUser(User user, Product product, String status) {
        user.addProductUnknownAllergy(product, status);
    }


    void initData() throws IOException {
        User user = new User("lipatova.on@mail.ru");
        this.addUserInDataBase(user);

        this.addIngredientsDataBaseFromFile(R.raw.ingredients);
        this.addProductsDataBaseFromFile(R.raw.products);

        //allergy
//        Product garnier_spf20 = new Product("3600540277718","GARNIER AMBRE SOLAIRE SPF20");
        this.addProductInUser(user, getProductByBarcode("3600540277718"), true); //"GARNIER AMBRE SOLAIRE SPF20"
//        Product nivea_spf15 = new Product("4005808290703","NIVEA SUN SPF15");
        this.addProductInUser(user, getProductByBarcode("4005808290703"), true); //"NIVEA SUN SPF15"
        this.addProductInUser(user, getProductByBarcode("699439002060"), true);
        this.addProductInUser(user, getProductByBarcode("001"), true);
        this.addProductInUser(user, getProductByBarcode("4606453057170"), true);
        this.addProductInUser(user, getProductByBarcode("4600936180782"), true);

        //no allergy
//        Product nivea_spf2 = new Product("4005808443406", "NIVEA SUN SPF2");
        this.addProductInUser(user, getProductByBarcode("4005808443406"), false); //"NIVEA SUN SPF2"
//        Product floresan_spf20 = new Product("4605319000565", "FLORESAN Молочко защитное водостойкое SPF20 ФОРМУЛА: 105");
        this.addProductInUser(user, getProductByBarcode("4605319000565"), false); //"FLORESAN Молочко защитное водостойкое SPF20 ФОРМУЛА: 105"
        this.addProductInUser(user, getProductByBarcode("3600540570673"), false);
        this.addProductInUser(user, getProductByBarcode("3600542163217"), false);
        this.addProductInUser(user, getProductByBarcode("4005808158065"), false);
        this.addProductInUser(user, getProductByBarcode("8901138713881"), false);
        this.addProductInUser(user, getProductByBarcode("8901248333085"), false);
        this.addProductInUser(user, getProductByBarcode("2000001026427"), false);
        this.addProductInUser(user, getProductByBarcode("8002110311511"), false);
        this.addProductInUser(user, getProductByBarcode("3574660085563"), false);
        this.addProductInUser(user, getProductByBarcode("4005808369980"), false);
        this.addProductInUser(user, getProductByBarcode("4606711703276"), false);
        this.addProductInUser(user, getProductByBarcode("4602159015155"), false);
        this.addProductInUser(user, getProductByBarcode("4607003245726"), false);
        this.addProductInUser(user, getProductByBarcode("5391520947216"), false);
        this.addProductInUser(user, getProductByBarcode("4602228002406"), false);
        this.addProductInUser(user, getProductByBarcode("4602228001805"), false);
        this.addProductInUser(user, getProductByBarcode("3600541033368"), false);
        this.addProductInUser(user, getProductByBarcode("4605922011866"), false);
        this.addProductInUser(user, getProductByBarcode("4670016720047"), false);
        this.addProductInUser(user, getProductByBarcode("4603733061988"), false);
        this.addProductInUser(user, getProductByBarcode("4600702025972"), false);
        this.addProductInUser(user, getProductByBarcode("3660005196640"), false);
        this.addProductInUser(user, getProductByBarcode("4620010796432"), false);
        this.addProductInUser(user, getProductByBarcode("3600540042910"), false);
        this.addProductInUser(user, getProductByBarcode("3600531143572"), false);
        this.addProductInUser(user, getProductByBarcode("4680558906489"), false);
        this.addProductInUser(user, getProductByBarcode("8711700753098"), false);
        this.addProductInUser(user, getProductByBarcode("8711700753098"), false);
        this.addProductInUser(user, getProductByBarcode("8718114402277"), false);
        this.addProductInUser(user, getProductByBarcode("3600540483393"), false);
        this.addProductInUser(user, getProductByBarcode("4606453034249"), false);
        this.addProductInUser(user, getProductByBarcode("4015100199529"), false);

//        8005610605371	Londa Professional Cредство для восстановления поврежденных волос Visible Repair - Маска 750 мл Италия 2020
//        8005610605272	Londa Professional Шампунь для окрашенных волос Color Radiance 250 мл
//        8005610604350	Londa Professional Кондиционер для окрашенных волос Color Radiance 250 мл
//        8809052585266	SKINLITE Маска для лица подтягивающая с коллагеном и экстрактом Зеленого чая 23мл
//        4600936180782 Свобода Крем массажный Балет 40г


    }

    // FEFF because this is the Unicode char represented by the UTF-8 byte order mark (EF BB BF).
    public static final String UTF8_BOM = "\uFEFF";

    void addIngredientsDataBaseFromFile(final int fileAddress) throws IOException {
        //read ANSI file
        //InputStream inputStream = ctx.getResources().openRawResource(fileAddress);
        //InputStreamReader inputReader = new InputStreamReader(inputStream, "windows-1251");
        //BufferedReader br = new BufferedReader(inputReader);

        //read UTF-8 file
        final InputStream inputStream = ctx.getResources().openRawResource(fileAddress);
        final InputStreamReader inputReader = new InputStreamReader(inputStream);

        final BufferedReader br = new BufferedReader(inputReader);
        String line = br.readLine();
        String lineCaps = line.toUpperCase();
        if (lineCaps.startsWith(UTF8_BOM)) {
            lineCaps = lineCaps.substring(1);
        }
        while((lineCaps=br.readLine())!=null) {
            String ingredientName;
            String ingredientVariant;
            String[] arrayOfIngredients =  lineCaps.split("\\$");
            ingredientName = arrayOfIngredients[0];
            Ingredient newIng = new Ingredient(ingredientName);
            this.addIngredientInDataBase(newIng);

            for (int i=1; i<arrayOfIngredients.length; i++) {
                ingredientVariant = arrayOfIngredients[i];
                if (ingredientVariant.length()!=0) {
                    this.addIngredientVariants(newIng, ingredientVariant);
                }
            }
        }
    }

    void addProductsDataBaseFromFile(final int fileAddress) throws IOException {
        //read ANSI file
        //InputStream inputStream = ctx.getResources().openRawResource(fileAddress);
        //InputStreamReader inputReader = new InputStreamReader(inputStream, "windows-1251");
        //BufferedReader br = new BufferedReader(inputReader);

        //read UTF-8 file
        final InputStream inputStream = ctx.getResources().openRawResource(fileAddress);
        final InputStreamReader inputReader = new InputStreamReader(inputStream);

        final BufferedReader br = new BufferedReader(inputReader);
        String line = br.readLine();
        if (line.startsWith(UTF8_BOM)) {
            line = line.substring(1);
        }
        while((line=br.readLine())!=null) {
            String barcode;
            String productName;
            String productIngredient;
            String[] arrayOfProductIngredients =  line.split("\\$");
            barcode = arrayOfProductIngredients[0];
            productName = arrayOfProductIngredients[1];
            Product newProd = new Product(barcode, productName);
            this.addProductInDataBase(newProd);

            for (int i=2; i<arrayOfProductIngredients.length; i++) {
                productIngredient = arrayOfProductIngredients[i];
                if (productIngredient.length()!=0) {
                    this.addIngredientInProduct(newProd, productIngredient.toUpperCase());
                }
            }
        }
    }

}
