package com.allergyprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private DataBase dataBase;
    private User user;
    private ArrayList<Product> arrayListAllergy;
    private ArrayList<Product> arrayListNoAllergy;
    private ArrayList<Ingredient> arrayListIngredientAllergy;
    private ArrayList<Integer> arrayListIngredientAllergyWeight;
    private ArrayList<Product> arrayListProductUnknownAllergy;
    private ArrayList<String> arrayListProductUnknownAllergyStatus;
    private ListView listViewAllergy;
    private ListView listViewNoAllergy;
    private ListView listViewCalculateAllergy;
    private ListView listViewProductUnknownAllergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dataBase = new DataBase(this);
        try {
            dataBase.initData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        user = dataBase.getUserDataBase().values().iterator().next();

        TextView userEmail = findViewById(R.id.userEmail);
        userEmail.setText(user.getUserEmail());

        listViewAllergy = (ListView) findViewById(R.id.productsListAllergy);
        listViewNoAllergy = (ListView) findViewById(R.id.productsListNoAllergy);

        arrayListAllergy = user.getProductsAllergy();
        arrayListNoAllergy = user.getProductsNoAllergy();

        //custom adapter for ArrayList<Product>
        ProductAdapter arrayAdapterAllergy = new ProductAdapter(this, R.layout.list_item_product, arrayListAllergy);
        ProductAdapter arrayAdapterNoAllergy = new ProductAdapter(this, R.layout.list_item_product, arrayListNoAllergy);

        listViewAllergy.setAdapter(arrayAdapterAllergy);
        listViewNoAllergy.setAdapter(arrayAdapterNoAllergy);


        // CALCULATE ALLERGY
        listViewCalculateAllergy = (ListView) findViewById(R.id.allergyList);
        arrayListIngredientAllergy = new ArrayList<Ingredient>();
        arrayListIngredientAllergyWeight = new ArrayList<Integer>();

        if (user.getIngredientAllergyWeightList().isEmpty()) {
            arrayListIngredientAllergy.clear();
            arrayListIngredientAllergyWeight.clear();
        }
        System.out.println("MainActivity user.getProductsAllergy().size() - " + user.getProductsAllergy().size());

        //custom adapter for ArrayList<Ingredient> Calculate Allergy
        final IngredientCalculateAdapter arrayAdapterCalculateAllergy = new IngredientCalculateAdapter(this,
                R.layout.list_item_ingredient, R.id.ingredientName, arrayListIngredientAllergy, arrayListIngredientAllergyWeight, user);


        // PREDICT ALLERGY
        listViewProductUnknownAllergy = (ListView) findViewById(R.id.productsListUnknownAllergy);
        arrayListProductUnknownAllergy = user.getProductsUnknownAllergy();
        arrayListProductUnknownAllergyStatus = user.getProductsUnknownAllergyStatus();

//        if (user.getProductsUnknownAllergy().isEmpty()) {
//            arrayListProductUnknownAllergy.clear();
//            arrayListProductUnknownAllergyStatus.clear();
//        }
        System.out.println("MainActivity user.getProductsUnknownAllergy().size() - " + user.getProductsUnknownAllergy().size());

        //custom adapter for ArrayList<Product> Unknown Allergy - TODO
        ProductUnknownAllergyAdapter arrayAdapterProductUnknownAllergy = new ProductUnknownAllergyAdapter(this,
                R.layout.list_item_product_unknown_allergy, R.id.productName, arrayListProductUnknownAllergy, arrayListProductUnknownAllergyStatus, user);
        listViewProductUnknownAllergy.setAdapter(arrayAdapterProductUnknownAllergy);
        System.out.println("MainActivity user.getProductsUnknownAllergy().size() - " + user.getProductsUnknownAllergy().size());



        TextView textHeader1 = findViewById(R.id.header1);
        final View layout1 = findViewById(R.id.list1);
        textHeader1.setOnClickListener(new ClickHeader(layout1));

        TextView textHeader2 = findViewById(R.id.header2);
        final View layout2 = findViewById(R.id.list2);
        textHeader2.setOnClickListener(new ClickHeader(layout2));

        TextView textHeader3 = findViewById(R.id.header3);
        final View layout3 = findViewById(R.id.list3);
        textHeader3.setOnClickListener(new ClickHeader(layout3));

        TextView textHeader4 = findViewById(R.id.header4);
        final View layout4 = findViewById(R.id.list4);
        textHeader4.setOnClickListener(new ClickHeader(layout4));


        //add product in list on click
        TextView addButtonAllergy = findViewById(R.id.addNewProduct_btn1);
        addButtonAllergy.setOnClickListener(new AddProduct(this, arrayAdapterAllergy, true));
        TextView addButtonNoAllergy = findViewById(R.id.addNewProduct_btn2);
        addButtonNoAllergy.setOnClickListener(new AddProduct(this, arrayAdapterNoAllergy, false));
        TextView addButtonProductUnknownAllergy = findViewById(R.id.addProductUnknownAllergy_btn3);
        addButtonProductUnknownAllergy.setOnClickListener(new AddProduct(this, arrayAdapterProductUnknownAllergy, User.PREDICT));

        //show product card on click
        listViewAllergy.setOnItemClickListener(new ShowProductCard(this, listViewAllergy));
        listViewNoAllergy.setOnItemClickListener(new ShowProductCard(this, listViewNoAllergy));

        //delete product from list on long click
        listViewAllergy.setOnItemLongClickListener(new RemoveProductOnLongClick(this,
                arrayAdapterAllergy, listViewAllergy, true));
        listViewNoAllergy.setOnItemLongClickListener(new RemoveProductOnLongClick(this,
                arrayAdapterNoAllergy, listViewNoAllergy, false));


        //calculate allergy
        TextView calculateAllergyButton = findViewById(R.id.calculateAllergy_btn);
        calculateAllergyButton.setOnClickListener(new CalculateAllergy(this, arrayAdapterCalculateAllergy));


        //predict allergy
//        TextView predictAllergyButton = findViewById(R.id.predictAllergy_btn);
//        predictAllergyButton

    }


    public DataBase getDataBase() {
        return dataBase;
    }
    public User getUser() {
        return user;
    }
    public ArrayList<Product> getArrayListAllergy() {
        return arrayListAllergy;
    }
    public ArrayList<Product> getArrayListNoAllergy() {
        return arrayListNoAllergy;
    }
    public ArrayList<Ingredient> getArrayListIngredientAllergy() {
        return arrayListIngredientAllergy;
    }
    public ArrayList<Integer> getArrayListIngredientAllergyWeight() {
        return arrayListIngredientAllergyWeight;
    }
    public ArrayList<Product> getArrayListProductUnknownAllergy() {
        return arrayListProductUnknownAllergy;
    }
    public ArrayList<String> getArrayListProductUnknownAllergyStatus() {
        return arrayListProductUnknownAllergyStatus;
    }

    public ListView getListViewAllergy() {
        return listViewAllergy;
    }
    public ListView getListViewNoAllergy() {
        return listViewNoAllergy;
    }
    public ListView getListViewCalculateAllergy() {
        return listViewCalculateAllergy;
    }
    public ListView getListViewProductUnknownAllergy() {
        return listViewProductUnknownAllergy;
    }



//    static HashMap<Ingredient, Integer> predictAllergy (User user, Product newProduct, DataBase dataBase) {
//        HashMap<Ingredient, Integer> predictAllergyList = new HashMap<Ingredient, Integer>();
//        for (Ingredient ing : newProduct.getProductIngredients()) {
//            predictAllergyList.put(ing, 0);
//        }
//        for (Product prod : user.getProductsNoAllergy()) {
//            for (Ingredient ing : prod.getProductIngredients()) {
//                if (predictAllergyList.containsKey(ing)) {
//                    predictAllergyList.remove(ing);
//                    System.out.println(ing.getIngredientName() + " removed from predictAllergyList");
//                }
//            }
//        }
//        for (HashMap.Entry<Ingredient, Integer> item : user.getUserAllergyListWeight().entrySet()) {
//            if (predictAllergyList.containsKey(item.getKey())) {
//                predictAllergyList.put(item.getKey(), item.getValue());
//            }
//        }
//        return predictAllergyList;
//    }

}


