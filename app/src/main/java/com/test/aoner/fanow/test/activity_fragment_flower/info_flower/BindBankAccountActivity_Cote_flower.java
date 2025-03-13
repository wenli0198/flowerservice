package com.test.aoner.fanow.test.activity_fragment_flower.info_flower;

import android.os.Bundle;
import android.widget.Button;

import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseInfoActivity_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.CoteBankAccountList_flower;
import com.test.aoner.fanow.test.constant_flower.Url_flower;
import com.test.aoner.fanow.test.dialog_flower.SubmitAccountInfoDialog_Cote_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.module_flower.AddBankAccountView_Cote_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.TitleView_flower;
import com.test.aoner.fanow.test.R;

import java.util.TreeMap;

public class BindBankAccountActivity_Cote_flower extends BaseInfoActivity_flower {

    //private EditText firstNameEt,middleNameEt,lastNameEt;
    private AddBankAccountView_Cote_flower addBankAccountView;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_bank_account_cote_flower);
        init();
    }

    private void init(){

        TitleView_flower titleView = findViewById(R.id.activity_bind_bank_account_view_title);
        setBackBtn_flower(titleView.getBackIb());

        nextBtn = findViewById(R.id.activity_bind_bank_account_btn_submit);
        addBankAccountView = findViewById(R.id.activity_bind_bank_account_view_add_bank_account);

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                findViewById(R.id.activity_bind_bank_account_view_operated),
                findViewById(R.id.activity_bind_bank_account_iv_operated),
                findViewById(R.id.activity_bind_bank_account_tv_operated_company_name));

        addBankAccountView.setPageTag(getPagetag());

        nextBtn.setOnClickListener(v -> {

            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_SAVE_CLICK");

            if (CoteBankAccountList_flower.getInstance().getBankAccountList().size()==0){
                showToast_flower("Veuillez ajouter au moins un compte de réception de prêt valide.");
            }else {
                new SubmitAccountInfoDialog_Cote_flower(this, v1 -> HttpManager_flower.getInstance().uploadInfo(Url_flower.PATH_BVN_INFO, parseData())).show();
            }
        });
    }


    private TreeMap<String,String> parseData() {
        return new TreeMap<String,String>(){{
            put("bvn", UserInfoHelper_flower.getInstance().getMobile());
        }};
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpManager_flower.getInstance().fetchBoundBankAccount_Cote();
    }

    public void onBindBankAccountResponse(){
        AddBankAccountView_Cote_flower.addAccountFlag = true;
        HttpManager_flower.getInstance().fetchBoundBankAccount_Cote();
    }

    public void onDeleteBankAccountResponse(){
        showToast_flower("Supprimé avec succès");
        HttpManager_flower.getInstance().fetchBoundBankAccount_Cote();
    }

    public void onFetchBankAccountListResponse() {
        addBankAccountView.updateAccountList();
    }

}