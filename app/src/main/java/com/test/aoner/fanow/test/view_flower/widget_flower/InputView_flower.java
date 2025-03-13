package com.test.aoner.fanow.test.view_flower.widget_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;

public class InputView_flower extends LinearLayout {

    private String title;
    private String hint;
    private int inputType;

    private TextView titleTv;
    private EditText inputEt;

    public InputView_flower(Context context) {
        super(context);
        init(context,null);
    }

    public InputView_flower(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public InputView_flower(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){

        if (attrs!=null){
            @SuppressLint({"Recycle", "CustomViewStyleable"}) TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.InputView);
            title = typedArray.getString(R.styleable.InputView_title);
            hint = typedArray.getString(R.styleable.InputView_android_hint);
            inputType = typedArray.getInt(R.styleable.InputView_android_inputType, InputType.TYPE_CLASS_TEXT);
        }

        View view = View.inflate(context, R.layout.view_input_flower,this);
        titleTv = view.findViewById(R.id.view_input_tv_title);
        inputEt = view.findViewById(R.id.view_input_et_input);

        if (TextUtils.isEmpty(title)) titleTv.setVisibility(GONE);
        else titleTv.setText(title);

        inputEt.setHint(StringUtil_flower.getSafeString(hint));

        inputEt.setInputType(inputType);
    }

    public boolean isInputEmpty(){
        return TextUtils.isEmpty(inputEt.getText());
    }

    public String getInput(){
        return StringUtil_flower.getSafeString(inputEt.getText().toString());
    }

    public void setInput(String text){
        inputEt.setText(StringUtil_flower.getSafeString(text));
    }

}
