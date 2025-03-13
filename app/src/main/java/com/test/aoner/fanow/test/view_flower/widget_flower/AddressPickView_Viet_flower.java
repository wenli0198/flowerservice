package com.test.aoner.fanow.test.view_flower.widget_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.dialog_flower.AddressSelectDialog_Niri_flower;
import com.test.aoner.fanow.test.dialog_flower.AddressSelectDialog_Viet_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;


public class AddressPickView_Viet_flower extends LinearLayout {

    private String title,hint;
    private boolean isMustInput = true;

    private TextView titleTv;
    private TextView inputTv;
    private View clickView;

    private AddressSelectDialog_Niri_flower addressSelectDialog;

    private String state, city;

    public AddressPickView_Viet_flower(Context context) {
        super(context);
        init(context,null);
    }

    public AddressPickView_Viet_flower(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public AddressPickView_Viet_flower(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    protected void init(Context context,AttributeSet attrs){

        if (attrs!=null){
            @SuppressLint("Recycle") TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.AddressPickView_Viet);
            title = typedArray.getString(R.styleable.AddressPickView_Viet_title);
            hint = typedArray.getString(R.styleable.AddressPickView_Viet_android_hint);
        }

        View view = View.inflate(context, R.layout.view_address_pick_flower,this);
        titleTv = view.findViewById(R.id.view_address_pick_tv_title);
        inputTv = view.findViewById(R.id.view_address_pick_tv_input);
        clickView = view.findViewById(R.id.view_address_pick_layout_click);

        setTitle(title);
        inputTv.setHint(StringUtil_flower.getSafeString(hint));

        clickView.setOnClickListener(v -> new AddressSelectDialog_Viet_flower(context, inputTv, addressStrs -> {
            if (addressStrs.length<2) return;
            state = addressStrs[0];
            city = addressStrs[1];
        }).show());

    }

    public boolean isInputEmpty() {
        return TextUtils.isEmpty(inputTv.getText());
    }

    public String getInput() {
        return StringUtil_flower.getSafeString(inputTv.getText().toString());
    }

    public void setInput(String text){
        inputTv.setText(StringUtil_flower.getSafeString(text));
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

    public void setMustInput(boolean mustInput) {
        isMustInput = mustInput;
    }
}
