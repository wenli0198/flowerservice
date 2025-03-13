package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.adapter_flower.InfoInputListAdapter_flower;
import com.test.aoner.fanow.test.util_flower.address_flower.AddressUtil_Tanz_flower;

import java.util.Arrays;
import java.util.List;


public class AddressSelectDialog_Tanz_flower extends BottomSheetDialog {

    private Context context;

    private TextView titleTv;
    private ListView listView;

    private TextView textView;

    private StringBuilder addressStrB = new StringBuilder();

    private String[] addressStrs = new String[2];

    private final OnAddressCheckFinishListener onAddressCheckFinishListener;

    public AddressSelectDialog_Tanz_flower(@NonNull Context context, TextView textView, OnAddressCheckFinishListener onAddressCheckFinishListener) {
        super(context,R.style.BottomSheetDialogStyle);
        this.context = context;
        this.textView = textView;
        this.onAddressCheckFinishListener = onAddressCheckFinishListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_address_select_flower);
        getWindow().setBackgroundDrawableResource(R.color.trans);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void initView(){

        titleTv = findViewById(R.id.dialog_address_select_tv_title);
        listView = findViewById(R.id.dialog_address_select_lv_list);

        findViewById(R.id.dialog_address_select_ib_close).setOnClickListener(v -> dismiss());

        titleTv.setText("City");
        List<String> stateList = Arrays.asList(AddressUtil_Tanz_flower.getInstance().getStates());
        listView.setAdapter(new InfoInputListAdapter_flower(getContext(),stateList));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String state = stateList.get(position);
            addressStrB.append(state).append(" ");
            addressStrs[0] = state;

            titleTv.setText("Area");
            List<String> cityList =Arrays.asList(AddressUtil_Tanz_flower.getInstance().getCities(state));
            listView.setAdapter(new InfoInputListAdapter_flower(getContext(),cityList));

            listView.setOnItemClickListener((parent1, view1, position1, id1) -> {

                String city = cityList.get(position1);
                addressStrB.append(city);
                addressStrs[1] = city;

                textView.setText(addressStrB.toString());

                onAddressCheckFinishListener.onFinish(addressStrs);

                dismiss();

            });

        });
    }

    public interface OnAddressCheckFinishListener{
        void onFinish(String[] addressStrs);
    }
}
