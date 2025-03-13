package com.test.aoner.fanow.test.view_flower.view_inflater_flower;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;

public class BankAccountViewInflater_flower {

    private final View mView;

    private TextView bankNameTv, bankAccountTv;
    private ImageView checkIv;
    private View clickView;

    private final int index;

    public BankAccountViewInflater_flower(View view, int index){
        mView = view;
        this.index = index;
        bankNameTv = mView.findViewById(R.id.item_bank_account_niri_tv_bank_name);
        bankAccountTv = mView.findViewById(R.id.item_bank_account_niri_tv_bank_account);
        checkIv = mView.findViewById(R.id.item_bank_account_niri_iv_check);
        clickView = mView.findViewById(R.id.item_bank_account_niri_view_click);
    }

    public void setCheck(boolean check){
        checkIv.setBackgroundResource(check ? R.drawable.icon_item_check_true:R.drawable.icon_item_check_false);
    }

    public void setBankName(String bankName){
        bankNameTv.setText(StringUtil_flower.getSafeString(bankName));
    }

    public void setBankAccount(String bankAccount){
        bankAccountTv.setText(StringUtil_flower.getSafeString(bankAccount));
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        clickView.setOnClickListener(onClickListener);
    }

}
