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

public class MsgDialog extends Dialog {

    private final String text;
    private final Runnable onClickListener;

    public MsgDialog(@NonNull BaseActivity_flower activity, String text, Runnable onClickListener) {
        super(activity);
        this.text = text;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_msg);
        getWindow().setBackgroundDrawableResource(R.color.trans);
        setCanceledOnTouchOutside(false);
        initView();
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void initView(){

        TextView textTv = findViewById(R.id.dialog_msg_tv_text);

        textTv.setText(StringUtil_flower.getSafeString(text));

        ThreadUtil_flower.getInstance().postDelay(() -> {
            dismiss();
            if (onClickListener!=null) onClickListener.run();
        },2000);
    }

}
