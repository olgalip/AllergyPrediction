package com.allergyprediction;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddProduct implements View.OnClickListener {
    private MainActivity mainActivity;
    private DataBase dataBase;
    private User user;
    private ProductAdapter productAdapter;
    private ProductUnknownAllergyAdapter productUnknownAllergyAdapter;
    private boolean isAllergy;
    String newProductStatus;
    private Product newProduct;

    AddProduct(@NotNull MainActivity mainActivity, ProductAdapter adapter, boolean isAllergy) {
        this.mainActivity = mainActivity;
        this.dataBase = mainActivity.getDataBase();
        this.user = mainActivity.getUser();
        this.productAdapter = adapter;
        this.isAllergy = isAllergy;
    }


    AddProduct(MainActivity mainActivity, ProductUnknownAllergyAdapter adapter, String newProductStatus) {
        this.mainActivity = mainActivity;
        this.dataBase = mainActivity.getDataBase();
        this.user = mainActivity.getUser();
        this.productUnknownAllergyAdapter = adapter;
        this.newProductStatus = newProductStatus;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        @SuppressLint("InflateParams") final View addView = mainActivity.getLayoutInflater().inflate(R.layout.dialog_add_product, null);

        //автодополнение для productBarcode
        final AutoCompleteTextView productBarcode_input = (AutoCompleteTextView)addView.findViewById(R.id.productBarcode_input);
        final ArrayList<String> barcodesArray = dataBase.getBarcodesArray();
        final ArrayAdapter<String> arrayAdapterBarcode = new ArrayAdapter<>(mainActivity, R.layout.list_item_product, barcodesArray);
        productBarcode_input.setAdapter(arrayAdapterBarcode);

        //TODO: написать автодополнение для productName
        final EditText productName_input = (EditText)addView.findViewById(R.id.productName_input);

        //автодополнение для productIngredient
        final AutoCompleteTextView productIngredient_input = (AutoCompleteTextView)addView.findViewById(R.id.productIngredient_input);
        final ArrayList<String> ingredientsArray = dataBase.getIngredientsArray();
        final ArrayAdapter<String> arrayAdapterIngredients = new ArrayAdapter<>(mainActivity, R.layout.list_item_product, ingredientsArray);
        productIngredient_input.setAdapter(arrayAdapterIngredients);

        final View ingredientsLayout = (View)addView.findViewById(R.id.ingredientsLayout);
        Button btn_add = (Button)addView.findViewById(R.id.btn_add);
        Button btn_cancel = (Button)addView.findViewById(R.id.btn_cancel);
        Button btn_plus = (Button)addView.findViewById(R.id.btn_plus);

        builder.setView(addView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        final LinearLayout linearLayoutAddIngredients = (LinearLayout)addView.findViewById(R.id.productIngredientsList);
        final LayoutInflater inflater = LayoutInflater.from(mainActivity);
        final ArrayList<String> arrayListProductIngredients = new ArrayList<String>();

        productBarcode_input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemClicked = arrayAdapterBarcode.getItem(position);
                if(barcodesArray.contains(itemClicked)) {
                    newProduct = dataBase.getProductByBarcode(itemClicked);
                    productBarcode_input.setText(itemClicked);
                    productBarcode_input.setEnabled(false);
                    productBarcode_input.setTextColor(R.color.colorPrimaryDark);
                    productName_input.setText(newProduct.getProductName());
                    productName_input.setEnabled(false);
                    productName_input.setTextColor(R.color.colorPrimaryDark);
                    ingredientsLayout.setVisibility(View.VISIBLE);
                    arrayListProductIngredients.addAll(newProduct.getProductIngredientsNames());
                    for (int i=0; i<arrayListProductIngredients.size(); i++) {
                        TextView textView  = (TextView)inflater.inflate(R.layout.list_item_product, linearLayoutAddIngredients, false);
                        textView.setText(arrayListProductIngredients.get(i));
                        linearLayoutAddIngredients.addView(textView);
                        //del ingredient on long click
                        textView.setOnLongClickListener(new RemoveIngredientOnLongClick(mainActivity, newProduct, linearLayoutAddIngredients, textView));
                    }
                }
            }
        });


        //add product ingredients
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ingredientName = productIngredient_input.getText().toString().toUpperCase();

                //check inputs on null
                if (ingredientName.length() == 0) {
                    productIngredient_input.setBackgroundResource(android.R.drawable.edit_text);
                } else {
                    productIngredient_input.setBackgroundResource(android.R.drawable.editbox_background_normal);

                    //AutoCompleteTextView for ingredients
                    productIngredient_input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String itemClicked = arrayAdapterIngredients.getItem(position);
                            if(ingredientsArray.contains(itemClicked)) {
                                productIngredient_input.setText(itemClicked);
                            }
                        }
                    });

                    //check is ingredientName in arrayListAddIngredients
                    if (dataBase.isIngredientInProduct(newProduct, ingredientName)) {
                        Utils.toast(mainActivity, R.string.ingredientAlreadyAdded);
                    } else {
                        dataBase.addIngredientInProduct(newProduct, ingredientName);
                        arrayListProductIngredients.add(ingredientName);
                        TextView textView  = (TextView)inflater.inflate(R.layout.list_item_product, linearLayoutAddIngredients, false);
                        textView.setText(ingredientName);
                        linearLayoutAddIngredients.addView(textView);
                        //del ingredient on long click
                        textView.setOnLongClickListener(new RemoveIngredientOnLongClick(mainActivity, newProduct, linearLayoutAddIngredients, textView));
                        productIngredient_input.setText("");
                    }
                }
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                String productBarcodeText = productBarcode_input.getText().toString();
                String productNameText = productName_input.getText().toString();
                if (productBarcodeText.length() == 0 | productNameText.length() == 0) {     //check inputs on null
                    Utils.toast(mainActivity, R.string.fillInputs);
                } else if (user.isProductInList(productBarcodeText)){       //check newProduct already in user lists
                    Utils.toast(mainActivity, R.string.alreadyInLists);
                } else if (ingredientsLayout.getVisibility() == View.GONE) {
                    newProduct = new Product(productBarcodeText, productNameText);
                    productBarcode_input.setEnabled(false);
                    productBarcode_input.setTextColor(R.color.colorPrimaryDark);
                    productBarcode_input.setText(productBarcodeText);
                    productName_input.setEnabled(false);
                    productName_input.setTextColor(R.color.colorPrimaryDark);
                    productName_input.setText(productNameText);
                    ingredientsLayout.setVisibility(View.VISIBLE);
                } else if (newProduct.getProductIngredients().isEmpty()) {
                    Utils.toast(mainActivity, R.string.fillIngredients);
                } else {
                    dataBase.addProductInDataBase(newProduct);
                    System.out.println("newProductStatus - " + newProductStatus);
                    if(newProductStatus.contentEquals(User.PREDICT)) {
                        System.out.println("newProductStatus.contentEquals(User.PREDICT) - " + newProductStatus.contentEquals(User.PREDICT));
                        dataBase.addProductsUnknownAllergyInUser(user, newProduct, newProductStatus);
                        productUnknownAllergyAdapter.notifyDataSetChanged();
                    } else {
                        dataBase.addProductInUser(user, newProduct, isAllergy);
                        productAdapter.notifyDataSetChanged();
                        user.getIngredientAllergyWeightList().clear();
                        System.out.println("user.getUserAllergyListWeight() after clear - " + user.getIngredientAllergyWeightList().size());
                    }

                    System.out.println(newProduct.getProductName() + " - new product added in DataBase");
                    alertDialog.dismiss();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

}
