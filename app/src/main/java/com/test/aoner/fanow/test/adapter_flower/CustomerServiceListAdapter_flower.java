package com.test.aoner.fanow.test.adapter_flower;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.PagetagInterface;
import com.test.aoner.fanow.test.adapter_flower.base_flower.BaseListAdapter_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.AppShowInfo_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.CustomerService_flower;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

import java.util.ArrayList;


public class CustomerServiceListAdapter_flower extends BaseListAdapter_flower implements PagetagInterface {

    private final Context context;

    private static String hotlineTitle = "";
    private static String emailTitle = "";

    private final ArrayList<CustomerService_flower> customerServices = new ArrayList<>();

    public CustomerServiceListAdapter_flower(Context context){
        this.context = context;
        hotlineTitle = context.getString(R.string.customer_service_hotline);
        emailTitle = context.getString(R.string.customer_service_email);
        initCustomerService();
    }

    @Override
    public int getCount() {
        return customerServices.size();
    }

    @Override
    public CustomerService_flower getItem(int position) {
        return customerServices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear(){
        customerServices.clear();
    }

    public void add(CustomerService_flower customerService){
        customerServices.add(customerService);
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view==null) view = LayoutInflater.from(context).inflate(R.layout.item_customer_service_flower,parent,false);

        TextView titleTv = view.findViewById(R.id.item_customer_service_tv_title);
        TextView textTv = view.findViewById(R.id.item_customer_service_tv_text);
        ViewGroup btnLayout = view.findViewById(R.id.item_customer_service_layout_btn);
        ImageView btnIv  = view.findViewById(R.id.item_customer_service_iv_btn);
        TextView btnTv = view.findViewById(R.id.item_customer_service_tv_btn);

        CustomerService_flower customerService = customerServices.get(position);

        titleTv.setText(customerService.getServiceTitle());
        String text = customerService.getServiceText();
        textTv.setText(text);

        if (customerService.getServiceType() == CustomerService_flower.CustomerServiceType.Hotline){
            btnIv.setImageResource(R.drawable.icon_dial);
            btnTv.setText(context.getString(R.string.word_dial));
            btnLayout.setOnClickListener(v -> {
                HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_PHONE_CALL_CLICK");
                String mobile =customerService.getServiceText().trim();
                String[] mobileArr = mobile.split(" ");
                if (mobileArr.length>0) mobile = mobileArr[mobileArr.length-1];
                Intent dialIntent_juucash =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
                context.startActivity(dialIntent_juucash);
            });
        }else if (customerService.getServiceType() == CustomerService_flower.CustomerServiceType.Email){
            btnIv.setImageResource(R.drawable.icon_copy);
            btnTv.setText(context.getString(R.string.word_copy));
            btnLayout.setOnClickListener(v -> {
                HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_EMAIL_COPY_CLICK");
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", customerService.getServiceText().trim());
                cm.setPrimaryClip(mClipData);
                Toast.makeText(context, context.getString(R.string.word_copy_success), Toast.LENGTH_SHORT).show();
            });
        }

        return view;
    }

    public void initCustomerService(){
        clear();
        String[] serviceHotlines = AppShowInfo_flower.getInstance().getMobile();
        for (String serviceHotline:serviceHotlines){
            if (!serviceHotline.isEmpty()){
                add(new CustomerService_flower(
                        CustomerService_flower.CustomerServiceType.Hotline,
                        hotlineTitle,
                        serviceHotline
                ));
            }
        }

        String[] serviceEmails = AppShowInfo_flower.getInstance().getEmail();
        for (String serviceEmail:serviceEmails){
            if (!serviceEmail.isEmpty()){
                add(new CustomerService_flower(
                        CustomerService_flower.CustomerServiceType.Email,
                        emailTitle,
                        serviceEmail
                ));
            }
        }
    }


    @Override
    public String getPagetag() {
        return "DIALOG_CUSTOMER_SERVICE";
    }
}
