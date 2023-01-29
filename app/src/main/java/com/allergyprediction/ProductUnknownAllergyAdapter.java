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
import java.util.Map;

class ProductUnknownAllergyAdapter extends ArrayAdapter<Product> {
    private Context ctx;
    private ArrayList<Product> productNameList;
    private ArrayList<String> arrayListProductUnknownAllergyStatus;
    private User user;

    ProductUnknownAllergyAdapter(@NonNull Context context, int row, int resource,
                                 @NonNull ArrayList<Product> products, ArrayList<String> status, User user) {
        super(context, row, resource, products);
        ctx = context;
        productNameList = products;
        arrayListProductUnknownAllergyStatus = status;
        this.user = user;
    }

    @Override
    public int getCount() {
        return productNameList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint({"ViewHolder", "InflateParams"}) View row = inflater.inflate(R.layout.list_item_product_unknown_allergy, null, true);
        TextView productName = row.findViewById(R.id.productName);
        TextView productStatus = row.findViewById(R.id.predictAllergy_btn);

        productName.setText(productNameList.get(position).getProductName());
        System.out.println("productNameList.get(position).getProductName() - " + productNameList.get(position).getProductName());

        productStatus.setText(arrayListProductUnknownAllergyStatus.get(position));
        System.out.println("arrayListProductUnknownAllergyStatus.get(position) - " + arrayListProductUnknownAllergyStatus.get(position));


        productStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.calculateUserProductUnknownAllergy(productNameList.get(position));

//                for(Map.Entry<Product, String> entry : user.getProductsUnknownAllergyStatusList().entrySet()) {
//                    mainActivity.getArrayListProductUnknownAllergy().add(entry.getKey());
//                    mainActivity.getArrayListProductUnknownAllergyStatus().add(entry.getValue());
//                }

            }
        });

        if (arrayListProductUnknownAllergyStatus.contains(User.PREDICT)) {
            productStatus.setBackgroundColor(ctx.getResources().getColor(R.color.colorAccent));
        }
        if (arrayListProductUnknownAllergyStatus.contains(User.ALLERGY_YES)) {
            productStatus.setBackgroundColor(ctx.getResources().getColor(R.color.colorError));
        }
        if (arrayListProductUnknownAllergyStatus.contains(User.ALLERGY_PROBABLY_YES)) {
            productStatus.setBackgroundColor(ctx.getResources().getColor(R.color.colorLightRed));
        }
        if (arrayListProductUnknownAllergyStatus.contains(User.ALLERGY_PROBABLY_NO)) {
            productStatus.setBackgroundColor(ctx.getResources().getColor(R.color.colorLightYellow));
        }
        if (arrayListProductUnknownAllergyStatus.contains(User.ALLERGY_NO)) {
            productStatus.setBackgroundColor(ctx.getResources().getColor(R.color.colorLightGreen));
        }

        return row;
    }


}