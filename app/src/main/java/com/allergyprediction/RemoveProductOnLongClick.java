package com.allergyprediction;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

public class RemoveProductOnLongClick implements AdapterView.OnItemLongClickListener {
    MainActivity mainActivity;
    User user;
    ProductAdapter productAdapter;
    ListView listView;
    boolean isAllergy;

    RemoveProductOnLongClick(MainActivity mainActivity, ProductAdapter adapter, ListView listView, boolean isAllergy) {
        this.mainActivity = mainActivity;
        this.user = mainActivity.getUser();
        this.productAdapter = adapter;
        this.listView = listView;
        this.isAllergy = isAllergy;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Product itemClicked = (Product)listView.getItemAtPosition(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        View delView = mainActivity.getLayoutInflater().inflate(R.layout.dialog_btn_del, null);
        Button btn_del = (Button)delView.findViewById(R.id.btn_del);
        builder.setView(delView);
        final AlertDialog alertDialog = builder.create();

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.delProduct(itemClicked, isAllergy);

                user.getIngredientAllergyWeightList().clear();
                System.out.println("user.getUserAllergyListWeight() after clear - " + user.getIngredientAllergyWeightList().size());

                productAdapter.remove(itemClicked);
                listView.setAdapter(productAdapter);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        return true;
    }
}
