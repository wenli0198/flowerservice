package com.test.aoner.fanow.test.view_flower.info_input_flower;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessData_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.base_flower.BaseInfoInputViewManager_flower;

public class GenderSelectViewManager_flower extends BaseInfoInputViewManager_flower {

    private TextView titleTv;
    private LinearLayout maleLayout,femaleLayout;
    private TextView maleTv, femaleTv;
    private ImageView maleIv, femaleIv;

    private boolean isMale = true;

    public GenderSelectViewManager_flower(View view, ProcessData_flower data,String pageTag){
        super(view,data,pageTag);
    }

    @Override
    protected void init(){

        titleTv = mView.findViewById(R.id.view_gender_select_tv_title);
        maleLayout = mView.findViewById(R.id.view_gender_select_layout_male);
        femaleLayout = mView.findViewById(R.id.view_gender_select_layout_female);
        maleTv = mView.findViewById(R.id.view_gender_select_tv_g1);
        femaleTv = mView.findViewById(R.id.view_gender_select_tv_female);
        maleIv = mView.findViewById(R.id.view_gender_select_iv_male);
        femaleIv = mView.findViewById(R.id.view_gender_select_iv_female);

        maleLayout.setOnClickListener(v -> setGender(true));
        femaleLayout.setOnClickListener(v -> setGender(false));

        setTitle(mData.getTitle());

    }

    @Override
    public boolean checkInput() {
        return true;
    }

    @Override
    public String getInput() {
        return isMale ?
                mView.getResources().getString(R.string.view_gender_select_male):
                mView.getResources().getString(R.string.view_gender_select_female);
    }

    public void setGender(boolean isMale){
        this.isMale = isMale;
        if (isMale){
            maleTv.setTextColor(Color.BLACK);
            femaleTv.setTextColor(ContextCompat.getColor(mView.getContext(),R.color.gray_ddd));
            maleIv.setImageResource(R.drawable.icon_item_check_true);
            femaleIv.setImageResource(R.drawable.icon_item_check_false);
            maleLayout.setBackgroundResource(R.drawable.ic_bg_input);
            femaleLayout.setBackgroundResource(R.drawable.ic_border_gray_ll_1dp_round_corner_10dp);
        }else {
            femaleTv.setTextColor(Color.BLACK);
            maleTv.setTextColor(ContextCompat.getColor(mView.getContext(),R.color.gray_ddd));
            maleIv.setImageResource(R.drawable.icon_item_check_false);
            femaleIv.setImageResource(R.drawable.icon_item_check_true);
            maleLayout.setBackgroundResource(R.drawable.ic_border_gray_ll_1dp_round_corner_10dp);
            femaleLayout.setBackgroundResource(R.drawable.ic_bg_input);
        }
        HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+mData.getParamName()+(isMale ? "_MALE":"_FEMALE")+"_CLICK").toUpperCase());
    }

    public void setTitle(String title){
        if (TextUtils.isEmpty(title)) titleTv.setVisibility(GONE);
        else {
            titleTv.setVisibility(VISIBLE);
            titleTv.setText(title);
        }
    }

}
