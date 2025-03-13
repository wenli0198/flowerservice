package com.test.aoner.fanow.test.view_flower.widget_flower;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

public class InfoInputEditText extends AppCompatEditText implements TextWatcher, View.OnFocusChangeListener {

    private long focusTime = 0;
    private long blurTime = 0;

    private boolean hasPaste = false;
    private boolean hasEdit = false;

    private boolean pasting = false;

    private String bpType = "";

    public InfoInputEditText(@NonNull Context context) {
        super(context);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public InfoInputEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public InfoInputEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste) {
            pasting = true;
            hasPaste = true;
        }
        Log.w("===", "onTextContextMenuItem: isPaste="+hasEdit);
        return super.onTextContextMenuItem(id);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override
    public void afterTextChanged(Editable s) {
        Log.w("===", "afterTextChanged: isPaste=");
        if (pasting) pasting = false;
        else hasEdit = true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (hasFocus) focusTime = System.currentTimeMillis();
        else {
            blurTime = System.currentTimeMillis();
            long time = blurTime - focusTime;
            Editable input = getText();
            String content = input == null ? "":input.toString();
            String remark = content+"$$"+time+"$$"+(hasPaste ? "P":"")+(hasEdit ? "E":"");
            HttpManager_flower.getInstance().saveUserBuriedPoint(bpType,remark);


        }

    }

    public void setBpType(String bpType) {
        this.bpType = bpType;
    }
}
