package com.allergyprediction;

import android.view.View;
import android.widget.ListView;

import java.util.Map;

public class CalculateAllergy implements View.OnClickListener {
    private MainActivity mainActivity;
    private User user;
    private IngredientCalculateAdapter adapter;
    private ListView listViewCalculateAllergy;

    CalculateAllergy(MainActivity mainActivity, IngredientCalculateAdapter arrayAdapterCalculateAllergy) {
        this.mainActivity = mainActivity;
        this.user = mainActivity.getUser();
        this.adapter = arrayAdapterCalculateAllergy;
        this.listViewCalculateAllergy = mainActivity.getListViewCalculateAllergy();
    }

    @Override
    public void onClick(View v) {
        user.calculateUserAllergyList();
        if (user.getIngredientAllergyWeightList().isEmpty()) {
            mainActivity.getArrayListIngredientAllergy().clear();
            mainActivity.getArrayListAllergy().clear();
            adapter.notifyDataSetChanged();
            Utils.toast(mainActivity, R.string.fillProductLists);
        } else {
            mainActivity.getArrayListIngredientAllergy().clear();
            mainActivity.getArrayListIngredientAllergyWeight().clear();

            for(Map.Entry<Ingredient, Integer> entry : user.getIngredientAllergyWeightList().entrySet()) {
                if(entry.getValue()>0) {
                    mainActivity.getArrayListIngredientAllergy().add(entry.getKey());
                    mainActivity.getArrayListIngredientAllergyWeight().add(entry.getValue());
                }

                adapter.notifyDataSetChanged();
            }

            listViewCalculateAllergy.setAdapter(adapter);

            System.out.println("______________calculate allergy______________");
            System.out.println("mainActivity.getArrayListIngredientAllergy().size() " + mainActivity.getArrayListIngredientAllergy().size());
            System.out.println("user.getUserAllergyListWeight().size() " + user.getIngredientAllergyWeightList().size());

            for (int i=0; i<mainActivity.getArrayListIngredientAllergy().size(); i++) {
                System.out.println(mainActivity.getArrayListIngredientAllergy().get(i).getIngredientName() +
                        " - with Weight - " + mainActivity.getArrayListIngredientAllergyWeight().get(i));
            }
        }

    }
}
