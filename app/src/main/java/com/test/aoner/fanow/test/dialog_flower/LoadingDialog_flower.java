package com.test.aoner.fanow.test.dialog_flower;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.R;

public class LoadingDialog_flower extends Dialog {


    public LoadingDialog_flower(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading_flower);
        getWindow().setBackgroundDrawableResource(R.color.trans);
        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
    }
}
