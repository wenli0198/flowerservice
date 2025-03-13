package com.test.aoner.fanow.test.view_flower.module_flower;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessInfo_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;

import java.util.ArrayList;

public class ProcessView_flower extends RelativeLayout {

    public ProcessView_flower(Context context) {
        super(context);
        initView(context);
    }

    public ProcessView_flower(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ProcessView_flower(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){

        View view = View.inflate(context, R.layout.view_process_flower,this);

        LinearLayout groupLayout = view.findViewById(R.id.view_process_layout_group);

        ArrayList<String> allSteps = ProcessInfo_flower.getInstance().getAllSteps();
        ArrayList<String>  allStepsTitle = ProcessInfo_flower.getInstance().getAllStepTitles();
        ArrayList<String> steps = ProcessInfo_flower.getInstance().getSteps();

        if (steps==null||steps.isEmpty()) return;

        boolean nowProcessFlag = true;
        for (int i=0;i<allSteps.size();i++){
            View unitView = View.inflate(context,R.layout.view_process_unit_flower,null);
            TextView nameTv = unitView.findViewById(R.id.view_process_unit_tv_name);
            View underLineView = unitView.findViewById(R.id.view_process_unit_view_under_line);

            if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Cote)){
                nameTv.setText(allStepsTitle.get(i));
            }else nameTv.setText(StringUtil_flower.processTitleFormat(allSteps.get(i)));

            unitView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));

            if (steps.contains(allSteps.get(i))&&nowProcessFlag){
                nameTv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                underLineView.setBackgroundColor(getContext().getColor(R.color.black));
                nowProcessFlag=false;
            }

            groupLayout.addView(unitView);
        }

    }

}
