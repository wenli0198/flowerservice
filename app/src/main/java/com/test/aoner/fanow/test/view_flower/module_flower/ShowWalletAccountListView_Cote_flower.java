package com.test.aoner.fanow.test.view_flower.module_flower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.CoteBankAccountList_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShowWalletAccountListView_Cote_flower extends RelativeLayout {

    private LinearLayout accountListLayout;

    public ShowWalletAccountListView_Cote_flower(Context context) {
        super(context);
        initView(context);
    }

    public ShowWalletAccountListView_Cote_flower(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ShowWalletAccountListView_Cote_flower(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public ShowWalletAccountListView_Cote_flower(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context) {
        View view = View.inflate(context, R.layout.view_show_wallet_account_list_cote_flower, this);
        accountListLayout = view.findViewById(R.id.view_show_wallet_account_list_layout_account_list);
    }

    public void updateAccountList(){
        accountListLayout.removeAllViews();
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

        accountListLayout.addView(view);
    }

}
