package com.test.aoner.fanow.test.adapter_flower;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.aoner.fanow.test.adapter_flower.base_flower.BaseListAdapter_flower;
import com.test.aoner.fanow.test.R;

import java.util.List;


public class InfoInputListAdapter_flower extends BaseListAdapter_flower {

    private Context context;

    private final List<String> datas;

    public InfoInputListAdapter_flower(Context context, List<String> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<String> getDatas() {
        return datas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) convertView = LayoutInflater.from(context).inflate(R.layout.item_info_input_list_flower,parent,false);

        TextView textView = convertView.findViewById(R.id.item_info_input_list_tv_text);
        textView.setText(datas.get(position));

        return convertView;
    }
}
