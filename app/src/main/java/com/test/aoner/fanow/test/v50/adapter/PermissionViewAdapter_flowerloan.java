package com.test.aoner.fanow.test.v50.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.v40.bean.PermissionInfo_flower;
import com.test.aoner.fanow.test.v40.listener.PermissionListener;

import java.util.ArrayList;


public class PermissionViewAdapter_flowerloan extends RecyclerView.Adapter<PermissionViewAdapter_flowerloan.PermissionDialogHolder_flower> {

    private final Context context;

    private final ArrayList<PermissionInfo_flower> infos;

    public PermissionViewAdapter_flowerloan(Context context, ArrayList<PermissionInfo_flower> infos){
        this.context = context;
        this.infos = infos;
    }

    public PermissionInfo_flower getInfo(int index){
        return infos.get(index);
    }

    @NonNull
    @Override
    public PermissionDialogHolder_flower onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PermissionDialogHolder_flower(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_permission_flower,parent,false));
    }

    @SuppressLint("InflateParams")
    @Override
    public void onBindViewHolder(@NonNull PermissionDialogHolder_flower holder, int position) {
        PermissionInfo_flower info = infos.get(position);

        info.setHolder_flower(holder);

        if (info.getIconId_flower()!=null) holder.setIcon(info.getIconId_flower());
        holder.setTitle(info.getTitle_flower());
        holder.setTitle1(info.getTitle1_flower());
        holder.setTitle2(info.getTitle2_flower());
        holder.setTitle3(info.getTitle3_flower());
        holder.setTitle4(info.getTitle4_flower());

        holder.setText1(info.getText1_flower());
        holder.setText2(info.getText2_flower());
        holder.setText3(info.getText3_flower());
        holder.setText4(info.getText4_flower());

        holder.setListener(info.getListener_flower());

        LinearLayout dianrongqi = holder.getPointGroup();

        for (int i=0;i<infos.size();i++){
            if (i==position) dianrongqi.addView(LayoutInflater.from(context).inflate(R.layout.view_permission_point_true,null));
            else dianrongqi.addView(LayoutInflater.from(context).inflate(R.layout.view_permission_point_false,null));
        }
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public static class PermissionDialogHolder_flower extends RecyclerView.ViewHolder{

        private final View mView;

        private final ImageView iconIv;
        private final  TextView titleTv;
        private final TextView title1Tv;
        private final TextView txt1Tv;
        private final TextView title2Tv;
        private final TextView txt2Tv;
        private final TextView title3Tv;
        private final TextView txt3Tv;
        private final TextView title4Tv;
        private final TextView txt4Tv;
        private final  ImageButton checkIb;

        private final View checkLayout;

        private PermissionListener listener;

        private boolean check = false;

        @SuppressLint("InflateParams")
        public PermissionDialogHolder_flower(View view){

            super(view);
            mView = view;

            iconIv = view.findViewById(R.id.item_permission_iv_img);
            titleTv = mView.findViewById(R.id.item_permission_tv_title);
            title1Tv = mView.findViewById(R.id.item_permission_tv_title_1);
            txt1Tv = mView.findViewById(R.id.item_permission_tv_text_1);
            title2Tv = mView.findViewById(R.id.item_permission_tv_title_2);
            txt2Tv = mView.findViewById(R.id.item_permission_tv_text_2);
            title3Tv = mView.findViewById(R.id.item_permission_tv_title_3);
            txt3Tv = mView.findViewById(R.id.item_permission_tv_text_3);
            title4Tv = mView.findViewById(R.id.item_permission_tv_title_4);
            txt4Tv = mView.findViewById(R.id.item_permission_tv_text_4);
            checkIb = mView.findViewById(R.id.itpe_ib_check);

            checkLayout = mView.findViewById(R.id.itpe_layout_check);

            checkIb.setOnClickListener(v -> setCheck(!check));

        }

        public void setIcon(int iconresid){
            iconIv.setImageResource(iconresid);
        }

        public void setTitle(String title) {
            if (TextUtils.isEmpty(title)) titleTv.setVisibility(View.GONE);
            else titleTv.setText(title);
        }

        public void setTitle1(String title) {
            if (TextUtils.isEmpty(title)) title1Tv.setVisibility(View.GONE);
            else title1Tv.setText(title);
        }

        public void setTitle2(String title) {
            if (TextUtils.isEmpty(title)) title2Tv.setVisibility(View.GONE);
            else title2Tv.setText(title);
        }

        public void setTitle3(String title) {
            if (TextUtils.isEmpty(title)) title3Tv.setVisibility(View.GONE);
            else title3Tv.setText(title);
        }

        public void setTitle4(String title) {
            if (TextUtils.isEmpty(title)) title4Tv.setVisibility(View.GONE);
            else title4Tv.setText(title);
        }

        public void setText1(String text) {
            if (TextUtils.isEmpty(text)) txt1Tv.setVisibility(View.GONE);
            else txt1Tv.setText(text);
        }

        public void setText2(String text) {
            if (TextUtils.isEmpty(text))txt2Tv.setVisibility(View.GONE);
            else txt2Tv.setText(text);
        }

        public void setText3(String text) {
            if (TextUtils.isEmpty(text)) txt3Tv.setVisibility(View.GONE);
            else txt3Tv.setText(text);
        }

        public void setText4(String text) {
            if (TextUtils.isEmpty(text)) txt4Tv.setVisibility(View.GONE);
            else txt4Tv.setText(text);
        }

        public void setCheck(boolean check) {
            this.check = check;
            checkIb.setBackgroundResource(check ? R.drawable.icon_item_check_true:R.drawable.icon_item_check_false);
            HttpManager_flower.getInstance().saveUserBuriedPoint(check ? "DIALOG_PERMISSION_CHECKBOX_YES":"DIALOG_PERMISSION_CHECKBOX_NO");
        }

        public boolean getCheck() {
            return check;
        }

        public void setListener(PermissionListener listener) {
            this.listener = listener;

            mView.findViewById(R.id.item_permission_btn_deny).setOnClickListener(v -> {
                if (listener!=null) listener.onFlowerPermissionDeny();
            });

            mView.findViewById(R.id.item_permission_btn_agree).setOnClickListener(v -> {
                if (listener!=null) listener.onFlowerPermissionAgree();
            });

        }

        public LinearLayout getPointGroup(){
            return mView.findViewById(R.id.itpe_layout_diangroup);
        }

        public void agree(){
            mView.findViewById(R.id.item_permission_view_ok).setVisibility(View.VISIBLE);
            mView.findViewById(R.id.item_permission_view_btn).setVisibility(View.GONE);
            checkLayout.setVisibility(View.GONE);
        }

    }
}
