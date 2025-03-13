package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;

public class Msg2Dialog extends Dialog {

    private final String text;

    public Msg2Dialog(@NonNull BaseActivity_flower activity, String text) {
        super(activity);
        this.text = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_msg);
        getWindow().setBackgroundDrawableResource(R.color.trans);
        setCanceledOnTouchOutside(true);
        initView();
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void initView(){

        TextView textTv = findViewById(R.id.dialog_msg_tv_text);

        textTv.setText(StringUtil_flower.getSafeString(text));

        ThreadUtil_flower.getInstance().postDelay(this::dismiss,5000);
    }

}
