package com.allergyprediction;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class ShowProductCard implements AdapterView.OnItemClickListener {
    MainActivity mainActivity;
    DataBase dataBase;
    ListView listView;

    ShowProductCard(MainActivity mainActivity, ListView listView) {
        this.mainActivity = mainActivity;
        this.dataBase = mainActivity.getDataBase();
        this.listView = listView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product itemClicked = (Product)listView.getItemAtPosition(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        View cardView = mainActivity.getLayoutInflater().inflate(R.layout.view_product_card, null);
        final TextView productBarcode = (TextView)cardView.findViewById(R.id.productBarcode);
        final TextView productName = (TextView)cardView.findViewById(R.id.productName);

        final LinearLayout linearLayoutProductIngredients = (LinearLayout)cardView.findViewById(R.id.productIngredientsList);
        LayoutInflater inflater = LayoutInflater.from(mainActivity);

        builder.setView(cardView);
        final AlertDialog alertDialog = builder.create();

        ArrayList<String> arrayListProductIngredients = itemClicked.getProductIngredientsNames();
        productBarcode.setText(itemClicked.getProductBarcode());
        productName.setText(itemClicked.getProductName());

        for (int i=0; i<arrayListProductIngredients.size(); i++) {
            TextView textView  = (TextView)inflater.inflate(R.layout.list_item_product, linearLayoutProductIngredients, false);
            textView.setText(arrayListProductIngredients.get(i));
            linearLayoutProductIngredients.addView(textView);
        }

        alertDialog.show();
    }
}
