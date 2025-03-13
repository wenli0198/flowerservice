package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

public class LanguageSelectDialog_flower extends BottomSheetDialog {

    private final int colorYes,colorNo;

    private boolean isEn = true;

    private final Runnable afterRunnable;

    private final boolean canCancel;

    public LanguageSelectDialog_flower(@NonNull Context context,Runnable afterRunnable,boolean canCancel) {
        super(context,R.style.BottomSheetDialogStyle);
        colorYes = Color.WHITE;
        colorNo = Color.parseColor("#FFD4D4D4");
        this.afterRunnable = afterRunnable;
        this.canCancel = canCancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_language_select_flower);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setCanceledOnTouchOutside(canCancel);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView(){

        View enView = findViewById(R.id.language_select_view_en);
        TextView enTv = findViewById(R.id.language_select_tv_en);
        View enSelectView = findViewById(R.id.language_select_view_en_select);

        View swView = findViewById(R.id.language_select_view_sw);
        TextView swTv = findViewById(R.id.language_select_tv_sw);
        View swSelectView = findViewById(R.id.language_select_view_sw_select);

        if (!UserInfoHelper_flower.getInstance().isLanguageEn()){
            isEn = false;
            swView.setBackgroundResource(R.drawable.ic_bg_language_select_yes);
            swTv.setTextColor(colorYes);
            swSelectView.setBackgroundResource(R.drawable.icon_language_select_yes);
            enView.setBackgroundResource(R.drawable.ic_bg_language_select_no);
            enTv.setTextColor(colorNo);
            enSelectView.setBackgroundResource(R.drawable.icon_language_select_no);
        }

        enView.setOnClickListener(v -> {

            if (isEn) return;
            isEn = true;

            HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_LANGUAGE_SELECT_ENGLISH_CLICK");

            enView.setBackgroundResource(R.drawable.ic_bg_language_select_yes);
            enTv.setTextColor(colorYes);
            enSelectView.setBackgroundResource(R.drawable.icon_language_select_yes);

            swView.setBackgroundResource(R.drawable.ic_bg_language_select_no);
            swTv.setTextColor(colorNo);
            swSelectView.setBackgroundResource(R.drawable.icon_language_select_no);

        });

        swView.setOnClickListener(v -> {

            if (!isEn) return;
            isEn = false;

            HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_LANGUAGE_SELECT_SWAHILI_CLICK");

            swView.setBackgroundResource(R.drawable.ic_bg_language_select_yes);
            swTv.setTextColor(colorYes);
            swSelectView.setBackgroundResource(R.drawable.icon_language_select_yes);

            enView.setBackgroundResource(R.drawable.ic_bg_language_select_no);
            enTv.setTextColor(colorNo);
            enSelectView.setBackgroundResource(R.drawable.icon_language_select_no);

        });

        findViewById(R.id.language_select_btn_next).setOnClickListener(v -> {

            HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_LANGUAGE_SELECT_NEXT_CLICK",isEn ? "EN":"SW");

            UserInfoHelper_flower.getInstance().setLanguage(isEn);

            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(isEn ? "en":"sw"));

            dismiss();

        });

    }

    @Override
    public void dismiss() {
        super.dismiss();
        HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_LANGUAGE_SELECT_CLOSE_CLICK");
        if (afterRunnable!=null) afterRunnable.run();
    }
}
