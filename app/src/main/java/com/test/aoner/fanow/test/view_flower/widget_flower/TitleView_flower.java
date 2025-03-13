package com.test.aoner.fanow.test.view_flower.widget_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.dialog_flower.CustomerServiceDialog_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;

public class TitleView_flower extends LinearLayout {

    private ImageButton backIb, customerServiceIb;
    private TextView titleTv;

    private String title;
    private boolean showBackBtn, showCustomerServiceBtn;
    private boolean isWhiteStyle;

    public TitleView_flower(Context context) {
        super(context);
        initView(context);
    }

    public TitleView_flower(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    public TitleView_flower(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs){
        @SuppressLint({"Recycle", "CustomViewStyleable"}) TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TitleView);
        title = typedArray.getString(R.styleable.TitleView_title);
        showBackBtn = typedArray.getBoolean(R.styleable.TitleView_showBackBtn,true);
        showCustomerServiceBtn = typedArray.getBoolean(R.styleable.TitleView_showCustomerServiceBtn,true);
        isWhiteStyle = typedArray.getBoolean(R.styleable.TitleView_isWhiteStyle,false);
        initView(context);
    }

    private void initView(Context context){
        View view = View.inflate(context,R.layout.view_title_flower,this);
        backIb = view.findViewById(R.id.view_title_ib_back);
        titleTv = view.findViewById(R.id.view_title_tv_title);
        customerServiceIb = view.findViewById(R.id.view_title_ib_customer_service);

        if (!showBackBtn) backIb.setVisibility(GONE);
        if (!showCustomerServiceBtn) customerServiceIb.setVisibility(GONE);

        titleTv.setText(StringUtil_flower.getSafeString(title));

        customerServiceIb.setOnClickListener(v -> new CustomerServiceDialog_flower(context).show());

        if (isWhiteStyle){
            backIb.setBackgroundResource(R.drawable.icon_back_white);
            customerServiceIb.setBackgroundResource(R.drawable.icon_customer_service_white);
            titleTv.setTextColor(ContextCompat.getColor(context,R.color.white));
        }
    }

    public void setTitle(String title) {
        this.title = title;
        titleTv.setText(StringUtil_flower.getSafeString(title));
    }

    public String getTitle() {
        return StringUtil_flower.getSafeString(title);
    }

    public ImageButton getBackIb() {
        return backIb;
    }
}
