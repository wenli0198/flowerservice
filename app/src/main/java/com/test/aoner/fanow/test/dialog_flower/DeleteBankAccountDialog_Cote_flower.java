package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.CoteBankAccountList_flower;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteBankAccountDialog_Cote_flower extends Dialog {

    private Context mContext;

    private TextView accountTv;
    private ImageView accountTypeIv;
    private TextView accountTypeTv;

    private final CoteBankAccountList_flower.CoteBankAccount bankAccount;

    public DeleteBankAccountDialog_Cote_flower(@NonNull Context context, CoteBankAccountList_flower.CoteBankAccount bankAccount) {
        super(context);
        mContext = context;
        this.bankAccount = bankAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_bank_account_cote_flower);
        getWindow().setBackgroundDrawableResource(R.color.trans);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setCanceledOnTouchOutside(false);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init(){

        accountTv = findViewById(R.id.dialog_delete_bank_account_tv_account);
        accountTypeIv = findViewById(R.id.dialog_delete_bank_account_iv_wallet_type);
        accountTypeTv = findViewById(R.id.dialog_delete_bank_account_tv_wallet_type);

        findViewById(R.id.dialog_delete_bank_account_btn_cancel).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("DELETE_ACCOUNT_CANCEL_CLICK");
            dismiss();
        });

        findViewById(R.id.dialog_delete_bank_account_ib_close).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("DELETE_ACCOUNT_CANCEL_CLICK");
            dismiss();
        });

        if (bankAccount ==null) return;

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

        findViewById(R.id.dialog_delete_bank_account_btn_delete).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("DELETE_ACCOUNT_SUBMIT_CLICK");
            HttpManager_flower.getInstance().deleteBoundBankAccount(bankAccount.getId());
            dismiss();
        });

    }

    @Override
    public void show() {
        super.show();
        HttpManager_flower.getInstance().saveUserBuriedPoint("DELETE_ACCOUNT_DIALOG_COTE_START");
    }

    @Override
    public void dismiss() {
        super.dismiss();
        HttpManager_flower.getInstance().saveUserBuriedPoint("DELETE_ACCOUNT_DIALOG_COTE_END");
    }
}
