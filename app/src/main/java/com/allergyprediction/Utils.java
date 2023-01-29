package com.allergyprediction;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {

    static void toast(Context ctx, int message) {
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}
