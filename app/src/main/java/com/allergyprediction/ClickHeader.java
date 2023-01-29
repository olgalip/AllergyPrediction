package com.allergyprediction;

import android.view.View;

class ClickHeader implements View.OnClickListener {
    View layout;
    public ClickHeader(View layout) {
        this.layout = layout;
    }
    @Override
    public void onClick(View v) {
        boolean isShown = layout.getVisibility() == View.VISIBLE;
        layout.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }
}
