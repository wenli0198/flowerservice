package com.test.aoner.fanow.test.activity_fragment_flower.loan_flower;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.DatePickeView_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.InputView_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.TitleView_flower;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoanActivity_flower;

import org.json.JSONObject;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class AddPaystackBankAccountActivity_Niri_flower extends BaseLoanActivity_flower {

    private InputView_flower bankAccountIv;
    private DatePickeView_flower cardExpiresDpv;
    private InputView_flower cvvIv;
    private Button addBtn;

    private View operatedView;
    private ImageView operatedIv;
    private TextView operatedCompanyNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paystack_bank_account_flower);
        init();
    }

    private void init(){
        TitleView_flower titleView = findViewById(R.id.activity_add_paystack_bank_account_view_title);
        setBackBtn_flower(titleView.getBackIb());

        bankAccountIv = findViewById(R.id.activity_add_paystack_bank_account_iv_bank_account);
        cardExpiresDpv = findViewById(R.id.activity_add_paystack_bank_account_dpv_card_expires);
        cvvIv = findViewById(R.id.activity_add_paystack_bank_account_iv_cvv);
        addBtn = findViewById(R.id.activity_add_paystack_bank_account_btn_add);
        operatedView = findViewById(R.id.activity_add_paystack_bank_account_view_operated);
        operatedIv = findViewById(R.id.activity_add_paystack_bank_account_iv_operated);
        operatedCompanyNameTv = findViewById(R.id.activity_add_paystack_bank_account_tv_company_name);

        addBtn.setOnClickListener(v -> {
            String bankAccount = bankAccountIv.getInput();
            String cardExpires = cardExpiresDpv.getInput();
            String cvv = cvvIv.getInput();
            if (TextUtils.isEmpty(bankAccount)||TextUtils.isEmpty(cardExpires)||TextUtils.isEmpty(cvv)){
                showToast_flower(getString(R.string.word_input_tip));
                return;
            }
            String year = cardExpiresDpv.getYear();
            String month = cardExpiresDpv.getMonth();
            HttpManager_flower.getInstance().orderRepaySDK(bankAccount, year, month, cvv);
        });

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                operatedView,
                operatedIv,
                operatedCompanyNameTv);

    }

    public void onOrderRepaySDKResponse(JSONObject jsonObject){

        PaystackSdk.initialize(this);
        String accessCode = jsonObject.optString("accessCode");
        String pubKey = jsonObject.optString("pubKey");
        if (TextUtils.isEmpty(accessCode) || TextUtils.isEmpty(pubKey)) {
            showToast_flower("Operation failed, please try again later.");
            return;
        }

        String cardNumber = bankAccountIv.getInput();
        String expiryYear = cardExpiresDpv.getYear();
        String expiryMonth = cardExpiresDpv.getMonth();
        String cvv = cvvIv.getInput();
        Card card = new Card(cardNumber, Integer.parseInt(expiryMonth), Integer.parseInt(expiryYear), cvv);
//        if (!card.isValid()) {
//            showToast("Please enter the correct card number");
//            return;
//        }

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
        return "PAYSTACK";
    }


}