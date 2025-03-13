package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.PagetagInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.adapter_flower.CustomerServiceListAdapter_flower;

public class CustomerServiceDialog_flower extends BottomSheetDialog implements PagetagInterface {

    public CustomerServiceDialog_flower(@NonNull Context context) {
        super(context,R.style.BottomSheetDialogStyle);
    }

    private int listHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_customer_service_flower);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView(){

        getWindow().setBackgroundDrawableResource(R.color.trans);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        findViewById(R.id.dialog_customer_service_ib_back).setOnClickListener(v -> dismiss());

        ListView listView = findViewById(R.id.dialog_customer_service_lv_list);
        CustomerServiceListAdapter_flower customerServiceListAdapter = new CustomerServiceListAdapter_flower(getContext());
        listView.setAdapter(customerServiceListAdapter);

        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        if (listHeight ==0) listHeight = layoutParams.height;
        layoutParams.height = customerServiceListAdapter.getCount()* listHeight;
        listView.setLayoutParams(layoutParams);

    }

    @Override
    public String getPagetag() {
        return "DIALOG_CUSTOMER_SERVICE";
    }
}
