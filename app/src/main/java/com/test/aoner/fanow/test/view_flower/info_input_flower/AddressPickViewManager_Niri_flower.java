package com.test.aoner.fanow.test.view_flower.info_input_flower;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessData_flower;
import com.test.aoner.fanow.test.dialog_flower.AddressSelectDialog_Niri_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.base_flower.BaseInfoInputViewManager_flower;


public class AddressPickViewManager_Niri_flower extends BaseInfoInputViewManager_flower {

    private TextView titleTv;
    private TextView inputTv;
    private View clickView;

    private AddressSelectDialog_Niri_flower addressSelectDialog;

    private String state, city, area;

    public AddressPickViewManager_Niri_flower(View view, ProcessData_flower data,String pageTag){
        super(view,data,pageTag);
    }

    @Override
    protected void init(){

        titleTv = mView.findViewById(R.id.view_address_pick_tv_title);
        inputTv = mView.findViewById(R.id.view_address_pick_tv_input);
        clickView = mView.findViewById(R.id.view_address_pick_layout_click);

        setTitle(mData.getTitle());
        inputTv.setHint(mData.getHint());

        clickView.setOnClickListener(v -> {
            final long time1 = System.currentTimeMillis();
            new AddressSelectDialog_Niri_flower(mView.getContext(), inputTv, addressStrs -> {
                if (addressStrs.length<3) return;
                state = addressStrs[0];
                city = addressStrs[1];
                area = addressStrs[2];
                final long time2 = System.currentTimeMillis();
                HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+mData.getParamName()+"_CLICK").toUpperCase(),
                        addressStrs[0]+"-"+addressStrs[1]+"-"+addressStrs[2]+"$$"+(time2-time1));
            }).show();
        });

    }


    @Override
    public boolean checkInput() {
        if (!mData.isMustInput()) return true;
        return (!TextUtils.isEmpty(state))&&(!TextUtils.isEmpty(city))&&(!TextUtils.isEmpty(area));
    }

    @Override
    public String getInput() {
        return "";
    }

    public void setTitle(String title){
        if (TextUtils.isEmpty(title)) titleTv.setVisibility(GONE);
        else {
            titleTv.setVisibility(VISIBLE);
            titleTv.setText(title);
        }
    }

    public String getState() {
        return StringUtil_flower.getSafeString(state);
    }

    public String getCity() {
        return StringUtil_flower.getSafeString(city);
    }

    public String getArea() {
        return StringUtil_flower.getSafeString(area);
    }
}
