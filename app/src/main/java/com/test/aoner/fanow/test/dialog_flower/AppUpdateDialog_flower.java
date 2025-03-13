package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;

public class AppUpdateDialog_flower extends Dialog {

    private BaseActivity_flower activity;

    private final String updateUri;

    public AppUpdateDialog_flower(@NonNull BaseActivity_flower activity, String updateUri) {
        super(activity);
        this.activity = activity;
        this.updateUri = updateUri;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_app_update_flower);
        initView();
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void initView(){

        getWindow().setBackgroundDrawableResource(R.color.trans);

        ((TextView)findViewById(R.id.dialog_app_update_tv_title)).setText("App Update");
        ((TextView)findViewById(R.id.dialog_app_update_tv_text)).setText("New version found, need to update now?");

        Button cancelBtn = findViewById(R.id.dialog_app_update_btn_cancel);
        Button updateBtn = findViewById(R.id.dialog_app_update_btn_submit);
        cancelBtn.setText("Cancel");
        updateBtn.setText("Update");
        cancelBtn.setTextColor(getContext().getColor(R.color.gray_dd));
        updateBtn.setTextColor(getContext().getColor(R.color.black));
        cancelBtn.setOnClickListener(view -> {
            dismiss();
            activity.finish();
            activity = null;
        });
        updateBtn.setOnClickListener(view -> getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(updateUri))));

        setCanceledOnTouchOutside(false);
    }

}
