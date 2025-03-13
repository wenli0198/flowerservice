package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.test.aoner.fanow.test.adapter_flower.WalletAccountTypeListAdapter_Cote_flower;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;


public class AddBankAccountDialog_Cote_flower extends BottomSheetDialog {

    private BaseActivity_flower baseActivity;
    private TextView prefix1Tv, prefix2Tv;
    private EditText mobile1Et, mobile2Et;
    private Button submitBtn;
    private TextView changeTv;

    private ImageView accountTypeIv;
    private TextView accountTypeTv;

    private WalletAccountTypeListAdapter_Cote_flower walletAccountTypeListAdapter;

    private String walletType = "WAVE";

    public static final String WalletType_Wave ="WAVE", WalletType_Others ="OTHERS";

    private final String pageTag;

    public AddBankAccountDialog_Cote_flower(@NonNull Context context,String pageTag) {
        super(context, R.style.BottomSheetDialogStyle);
        if (context instanceof BaseActivity_flower) baseActivity = (BaseActivity_flower) context;
        this.pageTag = StringUtil_flower.getSafeString(pageTag);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_bank_account_cote_flower);
        getWindow().setBackgroundDrawableResource(R.color.trans);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        init();
    }

    private void init(){

        prefix1Tv = findViewById(R.id.dialog_add_account_cote_tv_prefix_1);
        prefix2Tv = findViewById(R.id.dialog_add_account_cote_tv_prefix_2);
        mobile1Et = findViewById(R.id.dialog_add_account_cote_et_mobile_1);
        mobile2Et = findViewById(R.id.dialog_add_account_cote_et_mobile_2);
        submitBtn = findViewById(R.id.dialog_add_account_cote_btn_submit);
        changeTv = findViewById(R.id.dialog_add_account_cote_tv_change);
        accountTypeIv = findViewById(R.id.dialog_add_account_cote_iv_account_type);
        accountTypeTv = findViewById(R.id.dialog_add_account_cote_tv_account_type);

        mobile1Et.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+walletType+"_PHONE_INPUT").toUpperCase());
            else
                HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+walletType+"_PHONE_INPUT").toUpperCase(),StringUtil_flower.getSafeString(mobile1Et.getText().toString()));
        });

        mobile2Et.setOnFocusChangeListener((v,hasFocus) -> {
            if (hasFocus)
                HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+walletType+"_PHONE_CONFIRM_INPUT").toUpperCase());
            else
                HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+walletType+"_PHONE_CONFIRM_INPUT").toUpperCase(),StringUtil_flower.getSafeString(mobile2Et.getText().toString()));

        });


        String prefix = StaticConfig_flower.getMobilePrefix();
        prefix1Tv.setText(prefix);
        prefix2Tv.setText(prefix);

        changeTv.setOnClickListener(v -> changeType());

        View.OnClickListener submitBtnClickListener = v -> {

            HttpManager_flower.getInstance().saveUserBuriedPoint(pageTag+"_"+walletType+"_SAVE_CLICK");

            String account1 = StringUtil_flower.getSafeString(mobile1Et.getText().toString());
            String account2 = StringUtil_flower.getSafeString(mobile2Et.getText().toString());
            HttpManager_flower.getInstance().bindBankAccount(account1,account2, walletType);
            dismiss();
        };

        findViewById(R.id.dialog_add_bank_account_cote_ib_back).setOnClickListener(v -> dismiss());

        submitBtn.setOnClickListener(submitBtnClickListener);

    }

    @SuppressLint("SetTextI18n")
    public void changeType(){
        if (walletType.equalsIgnoreCase(WalletType_Wave)){
            walletType = WalletType_Others;
            accountTypeIv.setBackgroundResource(R.drawable.icon_cote_wallet_type_others);
            accountTypeTv.setText("Orange/MTN/MOOV");
            changeTv.setText("Je veux utiliser Wave");
        }else{
            walletType = WalletType_Wave;
            accountTypeIv.setBackgroundResource(R.drawable.icon_cote_wallet_type_wave);
            accountTypeTv.setText("Wave");
            changeTv.setText("je n'ai pas Wave");
        }
        HttpManager_flower.getInstance().saveUserBuriedPoint(pageTag+"_"+walletType+"_CLICK");
    }

}
