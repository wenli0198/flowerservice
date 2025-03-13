package com.test.aoner.fanow.test.v50.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.R;


public class PermissionAgreeDialog_flowerloan extends Dialog {


    private final Runnable cancleRunnable,agreeRunnable;

    public PermissionAgreeDialog_flowerloan(@NonNull Context context, Runnable cancleRunnable, Runnable agreeRunnable) {
        super(context);
        this.cancleRunnable = cancleRunnable;
        this.agreeRunnable = agreeRunnable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_permissionagree_flower);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView(){

        getWindow().setBackgroundDrawableResource(R.color.trans);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        Button cancleBtn = findViewById(R.id.dlpeag_btn_cancle);
        Button agreeBtn = findViewById(R.id.dlpeag_btn_agree);

        cancleBtn.setOnClickListener(v -> {
            dismiss();
            if (cancleRunnable!=null) cancleRunnable.run();
        });

        agreeBtn.setOnClickListener(v -> {
            dismiss();
            if (agreeRunnable!=null) agreeRunnable.run();
        });

    }
}
