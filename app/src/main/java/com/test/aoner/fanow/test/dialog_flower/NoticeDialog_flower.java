package com.test.aoner.fanow.test.dialog_flower;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

public class NoticeDialog_flower extends Dialog {

    private final String title;
    private final String content;

    public NoticeDialog_flower(@NonNull Context context,String title,String content) {
        super(context);
        this.title = title;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notice_flower);

        getWindow().setBackgroundDrawableResource(R.color.trans);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        findViewById(R.id.dialog_notice_close).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_NOTICE_CLOSE_CLICK");
            dismiss();
        });

        findViewById(R.id.dialog_notice_confirm).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_NOTICE_CONFIRM_CLICK");
            dismiss();
        });

        TextView titleTv =findViewById(R.id.dialog_notice_tv_title);
        if (!TextUtils.isEmpty(title)) titleTv.setText(title);

        TextView contentTv = findViewById(R.id.dialog_notice_tv_content);
        if (!TextUtils.isEmpty(content)) contentTv.setText(content);

    }
}
