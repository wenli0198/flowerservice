package com.test.aoner.fanow.test.view_flower.info_input_flower;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessData_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.base_flower.BaseInfoInputViewManager_flower;
import com.test.aoner.fanow.test.view_flower.base_flower.OnInputChangeListener_flower;


public class DatePickViewManager_flower extends BaseInfoInputViewManager_flower {

    private TextView titleTv;
    private ViewGroup clickLayout;
    private TextView inputTv;

    private OnInputChangeListener_flower onInputChangeListener;

    public DatePickViewManager_flower(View view, ProcessData_flower data,String pageTag){
        super(view,data,pageTag);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void init(){

        titleTv = mView.findViewById(R.id.view_date_picker_tv_title);
        clickLayout = mView.findViewById(R.id.view_date_picker_layout_click);
        inputTv = mView.findViewById(R.id.view_date_picker_tv_input);

        setTitle(StringUtil_flower.getSafeString(mData.getTitle()));
        inputTv.setHint(StringUtil_flower.getSafeString(mData.getHint()));

        clickLayout.setOnClickListener(v -> {

            final long time1 = System.currentTimeMillis();

            AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
            View dialogView = ((Activity) mView.getContext()).getLayoutInflater().inflate(R.layout.dialog_date_picker_flower,null);
            DatePicker datePicker = dialogView.findViewById(R.id.dialog_date_picker_view);
            datePicker.setCalendarViewShown(false);
            builder.setView(dialogView).setTitle(mView.getResources().getString(R.string.dialog_date_picker_title));
            builder.setPositiveButton(mView.getResources().getString(R.string.word_submit),(dialog, which) -> {
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();
                String monthStr = month<10 ? "0"+month:String.valueOf(month);
                String dayStr = day<10 ? "0"+day:String.valueOf(day);
                //String input = dayStr+"/"+monthStr+"/"+datePicker.getYear();
                String input = datePicker.getYear()+"-"+monthStr+"-"+dayStr;
                final long time2 = System.currentTimeMillis();
                HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+mData.getParamName()+"_CLICK").toUpperCase(),input+"$$"+(time2-time1));
                inputTv.setText(input);
                if (onInputChangeListener !=null) onInputChangeListener.onInputChange(input);
                dialog.cancel();
            });
            builder.setNegativeButton(mView.getResources().getString(R.string.word_cancel), ((dialog, which) -> dialog.cancel()));
            builder.show();
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
        if (TextUtils.isEmpty(title)) titleTv.setVisibility(View.GONE);
        else {
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(title);
        }
    }

    public void setOnInputChangeListener(OnInputChangeListener_flower onInputChangeListener) {
        this.onInputChangeListener = onInputChangeListener;
    }
}
