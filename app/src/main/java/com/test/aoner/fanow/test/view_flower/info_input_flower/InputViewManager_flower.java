package com.test.aoner.fanow.test.view_flower.info_input_flower;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.test.aoner.fanow.test.constant_flower.Constant_flower.Data_Action_Contact_Phone;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessData_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessInfo_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.view_flower.base_flower.BaseInfoInputViewManager_flower;
import com.test.aoner.fanow.test.view_flower.base_flower.OnInputChangeListener_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.InfoInputEditText;

public class InputViewManager_flower extends BaseInfoInputViewManager_flower {

    private TextView titleTv;
    private InfoInputEditText inputEt;

    private OnInputChangeListener_flower onInputChangeListener;

    public InputViewManager_flower(View view, ProcessData_flower data,String pageTag){
        super(view,data,pageTag);
    }

    @Override
    protected void init(){

        titleTv = mView.findViewById(R.id.view_input_tv_title);
        inputEt = mView.findViewById(R.id.view_input_et_input);

        setTitle(mData.getTitle());
        inputEt.setHint(StringUtil_flower.getSafeString(mData.getHint()));

        if (mData.getAction().equalsIgnoreCase(Data_Action_Contact_Phone)) inputEt.setInputType(InputType.TYPE_CLASS_NUMBER);

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Ghana)&& mData.getParamName().equalsIgnoreCase("walletMobile")){
            inputEt.setText(UserInfoHelper_flower.getInstance().getMobile());
        }
        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Ghana)&& mData.getParamName().equalsIgnoreCase("walletUsername")){
            inputEt.setText(ProcessInfo_flower.getInstance().getRecipientName());
        }

        inputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (onInputChangeListener !=null) onInputChangeListener.onInputChange(s.toString());
            }
        });

        inputEt.setBpType((pageTag+"_"+mData.getParamName()+"_INPUT").toUpperCase());

    }


    @Override
    public boolean checkInput() {
        if (!mData.isMustInput()) return true;
        return !TextUtils.isEmpty(inputEt.getText());
    }

    @Override
    public String getInput() {
        return StringUtil_flower.getSafeString(inputEt.getText().toString());
    }

    public void addTextChangeListener(TextWatcher textWatcher){
        inputEt.addTextChangedListener(textWatcher);
    }

    public void setTitle(String title){
        if (TextUtils.isEmpty(title)) titleTv.setVisibility(GONE);
        else {
            titleTv.setVisibility(VISIBLE);
            titleTv.setText(title);
        }
    }

    public void setOnInputChangeListener(OnInputChangeListener_flower onInputChangeListener) {
        this.onInputChangeListener = onInputChangeListener;
    }
}
