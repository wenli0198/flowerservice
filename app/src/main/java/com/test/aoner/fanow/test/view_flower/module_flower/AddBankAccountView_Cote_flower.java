package com.test.aoner.fanow.test.view_flower.module_flower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.PagetagInterface;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.CoteBankAccountList_flower;
import com.test.aoner.fanow.test.dialog_flower.AddBankAccountDialog2_Cote_flower;
import com.test.aoner.fanow.test.dialog_flower.AddBankAccountDialog_Cote_flower;
import com.test.aoner.fanow.test.dialog_flower.DeleteBankAccountDialog_Cote_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddBankAccountView_Cote_flower extends LinearLayout implements PagetagInterface {

    private LinearLayout groupLayout;
    private View addAccountBtnView;

    private final ArrayList<View> accountArrViews = new ArrayList<>();

    public static boolean addAccountFlag =false;

    private String pageTag;

    public AddBankAccountView_Cote_flower(Context context) {
        super(context);
        init(context);
    }

    public AddBankAccountView_Cote_flower(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddBankAccountView_Cote_flower(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

        View view = View.inflate(context, R.layout.view_add_bank_account_cote_flower,this);
        groupLayout = view.findViewById(R.id.view_add_bank_account_cote_layout_group);
        addAccountBtnView = view.findViewById(R.id.view_add_bank_account_cote_layout_add_account_btn);

        addAccountBtnView.setOnClickListener(v -> {

            HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_ADD_CLICK").toUpperCase());

            if (CoteBankAccountList_flower.getInstance().getBankAccountList().size()>0){
                new AddBankAccountDialog2_Cote_flower(context, CoteBankAccountList_flower.getInstance().getBankAccountList().get(0),getPagetag()).show();
            }else {
                new AddBankAccountDialog_Cote_flower(context,getPagetag()).show();
            }
        });

    }

    public void updateAccountList(){
        accountArrViews.clear();
        groupLayout.removeAllViews();
        ArrayList<CoteBankAccountList_flower.CoteBankAccount> accounts = CoteBankAccountList_flower.getInstance().getBankAccountList();
        for (CoteBankAccountList_flower.CoteBankAccount account:accounts){
            addAccount(account);
        }
        if (accountArrViews.size()==1) accountArrViews.get(0).findViewById(R.id.item_wallet_account_ib_delete).setVisibility(GONE);

        if (addAccountFlag){
            addAccountFlag = false;
            if (CoteBankAccountList_flower.getInstance().getBankAccountList().size()==1){
                new AddBankAccountDialog2_Cote_flower(getContext(), CoteBankAccountList_flower.getInstance().getBankAccountList().get(0),getPagetag()).show();
            }
        }
    }

    private void addAccount(CoteBankAccountList_flower.CoteBankAccount bankAccount){

        View view = View.inflate(getContext(), R.layout.item_wallet_account_cote_flower, null);
        TextView accountTv = view.findViewById(R.id.item_wallet_account_tv_account);
        ImageView accountTypeIv = view.findViewById(R.id.item_wallet_account_iv_wallet_type);
        TextView accountTypeTv = view.findViewById(R.id.item_wallet_account_tv_wallet_type);
        ImageButton deleteIb = view.findViewById(R.id.item_wallet_account_ib_delete);

        accountArrViews.add(view);

        accountTv.setText(bankAccount.getWalletMobile());
        accountTypeTv.setText(bankAccount.getWalletDesc());
        deleteIb.setOnClickListener(v -> new DeleteBankAccountDialog_Cote_flower(getContext(),bankAccount).show());

        groupLayout.addView(view);

        if (bankAccount.getBitmap()!=null){
            accountTypeIv.setImageBitmap(bankAccount.getBitmap());
        }else {
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
                        bankAccount.setBitmap(bitmap);
                        ThreadUtil_flower.getInstance().runOnUiThread(() -> {
                            if (accountTypeIv!=null) accountTypeIv.setImageBitmap(bitmap);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public void setPageTag(String pageTag) {
        this.pageTag = pageTag;
    }

    @Override
    public String getPagetag() {
        return StringUtil_flower.getSafeString(pageTag);
    }
}
