package com.test.aoner.fanow.test.view_flower.info_input_flower;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.adapter_flower.InfoInputListAdapter_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessData_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.base_flower.BaseInfoInputViewManager_flower;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactSelectViewManager_flower extends BaseInfoInputViewManager_flower {

    private TextView titleTv;
    private TextView inputTv;

    private final View.OnClickListener onClickListener;

    public ContactSelectViewManager_flower(View view, ProcessData_flower data, String pageTag, View.OnClickListener onClickListener){
        super(view,data,pageTag);
        this.onClickListener = onClickListener;
    }

    @Override
    protected void init(){

        titleTv = mView.findViewById(R.id.view_select_tv_title);
        ViewGroup clickLayout = mView.findViewById(R.id.view_select_layout_click);
        inputTv = mView.findViewById(R.id.view_select_tv_input);

        ImageView logoIv = mView.findViewById(R.id.view_select_iv_logo);
        logoIv.setBackgroundResource(R.drawable.icon_contact);

        setTitle(mData.getTitle());
        inputTv.setHint(StringUtil_flower.getSafeString(mData.getHint()));

        clickLayout.setOnClickListener(v -> {
            if (onClickListener!=null) onClickListener.onClick(v);
        });

    }

    @Override
    public boolean checkInput() {
        if (!mData.isMustInput()) return true;
        return !TextUtils.isEmpty(inputTv.getText());
    }

    @Override
    public String getInput() {
        return StringUtil_flower.getSafeString(inputTv.getText().toString());
    }

    public void setTitle(String title){
        if (TextUtils.isEmpty(title)) titleTv.setVisibility(GONE);
        else {
            titleTv.setVisibility(VISIBLE);
            titleTv.setText(title);
        }
    }

    public void setText(String text){
        inputTv.setText(StringUtil_flower.getSafeString(text));
    }

    public String getParamName(){
        if (mData == null) return "";
        return mData.getParamName();
    }

}
