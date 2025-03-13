package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.view_flower.module_flower.ShowWalletAccountListView_Cote_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.CoteBankAccountList_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SubmitAccountInfoDialog_Cote_flower extends Dialog {

    private BaseActivity_flower baseActivity;

    private ShowWalletAccountListView_Cote_flower showWalletAccountListView;

    private final View.OnClickListener onSubmitBtnClickListener;

    private LinearLayout groupLayout;

    public SubmitAccountInfoDialog_Cote_flower(@NonNull Context context, View.OnClickListener onSubmitBtnClickListener) {
        super(context);
        if (context instanceof BaseActivity_flower) baseActivity = (BaseActivity_flower) context;
        this.onSubmitBtnClickListener = onSubmitBtnClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_submit_account_info_cote_flower);
        getWindow().setBackgroundDrawableResource(R.color.trans);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setCanceledOnTouchOutside(false);
        init();
    }

    private void init(){

        findViewById(R.id.dialog_submit_account_info_ib_back).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("SUBMIT_ACCOUNT_INFO_DIALOG_COTE_CANCEL_CLICK");
            dismiss();
        });

        findViewById(R.id.dialog_submit_account_info_btn_submit).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("SUBMIT_ACCOUNT_INFO_DIALOG_COTE_SUBMIT_CLICK");
            if (onSubmitBtnClickListener !=null) onSubmitBtnClickListener.onClick(v);
            dismiss();
        });

        groupLayout = findViewById(R.id.dialog_submit_account_info_layout_group);

        ArrayList<CoteBankAccountList_flower.CoteBankAccount> accounts = CoteBankAccountList_flower.getInstance().getBankAccountList();
        for (CoteBankAccountList_flower.CoteBankAccount account:accounts){
            addAccount(account);
        }

    }

    private void addAccount(CoteBankAccountList_flower.CoteBankAccount bankAccount){
        View view = View.inflate(getContext(), R.layout.item_show_wallet_account_cote_flower, null);
        TextView accountTv = view.findViewById(R.id.item_show_wallet_account_tv_account);
        ImageView accountTypeIv = view.findViewById(R.id.item_show_wallet_account_iv_wallet_type);
        TextView accountTypeTv = view.findViewById(R.id.item_show_wallet_account_tv_wallet_type);

        accountTv.setText(bankAccount.getWalletMobile());
        accountTypeTv.setText(bankAccount.getWalletDesc());

        if (bankAccount.getBitmap()!=null){
            accountTypeIv.setImageBitmap(bankAccount.getBitmap());
        }else {
            ThreadUtil_flower.getInstance().runOnChildThread(() -> {
                try {
                    URL url = new URL(bankAccount.getWalletLogo());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    if (connection.getResponseCode()==200){
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        bankAccount.setBitmap(bitmap);
                        ThreadUtil_flower.getInstance().runOnUiThread(() -> accountTypeIv.setImageBitmap(bitmap));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        groupLayout.addView(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void show() {
        super.show();
        HttpManager_flower.getInstance().saveUserBuriedPoint("SUBMIT_ACCOUNT_INFO_DIALOG_COTE_START");
    }

    @Override
    public void dismiss() {
        super.dismiss();
        HttpManager_flower.getInstance().saveUserBuriedPoint("SUBMIT_ACCOUNT_INFO_DIALOG_COTE_END");
    }
}
