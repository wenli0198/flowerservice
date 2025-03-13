package com.test.aoner.fanow.test.activity_fragment_flower.loan_flower;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.NiriBankAccountList_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.view_inflater_flower.BankAccountViewInflater_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.TitleView_flower;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoanActivity_flower;

import org.json.JSONObject;

import java.util.ArrayList;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class RepayPaystackActivity_Niri_flower extends BaseLoanActivity_flower {

    private LinearLayout groupLayout;
    private Button addBankCardBtn, repayBtn;

    private final ArrayList<BankAccountViewInflater_flower> bankAccountViewInflaters = new ArrayList<>();

    private int checkIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_paystack_niri_flower);
        init();
    }

    private void init(){
        TitleView_flower titleView = findViewById(R.id.activity_repay_paystack_list_view_title);
        setBackBtn_flower(titleView.getBackIb());

        groupLayout = findViewById(R.id.activity_repay_paystack_list_layout_group);
        addBankCardBtn = findViewById(R.id.activity_repay_paystack_list_btn_add_bank_card);
        repayBtn = findViewById(R.id.activity_repay_paystack_list_btn_repay);

        addBankCardBtn.setOnClickListener(v -> startActivity_flower(AddPaystackBankAccountActivity_Niri_flower.class));

        repayBtn.setOnClickListener(v -> {
            if (NiriBankAccountList_flower.getInstance().getBankAccountList().size()==0){
                showToast_flower("Please add a bank account first");
                return;
            }
            if (checkIndex <0|| checkIndex >= NiriBankAccountList_flower.getInstance().getBankAccountList().size()){
                showToast_flower("Please select a bank account first");
                return;
            }
            NiriBankAccountList_flower.NiriBankAccount bankAccount = NiriBankAccountList_flower.getInstance().getBankAccountList().get(checkIndex);
            HttpManager_flower.getInstance().orderRepaySDK(bankAccount.getCardno(), bankAccount.getExpiryYear(), bankAccount.getExpiryMonth(), bankAccount.getCvv());
        });

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                findViewById(R.id.activity_repay_paystack_list_view_operated),
                findViewById(R.id.activity_repay_paystack_list_iv_operated_logo),
                findViewById(R.id.activity_repay_paystack_list_tv_company_name));

        updateBankAccountList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpManager_flower.getInstance().bankList_niri();
    }

    public void onFetchBankListResponse(){
        updateBankAccountList();
    }

    private void updateBankAccountList(){
        bankAccountViewInflaters.clear();
        ArrayList<NiriBankAccountList_flower.NiriBankAccount> bankAccounts = NiriBankAccountList_flower.getInstance().getBankAccountList();
        for (int i = 0;i<bankAccounts.size();i++){
            addBankAccount(bankAccounts.get(i),i);
        }

        for (int i = 0; i< bankAccountViewInflaters.size(); i++){
            BankAccountViewInflater_flower bankAccountViewInflater = bankAccountViewInflaters.get(i);
            final int finalI = i;
            bankAccountViewInflater.setOnClickListener(v -> {
                for (int j = 0; j< bankAccountViewInflaters.size(); j++){
                    bankAccountViewInflaters.get(j).setCheck(finalI==j);
                    checkIndex = finalI;
                }
            });
        }
    }

    @SuppressLint("InflateParams")
    private void addBankAccount(NiriBankAccountList_flower.NiriBankAccount bankAccount, int index){
        if (bankAccount==null) return;
        View bankAccountView = LayoutInflater.from(this).inflate(R.layout.item_bank_account_niri_flower,null);
        BankAccountViewInflater_flower bankAccountViewInflater = new BankAccountViewInflater_flower(bankAccountView,index);
        bankAccountViewInflater.setBankName(bankAccount.getBankName());
        bankAccountViewInflater.setBankAccount(bankAccount.getCardno());
        bankAccountViewInflaters.add(bankAccountViewInflater);
        groupLayout.addView(bankAccountView);
    }

    public void onOrderRepaySDKResponse(JSONObject jsonObject){
        if (NiriBankAccountList_flower.getInstance().getBankAccountList().size()==0|| checkIndex <0|| checkIndex >= NiriBankAccountList_flower.getInstance().getBankAccountList().size()){
            return;
        }
        NiriBankAccountList_flower.NiriBankAccount bankAccount = NiriBankAccountList_flower.getInstance().getBankAccountList().get(checkIndex);

        PaystackSdk.initialize(this);
        String accessCode = jsonObject.optString("accessCode");
        String pubKey = jsonObject.optString("pubKey");
        if (TextUtils.isEmpty(accessCode) || TextUtils.isEmpty(pubKey)) {
            showToast_flower("Operation failed, please try again later.");
            return;
        }

        String cardNumber = bankAccount.getCardno();
        String expiryYear = bankAccount.getExpiryYear();
        String expiryMonth = bankAccount.getExpiryMonth();
        String cvv = bankAccount.getCvv();
        Card card = new Card(cardNumber, Integer.parseInt(expiryMonth), Integer.parseInt(expiryYear), cvv);

        Charge charge = new Charge();
        charge.setCard(card);
        charge.setAccessCode(accessCode);
        PaystackSdk.setPublicKey(pubKey);
        PaystackSdk.chargeCard(this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                showToast_flower("Successful repayment");
                ThreadUtil_flower.getInstance().postDelay(() -> HttpManager_flower.getInstance().requestUserProcess(), 1000);
            }
            @Override
            public void beforeValidate(Transaction transaction) {}
            @Override
            public void onError(Throwable error, Transaction transaction) {
                showToast_flower(transaction.getReference());
            }
        });
    }

    @Override
    public String getPagetag() {
        return "REPAY_PAYSTACK";
    }
}