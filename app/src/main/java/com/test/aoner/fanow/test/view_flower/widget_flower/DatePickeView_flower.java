package com.test.aoner.fanow.test.view_flower.widget_flower;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;

public class DatePickeView_flower extends LinearLayout {

    private String title;
    private String hint;

    private TextView titleTv;
    private TextView inputTv;
    private View clickView;

    private String year, month, day;

    public DatePickeView_flower(Context context) {
        super(context);
        init(context,null);
    }

    public DatePickeView_flower(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public DatePickeView_flower(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){

        if (attrs!=null){
            @SuppressLint({"Recycle", "CustomViewStyleable"}) TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.DatePickeView);
            title = typedArray.getString(R.styleable.DatePickeView_title);
            hint = typedArray.getString(R.styleable.DatePickeView_android_hint);
        }

        View view = View.inflate(context, R.layout.view_date_picker_flower,this);
        titleTv = view.findViewById(R.id.view_date_picker_tv_title);
        inputTv = view.findViewById(R.id.view_date_picker_tv_input);
        clickView = view.findViewById(R.id.view_date_picker_layout_click);

        if (TextUtils.isEmpty(title)) titleTv.setVisibility(GONE);
        else titleTv.setText(title);

        inputTv.setHint(StringUtil_flower.getSafeString(hint));

        clickView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialogView = ((Activity)context).getLayoutInflater().inflate(R.layout.dialog_date_picker_flower,null);
            DatePicker datePicker = dialogView.findViewById(R.id.dialog_date_picker_view);
            datePicker.setCalendarViewShown(false);
            builder.setView(dialogView).setTitle(context.getResources().getString(R.string.dialog_date_picker_title));
            builder.setPositiveButton(context.getResources().getString(R.string.word_submit),(dialog,which) -> {
                int month = datePicker.getMonth() + 1;
                this.month = String.valueOf(month);
                int day = datePicker.getDayOfMonth();
                this.day = String.valueOf(day);
                int year = datePicker.getYear();
                this.year = String.valueOf(year);
                String monthStr = month<10 ? "0"+month:String.valueOf(month);
                String dayStr = day<10 ? "0"+day:String.valueOf(day);
                String input = dayStr+"/"+monthStr+"/"+year;
                inputTv.setText(input);
                dialog.cancel();
                
            });
            builder.setNegativeButton(context.getResources().getString(R.string.word_cancel), ((dialog, which) -> dialog.cancel()));
            builder.show();
        });

    }

    public boolean isInputEmpty(){
        return TextUtils.isEmpty(inputTv.getText());
    }

    public void setInput(String text){
        inputTv.setText(StringUtil_flower.getSafeString(text));
    }

    public String getInput(){
        return StringUtil_flower.getSafeString(inputTv.getText().toString());
    }

    public String getYear() {
        return StringUtil_flower.getSafeString(year);
    }

    public String getMonth() {
        return StringUtil_flower.getSafeString(month);
    }

    public String getDay() {
        return StringUtil_flower.getSafeString(day);
    }
}
