package com.test.aoner.fanow.test.view_flower.info_input_flower;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.adapter_flower.InfoInputListAdapter_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessData_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.base_flower.BaseInfoInputViewManager_flower;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectViewManager_flower extends BaseInfoInputViewManager_flower {

    private TextView titleTv;
    private ViewGroup clickLayout;
    private TextView inputTv;
    private ViewGroup listLayout;
    private ListView listLv;

    private int itemHeight = 0;
    private ArrayList<String> items;
    private InfoInputListAdapter_flower listAdapter;

    private long time1=0;
    private long time2=0;

    public SelectViewManager_flower(View view, ProcessData_flower data,String pageTag){
        super(view,data,pageTag);
    }

    @Override
    protected void init(){

        titleTv = mView.findViewById(R.id.view_select_tv_title);
        clickLayout = mView.findViewById(R.id.view_select_layout_click);
        inputTv = mView.findViewById(R.id.view_select_tv_input);
        listLayout = mView.findViewById(R.id.view_select_layout_list);
        listLv = mView.findViewById(R.id.view_select_lv_list);

        setTitle(mData.getTitle());
        inputTv.setHint(StringUtil_flower.getSafeString(mData.getHint()));

        clickLayout.setOnClickListener(v -> {
            if (listLayout.getVisibility() == VISIBLE) listLayout.setVisibility(GONE);
            else {
                time1 = System.currentTimeMillis();
                listLayout.setVisibility(VISIBLE);
            }
        });

        addItems(mData.getTitlesOfValue());
    }

    @Override
    public boolean checkInput() {
        if (!mData.isMustInput()) return true;
        return !TextUtils.isEmpty(inputTv.getText());
    }

    @Override
    public String getInput() {
        if (mData == null) return "";

        return mData.getValueOfTitle(StringUtil_flower.getSafeString(inputTv.getText().toString()));
    }

    public String getTextInput(){
        return StringUtil_flower.getSafeString(inputTv.getText().toString());
    }

    public void setTitle(String title){
        if (TextUtils.isEmpty(title)) titleTv.setVisibility(GONE);
        else {
            titleTv.setVisibility(VISIBLE);
            titleTv.setText(title);
        }
    }

    public void addItems(ArrayList<String> itemList){
        items = itemList;
        listAdapter = new InfoInputListAdapter_flower(mView.getContext(), items);
        listLv.setAdapter(listAdapter);
        listLv.setOnItemClickListener((parent, view, position, id) -> {

            if (items ==null) return;
            String input = items.get(position);

            time2 = System.currentTimeMillis();
            HttpManager_flower.getInstance().saveUserBuriedPoint((pageTag+"_"+mData.getParamName()+"_CLICK").toUpperCase(),input+"$$"+(time2-time1));

            inputTv.setText(input);
            listLayout.setVisibility(GONE);
        });

        listAdapter.notifyDataSetChanged();

        ViewGroup.LayoutParams layoutParams = listLv.getLayoutParams();
        if (itemHeight ==0) itemHeight = layoutParams.height;
        layoutParams.height = items.size()* itemHeight;
        listLv.setLayoutParams(layoutParams);

    }

    public void addItems(String[] itemArr){
        addItems(new ArrayList<>(Arrays.asList(itemArr)));
    }

    public String getParamName(){
        if (mData == null) return "";
        return mData.getParamName();
    }

}
