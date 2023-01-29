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

public class IngredientAdapter extends ArrayAdapter<Ingredient> {
    private ArrayList<Ingredient> ingredientArrayList;
    Context ctx;


    public IngredientAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Ingredient> objects) {
        super(context, resource, objects);
        ctx = context;
        ingredientArrayList = objects;
    }

    @Override
    public int getCount(){
        return ingredientArrayList.size();
    }

    @NonNull
    @Override
    public TextView getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint({"ViewHolder", "InflateParams"}) TextView row = (TextView)inflater.inflate(R.layout.list_item_product, null, true);
        row.setText(ingredientArrayList.get(position).getIngredientName());
        return row;
    }
}
