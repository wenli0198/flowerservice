package com.test.aoner.fanow.test.dialog_flower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.adapter_flower.WalletAccountTypeListAdapter_Cote_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.AppShowInfo_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.CoteBankAccountList_flower;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddBankAccountDialog2_Cote_flower extends BottomSheetDialog {

    private BaseActivity_flower baseActivity;
    private EditText account1Et, account2Et;

    private ImageView accountTypeIv;
    private TextView accountTypeTv;
    private ViewGroup accountTypeListLayout;

    private int itemHeight =0;

    private String walletChannelStr;

    private WalletAccountTypeListAdapter_Cote_flower walletAccountTypeListAdapter;

    private final CoteBankAccountList_flower.CoteBankAccount bankAccount;

    private final String pageTag;

    public AddBankAccountDialog2_Cote_flower(@NonNull Context context, CoteBankAccountList_flower.CoteBankAccount bankAccount,String pageTag) {
        super(context,R.style.BottomSheetDialogStyle);
        if (context instanceof BaseActivity_flower) baseActivity = (BaseActivity_flower) context;
        this.bankAccount = bankAccount;
        this.pageTag = StringUtil_flower.getSafeString(pageTag);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_bank_account_2_cote_flower);
        getWindow().setBackgroundDrawableResource(R.color.trans);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){

        account1Et = findViewById(R.id.dialog_add_account_2_et_account_1);
        account2Et = findViewById(R.id.dialog_add_account_2_et_account_2);

        account1Et.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+getWalletChannel()+"_PHONE_INPUT").toUpperCase());
            else
                HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+getWalletChannel()+"_PHONE_INPUT").toUpperCase(),StringUtil_flower.getSafeString(account1Et.getText().toString()));
        });

        account2Et.setOnFocusChangeListener((v,hasFocus) -> {
            if (hasFocus)
                HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+getWalletChannel()+"_PHONE_CONFIRM_INPUT").toUpperCase());
            else
                HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+getWalletChannel()+"_PHONE_CONFIRM_INPUT").toUpperCase(),StringUtil_flower.getSafeString(account2Et.getText().toString()));

        });


        findViewById(R.id.dialog_add_account_ib_back).setOnClickListener(v -> {
            dismiss();
        });
        findViewById(R.id.dialog_add_account_2_tv_cancel).setOnClickListener(v -> {
            dismiss();
        });

        TextView showAccountTv = findViewById(R.id.dialog_add_account_2_tv_show_account);
        ImageView showAccountIv = findViewById(R.id.dialog_add_account_2_tv_show_account_type);
        if (bankAccount !=null){
            showAccountTv.setText(bankAccount.getWalletMobile());
            ThreadUtil_flower.getInstance().runOnChildThread(() -> {
                try {
                    if (TextUtils.isEmpty(bankAccount.getWalletLogo())) return;
                    URL url = new URL(bankAccount.getWalletLogo());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    if (connection.getResponseCode()==200){
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ThreadUtil_flower.getInstance().runOnUiThread(() -> {
                            if (showAccountIv!=null) showAccountIv.setImageBitmap(bitmap);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        ViewGroup accountTypeLayout = findViewById(R.id.dialog_add_account_2_layout_account_type);
        accountTypeIv = findViewById(R.id.dialog_add_account_2_iv_account_type);
        accountTypeTv = findViewById(R.id.dialog_add_account_2_tv_account_type);
        accountTypeListLayout = findViewById(R.id.dialog_add_account_2_layout_account_type_list);
        ListView accountTypeListLv = findViewById(R.id.dialog_add_account_2_lv_account_type_list);
        accountTypeLayout.setOnClickListener(v -> accountTypeListLayout.setVisibility(accountTypeListLayout.getVisibility()==View.VISIBLE ? View.GONE:View.VISIBLE));

        accountTypeLayout.setOnClickListener(v -> accountTypeListLayout.setVisibility(accountTypeListLayout.getVisibility()==View.VISIBLE ? View.GONE:View.VISIBLE));
        walletAccountTypeListAdapter = new WalletAccountTypeListAdapter_Cote_flower(getContext());
        accountTypeListLv.setAdapter(walletAccountTypeListAdapter);
        accountTypeListLv.setOnItemClickListener((parent, view, position, id) -> {
            accountTypeListLayout.setVisibility(View.GONE);
            AppShowInfo_flower.WalletChannel walletChannel = walletAccountTypeListAdapter.getItem(position);
            walletChannelStr = walletChannel.getValue();
            accountTypeTv.setText(walletChannel.getDesc());
            HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+getWalletChannel()+"_CLICK").toUpperCase());
            if (walletChannel.getBitmap()!=null){
                accountTypeIv.setImageBitmap(walletChannel.getBitmap());
            }else {
                ThreadUtil_flower.getInstance().runOnChildThread(() -> {
                    try {
                        if (TextUtils.isEmpty(walletChannel.getLogo())) return;
                        URL url = new URL(walletChannel.getLogo());
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(5000);
                        connection.setRequestMethod("GET");
                        if (connection.getResponseCode()==200){
                            InputStream inputStream = connection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            walletChannel.setBitmap(bitmap);
                            ThreadUtil_flower.getInstance().runOnUiThread(() -> {
                                if (accountTypeIv !=null) accountTypeIv.setImageBitmap(bitmap);
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        ViewGroup.LayoutParams layoutParams = accountTypeListLv.getLayoutParams();
        if (itemHeight ==0) itemHeight =layoutParams.height;
        layoutParams.height = walletAccountTypeListAdapter.getCount() * itemHeight;
        accountTypeListLv.setLayoutParams(layoutParams);

        setCanceledOnTouchOutside(false);

        findViewById(R.id.dialog_add_account_2_btn_submit).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+getWalletChannel()+"_SAVE_CLICK").toUpperCase());
            String account1 = StringUtil_flower.getSafeString(account1Et.getText().toString());
            String account2 = StringUtil_flower.getSafeString(account2Et.getText().toString());
            HttpManager_flower.getInstance().bindBankAccount(account1,account2, StringUtil_flower.getSafeString(walletChannelStr));
            dismiss();
        });

    }

    private String getWalletChannel(){
        if (TextUtils.isEmpty(walletChannelStr)) return "NO_SELECT";
        return walletChannelStr;
    }

}
