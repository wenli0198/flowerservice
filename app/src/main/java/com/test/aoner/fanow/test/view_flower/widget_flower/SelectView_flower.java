package com.test.aoner.fanow.test.view_flower.widget_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.adapter_flower.InfoInputListAdapter_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectView_flower extends LinearLayout {

    private Context mContext;

    private String title;
    private String hint;
    private Drawable icon;

    private TextView titleTv;
    private ViewGroup clickLayout;
    private TextView inputTv;
    private ViewGroup listLayout;
    private ListView listLv;

    private OnPickListener onPickListener;
    private InterceptPickListener interceptPickListener;

    private int itemHeight = 0;
    private ArrayList<String> items;
    private InfoInputListAdapter_flower listAdapter;

    private final AdapterView.OnItemClickListener onItemClickListener = (parent, view, position, id) -> {
        if (items == null) return;
        String pickStr = items.get(position);

        listLayout.setVisibility(GONE);

        if (interceptPickListener!=null){
            interceptPickListener.onPick(pickStr);
            return;
        }

        inputTv.setText(pickStr);

        if (onPickListener != null) onPickListener.onPick(pickStr);
    };

    public SelectView_flower(Context context) {
        super(context);
        init(context,null);
    }

    public SelectView_flower(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public SelectView_flower(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){

        mContext = context;

        if (attrs!=null){
            @SuppressLint("Recycle") TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.SelectView);
            title = typedArray.getString(R.styleable.SelectView_title);
            hint = typedArray.getString(R.styleable.SelectView_android_hint);
            icon = typedArray.getDrawable(R.styleable.SelectView_icon);
        }

        View view = View.inflate(context, R.layout.view_select_flower,this);
        titleTv = view.findViewById(R.id.view_select_tv_title);
        clickLayout = view.findViewById(R.id.view_select_layout_click);
        inputTv = view.findViewById(R.id.view_select_tv_input);
        listLayout = view.findViewById(R.id.view_select_layout_list);
        listLv = view.findViewById(R.id.view_select_lv_list);
        View iconView = view.findViewById(R.id.view_select_layout_icon);
        ImageView iconIv = view.findViewById(R.id.view_select_iv_icon);

        if (TextUtils.isEmpty(title)) titleTv.setVisibility(GONE);
        else titleTv.setText(title);

        inputTv.setHint(StringUtil_flower.getSafeString(hint));

        clickLayout.setOnClickListener(v -> listLayout.setVisibility(listLayout.getVisibility()==VISIBLE ? GONE:VISIBLE));

        if (icon !=null){
            iconView.setVisibility(VISIBLE);
            iconIv.setImageDrawable(icon);
        }

    }

    public boolean isInputEmpty(){
        return TextUtils.isEmpty(inputTv.getText());
    }

    public String getInput(){
        return StringUtil_flower.getSafeString(inputTv.getText().toString());
    }

    public void addListItem(String... listItems){
        addListItem(new ArrayList<>(Arrays.asList(listItems)));
    }

    public void addListItem(ArrayList<String> listItems){
        this.items = listItems;
        listAdapter = new InfoInputListAdapter_flower(mContext, items);
        listLv.setAdapter(listAdapter);
        listLv.setOnItemClickListener(onItemClickListener);

        ViewGroup.LayoutParams layoutParams = listLv.getLayoutParams();
        if (itemHeight == 0) itemHeight = layoutParams.height;
        layoutParams.height = items.size()* itemHeight;
        listLv.setLayoutParams(layoutParams);
    }

    public void setInput(String text){
        inputTv.setText(StringUtil_flower.getSafeString(text));
    }

    public void setOnPickListener(OnPickListener onPickListener) {
        this.onPickListener = onPickListener;
    }

    public void setInterceptPickListener(InterceptPickListener interceptPickListener) {
        this.interceptPickListener = interceptPickListener;
    }

    public interface OnPickListener {
        void onPick(String pickStr);
    }

    public interface InterceptPickListener{
        void onPick(String pickStr);
    }

}
