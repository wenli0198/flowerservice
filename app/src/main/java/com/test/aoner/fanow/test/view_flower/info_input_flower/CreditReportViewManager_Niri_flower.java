package com.test.aoner.fanow.test.view_flower.info_input_flower;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessData_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.base_flower.BaseInfoInputViewManager_flower;

public class CreditReportViewManager_Niri_flower extends BaseInfoInputViewManager_flower {

    private View clickView;
    private TextView titleTv;
    private ImageView checkIb;
    private TextView textTv;

    private boolean isCheck = false;

    public CreditReportViewManager_Niri_flower(View view, ProcessData_flower data,String pageTag){
        super(view,data,pageTag);
    }

    @Override
    protected void init(){

        clickView = mView.findViewById(R.id.view_credit_report_layout_click);
        titleTv = mView.findViewById(R.id.view_credit_report_tv_title);
        checkIb = mView.findViewById(R.id.view_credit_report_iv_check);
        textTv = mView.findViewById(R.id.view_credit_report_tv_text);

        titleTv.setText(mData.getTitle());
        textTv.setText(StringUtil_flower.getSafeString(mData.getHint()));

        clickView.setOnClickListener(v -> {
            checkIb.setBackgroundResource(isCheck ? R.drawable.icon_item_check_false:R.drawable.icon_item_check_true);
            isCheck = !isCheck;
            HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+mData.getParamName()+"_CLICK").toUpperCase(),getInput());
        });
    }


    @Override
    public boolean checkInput() {
        if (!mData.isMustInput()) return true;
        return isCheck;
    }

    @Override
    public String getInput() {
        return isCheck ? "YES":"NO";
    }

}
