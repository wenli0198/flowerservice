package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

public class PhotoSelectDialog_flower extends BottomSheetDialog {

    private View tpView,faView;
    private TextView tpTv,faTv;
    private View tpSelectView,faSelectView;

    private final int colorYes,colorNo;

    private boolean isTp = true;

    private final Runnable takePhotoR,fromAlbumR;

    public PhotoSelectDialog_flower(@NonNull Context context,Runnable takePhotoR,Runnable fromAlbumR) {
        super(context,R.style.BottomSheetDialogStyle);
        colorYes = Color.WHITE;
        colorNo = Color.parseColor("#FFD4D4D4");
        this.takePhotoR = takePhotoR;
        this.fromAlbumR = fromAlbumR;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo_select_flower);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView(){

        tpView = findViewById(R.id.photo_select_view_tp);
        faView = findViewById(R.id.photo_select_view_fa);
        tpTv = findViewById(R.id.photo_select_tv_tp);
        faTv = findViewById(R.id.photo_select_tv_fa);
        tpSelectView = findViewById(R.id.photo_select_view_tp_select);
        faSelectView = findViewById(R.id.photo_select_view_fa_select);

        tpView.setOnClickListener(v -> {

            if (isTp) return;
            isTp = true;

            HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_PHOTO_SELECT_TAKE_A_PHOTO_CLICK");

            tpView.setBackgroundResource(R.drawable.ic_bg_language_select_yes);
            tpTv.setTextColor(colorYes);
            tpSelectView.setBackgroundResource(R.drawable.icon_language_select_yes);

            faView.setBackgroundResource(R.drawable.ic_bg_language_select_no);
            faTv.setTextColor(colorNo);
            faSelectView.setBackgroundResource(R.drawable.icon_language_select_no);

        });

        faView.setOnClickListener(v -> {

            if (!isTp) return;
            isTp = false;

            HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_PHOTO_SELECT_FROM_ALBUM_CLICK");

            tpView.setBackgroundResource(R.drawable.ic_bg_language_select_no);
            tpTv.setTextColor(colorNo);
            tpSelectView.setBackgroundResource(R.drawable.icon_language_select_no);

            faView.setBackgroundResource(R.drawable.ic_bg_language_select_yes);
            faTv.setTextColor(colorYes);
            faSelectView.setBackgroundResource(R.drawable.icon_language_select_yes);

        });

        findViewById(R.id.photo_select_btn_next).setOnClickListener(v -> {

            HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_PHOTO_SELECT_NEXT_CLICK", isTp ? "EN":"SW");
            if (isTp && takePhotoR!=null) takePhotoR.run();
            else if (!isTp && fromAlbumR!=null) fromAlbumR.run();
            dismiss();

        });

    }

    @Override
    public void dismiss() {
        super.dismiss();
        HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_PHOTO_SELECT_CLOSE_CLICK");
    }
}
