package com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.RepayInfo_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.dialog_flower.CustomerServiceDialog_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoanFragment_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.loan_flower.RepayPaystackActivity_Niri_flower;


public class RepayFragment_flower extends BaseLoanFragment_flower {

    private TextView totalRepaymentTv, dueDateTv;
    private TextView loanAmountTv, interestTv, serviceFeeTv, vatTv,overdueDaysTv, overduePenaltyTv;

    private View planALayout,planBLayout;
    private ImageView planAIv,planBIv;
    private View planADetailLayout;
    private View planAFoldClickLayout;
    private ImageView planAFoldIv;
    private TextView extendedServiceChargesTv,daysOfExtensionTv;
    private TextView dueDateAfterExtension1Tv,dueDateAfterExtension2Tv;
    private TextView totalRepaymentDueTv;
    private TextView paymentAmountTv;

    private boolean deferredFlag = false;

    private TextView offlineRepaymentTv_niri;
    private View offlineRepaymentView_niri;
    private TextView offlineRepaymentBankNameTv_niri, offlineRepaymentBankAccountTv_niri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repay_flower, container, false);
        init(view);
        return view;
    }

    private void init(View view){

        totalRepaymentTv = view.findViewById(R.id.fragment_repay_tv_total_repayment);
        dueDateTv = view.findViewById(R.id.fragment_repay_tv_due_date);
        loanAmountTv = view.findViewById(R.id.fragment_repay_tv_loan_amount);
        interestTv = view.findViewById(R.id.fragment_repay_tv_interest);
        serviceFeeTv = view.findViewById(R.id.fragment_repay_tv_service_fee);
        overdueDaysTv = view.findViewById(R.id.fragment_repay_tv_overdue_days);
        vatTv = view.findViewById(R.id.fragment_repay_tv_vat);
        View vatLayout = view.findViewById(R.id.fragment_repay_layout_vat);
        overduePenaltyTv = view.findViewById(R.id.fragment_repay_tv_overdue_penalty);
        offlineRepaymentTv_niri = view.findViewById(R.id.fragment_repay_tv_offline_repayment_niri);
        offlineRepaymentView_niri = view.findViewById(R.id.fragment_repay_view_offline_repay_niri);
        ImageView copyBankNameIv_niri = view.findViewById(R.id.fragment_repay_iv_offline_repay_copy_bank_name_niri);
        ImageView copyBankAccountIv_niri = view.findViewById(R.id.fragment_repay_iv_offline_repay_copy_bank_account_niri);
        offlineRepaymentBankNameTv_niri = view.findViewById(R.id.fragment_repay_tv_offline_repay_bank_name_niri);
        offlineRepaymentBankAccountTv_niri = view.findViewById(R.id.fragment_repay_tv_offline_repay_bank_account_niri);
        planALayout = view.findViewById(R.id.fragment_repay_layout_plan_a);
        planBLayout = view.findViewById(R.id.fragment_repay_layout_plan_b);
        planAIv = view.findViewById(R.id.fragment_repay_iv_plan_a);
        planBIv = view.findViewById(R.id.fragment_repay_iv_plan_b);
        planADetailLayout = view.findViewById(R.id.fragment_repay_layout_plan_a_detail);
        planAFoldClickLayout = view.findViewById(R.id.fragment_repay_layout_plan_a_fold_click);
        planAFoldIv = view.findViewById(R.id.fragment_repay_iv_plan_a_fold);
        extendedServiceChargesTv = view.findViewById(R.id.fragment_repay_tv_extended_service_charges);
        daysOfExtensionTv = view.findViewById(R.id.fragment_repay_tv_days_of_extension);
        dueDateAfterExtension1Tv = view.findViewById(R.id.fragment_repay_tv_due_date_after_extension_1);
        dueDateAfterExtension2Tv = view.findViewById(R.id.fragment_repay_plan_b_tv_due_date_after_extension_2);
        totalRepaymentDueTv = view.findViewById(R.id.fragment_repay_plan_b_tv_total_repayment_due);
        paymentAmountTv = view.findViewById(R.id.fragment_repay_tv_payment_amount);

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Cote)) vatLayout.setVisibility(VISIBLE);

        view.findViewById(R.id.fragment_repay_ib_customer_service).setOnClickListener(v -> new CustomerServiceDialog_flower(requireContext()).show());

        view.findViewById(R.id.fragment_repay_btn_query_process).setOnClickListener(v -> HttpManager_flower.getInstance().requestUserProcess());
        view.findViewById(R.id.fragment_repay_btn_repay).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag()+"_SUBMIT_CLICK").toUpperCase());

            switch (StaticConfig_flower.Select_Country){
                case Constant_flower.Country_Cote:
                case Constant_flower.Country_Ghana:
                case Constant_flower.Country_Vietnam:
                    HttpManager_flower.getInstance().orderRepayH5_gh_cl_vi(deferredFlag ? "YES":"NO");
                    break;
                case Constant_flower.Country_Uganda:
                case Constant_flower.Country_Kenya:
                case Constant_flower.Country_Tanzan:
                    HttpManager_flower.getInstance().orderRepayH5_ug_ke(deferredFlag ? "YES":"NO");
                    break;
                case Constant_flower.Country_Nigeria:
                    HttpManager_flower.getInstance().bankList_niri();
                    break;
            }
        });

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Nigeria)){
            offlineRepaymentTv_niri.setVisibility(VISIBLE);
            offlineRepaymentTv_niri.setOnClickListener(v -> HttpManager_flower.getInstance().createVirtualAccount());
        }

        copyBankNameIv_niri.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", RepayInfo_flower.getInstance().getVirtualBankName());
            cm.setPrimaryClip(mClipData);
            Toast.makeText(requireContext(), getString(R.string.word_copy_success), Toast.LENGTH_SHORT).show();
        });

        copyBankAccountIv_niri.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", RepayInfo_flower.getInstance().getVirtualBankAccount());
            cm.setPrimaryClip(mClipData);
            Toast.makeText(requireContext(), getString(R.string.word_copy_success), Toast.LENGTH_SHORT).show();
        });

        GlobalConfig_flower.getInstance().handleOperatedView(requireActivity(),
                view.findViewById(R.id.fragment_repay_view_operated),
                view.findViewById(R.id.fragment_repay_iv_operated_logo),
                view.findViewById(R.id.fragment_repay_tv_company_name));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HttpManager_flower.getInstance().getRepayDetail();
    }

    @SuppressLint("SetTextI18n")
    public void updateRepayDetail(){

        String moneyUnit = StringUtil_flower.getSafeString(StaticConfig_flower.getMoneyUnit()).toUpperCase();

        totalRepaymentTv.setText(moneyUnit+" "+ RepayInfo_flower.getInstance().getRepayAmount());
        dueDateTv.setText(RepayInfo_flower.getInstance().getRepayDate());
        loanAmountTv.setText(RepayInfo_flower.getInstance().getAmount()+" "+moneyUnit);
        interestTv.setText(RepayInfo_flower.getInstance().getInterest()+" "+moneyUnit);
        overdueDaysTv.setText(RepayInfo_flower.getInstance().getOverdueDay()+" "+getString(R.string.word_days));
        overduePenaltyTv.setText(RepayInfo_flower.getInstance().getOverdueAmount()+" "+moneyUnit);

        paymentAmountTv.setText(RepayInfo_flower.getInstance().getRepayAmount()+" "+moneyUnit);

        vatTv.setText(RepayInfo_flower.getInstance().getServiceVatFee()+" "+moneyUnit);

        if (RepayInfo_flower.getInstance().isOverdue()){
            overdueDaysTv.setTextColor(Color.RED);
        }else {
            overdueDaysTv.setTextColor(requireContext().getColor(R.color.blue));
        }

        switch (StaticConfig_flower.Select_Country){
            case Constant_flower.Country_Cote:
                serviceFeeTv.setText(RepayInfo_flower.getInstance().getRepayProFee()+" "+moneyUnit);
                HttpManager_flower.getInstance().fetchBoundBankAccount_Cote();
                break;
            case Constant_flower.Country_Nigeria:
                serviceFeeTv.setText(RepayInfo_flower.getInstance().getServiceFee()+" "+moneyUnit);
                if (!TextUtils.isEmpty(RepayInfo_flower.getInstance().getVirtualBankAccount())) {
                    offlineRepaymentView_niri.setVisibility(VISIBLE);
                    offlineRepaymentTv_niri.setVisibility(GONE);
                    offlineRepaymentBankNameTv_niri.setText(RepayInfo_flower.getInstance().getVirtualBankName());
                    offlineRepaymentBankAccountTv_niri.setText(RepayInfo_flower.getInstance().getVirtualBankAccount());
                } else {
                    offlineRepaymentView_niri.setVisibility(GONE);
                    offlineRepaymentTv_niri.setVisibility(VISIBLE);
                }
                break;
            default:
                serviceFeeTv.setText(RepayInfo_flower.getInstance().getServiceFee()+" "+moneyUnit);
        }


        if (RepayInfo_flower.getInstance().getDeferredFlag()){
            planBLayout.setVisibility(VISIBLE);
        }else {
            planBLayout.setVisibility(GONE);
            planADetailLayout.setVisibility(VISIBLE);
            planAFoldIv.setBackgroundResource(R.drawable.icon_repay_plan_a_unfolded);
        }

        planALayout.setOnClickListener(v -> setDeferredFlag(false));
        planBLayout.setOnClickListener(v -> setDeferredFlag(true));

        planAFoldClickLayout.setOnClickListener(v -> {
            if (planADetailLayout.getVisibility() == GONE){
                planADetailLayout.setVisibility(VISIBLE);
                planAFoldIv.setBackgroundResource(R.drawable.icon_repay_plan_a_unfolded);
            }else {
                planADetailLayout.setVisibility(GONE);
                planAFoldIv.setBackgroundResource(R.drawable.icon_repay_plan_a_folded);
            }
        });

        extendedServiceChargesTv.setText(RepayInfo_flower.getInstance().getDeferredCurrentRepayAmount()+" "+moneyUnit);
        daysOfExtensionTv.setText(RepayInfo_flower.getInstance().getDeferredDays()+" "+getString(R.string.word_days));
        dueDateAfterExtension1Tv.setText(RepayInfo_flower.getInstance().getDeferredCurrentRepayTime());
        dueDateAfterExtension2Tv.setText(RepayInfo_flower.getInstance().getDeferredRepayTime());
        totalRepaymentDueTv.setText(RepayInfo_flower.getInstance().getDeferredRepayAmount()+" "+moneyUnit);
    }

    @SuppressLint("SetTextI18n")
    private void setDeferredFlag(boolean deferredFlag) {

        this.deferredFlag = deferredFlag;
        if (deferredFlag){
            planAIv.setBackgroundResource(R.drawable.icon_item_check_false);
            planBIv.setBackgroundResource(R.drawable.icon_item_check_true);
            paymentAmountTv.setText(RepayInfo_flower.getInstance().getDeferredCurrentRepayAmount()+" "+StringUtil_flower.getSafeString(StaticConfig_flower.getMoneyUnit()).toUpperCase());
        }else {
            planAIv.setBackgroundResource(R.drawable.icon_item_check_true);
            planBIv.setBackgroundResource(R.drawable.icon_item_check_false);
            paymentAmountTv.setText(RepayInfo_flower.getInstance().getRepayAmount()+" "+StringUtil_flower.getSafeString(StaticConfig_flower.getMoneyUnit()).toUpperCase());
        }
        HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag()+"_"+(deferredFlag ? "ROLLOVER":"FULL")+"_CLICK").toUpperCase());
    }

    public void onFetchBankListResponse_Niri(){
        Context context = requireContext();
        if (context instanceof BaseActivity_flower) ((BaseActivity_flower)context).startActivity_flower(RepayPaystackActivity_Niri_flower.class);
    }

    @Override
    public String getPagetag() {
        return "REPAY";
    }
}