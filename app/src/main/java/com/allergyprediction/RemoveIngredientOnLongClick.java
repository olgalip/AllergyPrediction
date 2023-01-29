package com.allergyprediction;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class RemoveIngredientOnLongClick implements AdapterView.OnLongClickListener {
    MainActivity mainActivity;
    Product product;
    TextView textView;
    LinearLayout linearLayout;

    RemoveIngredientOnLongClick(MainActivity mainActivity, Product product, LinearLayout linearLayout, TextView view) {
        this.mainActivity = mainActivity;
        this.product = product;
        this.linearLayout = linearLayout;
        this.textView = view;
    }

    @Override
    public boolean onLongClick(View v) {
        final String itemClicked = (String) textView.getText();

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        View delView = mainActivity.getLayoutInflater().inflate(R.layout.dialog_btn_del, null);
        Button btn_del = (Button)delView.findViewById(R.id.btn_del);
        builder.setView(delView);
        final AlertDialog alertDialog = builder.create();

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.delIngredient(itemClicked);
                linearLayout.removeView(textView);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        return true;
    }
}
