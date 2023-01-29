package com.allergyprediction;

import android.view.View;
import android.widget.ListView;

import java.util.Map;

public class PredictAllergy implements View.OnClickListener {
    private MainActivity mainActivity;
    private User user;
    private ProductUnknownAllergyAdapter adapter;
    private ListView listViewProductUnknownAllergy;
    private Product productUnknownAllergy;

     PredictAllergy(ProductUnknownAllergyAdapter arrayAdapterProductUnknownAllergy, Product productUnknownAllergy) {
         this.mainActivity = mainActivity;
         this.user = mainActivity.getUser();
         this.adapter = arrayAdapterProductUnknownAllergy;
         this.listViewProductUnknownAllergy = mainActivity.getListViewProductUnknownAllergy();
         this.productUnknownAllergy = productUnknownAllergy;
     }

    @Override
    public void onClick(View v) {
        user.calculateUserProductUnknownAllergy(productUnknownAllergy);

        for(Map.Entry<Product, String> entry : user.getProductsUnknownAllergyStatusList().entrySet()) {
            mainActivity.getArrayListProductUnknownAllergy().add(entry.getKey());
            mainActivity.getArrayListProductUnknownAllergyStatus().add(entry.getValue());

            adapter.notifyDataSetChanged();
        }

        listViewProductUnknownAllergy.setAdapter(adapter);

    }
}