package com.allergyprediction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class IngredientCalculateAdapter extends ArrayAdapter<Ingredient> {
    private Context ctx;
    private ArrayList<Ingredient> ingredientNameList;
    private ArrayList<Integer> allergyWeightList;
    private User user;

    IngredientCalculateAdapter(@NonNull Context context, int row, int resource,
                               @NonNull ArrayList<Ingredient> ingredients, @NonNull ArrayList<Integer> weight, User user) {
        super(context, row, resource, ingredients);
        ctx = context;
        ingredientNameList = ingredients;
        allergyWeightList = weight;
        this.user = user;
    }

    @Override
    public int getCount(){
        return ingredientNameList.size();
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint({"ViewHolder", "InflateParams"}) View row = inflater.inflate(R.layout.list_item_ingredient, null, true);
        TextView ingredientName = row.findViewById(R.id.ingredientName);
        TextView ingredientAllergyPercent = row.findViewById(R.id.ingredientAllergyPercent);
        ingredientName.setText(ingredientNameList.get(position).getIngredientName());

        System.out.println("allergyWeightList.get(position) before percentInt - " + allergyWeightList.get(position));

        int productsAllergySize = user.getProductsAllergy().size();
        float percent = ((float)allergyWeightList.get(position)/productsAllergySize)*100;
        int percentInt = (int) percent;

        System.out.println("productsAllergySize - " + productsAllergySize);

        ingredientAllergyPercent.setText(percentInt+ "%");
        System.out.println(percentInt + "% - " + ingredientNameList.size());

        System.out.println(ingredientNameList.get(position).getIngredientName() + " - percentInt - " + percentInt);
        if(percentInt >= 200/productsAllergySize) {
            ingredientName.setBackgroundColor(ctx.getResources().getColor(R.color.colorError));
            ingredientAllergyPercent.setBackgroundColor(ctx.getResources().getColor(R.color.colorError));
        }
        return row;
    }
}

