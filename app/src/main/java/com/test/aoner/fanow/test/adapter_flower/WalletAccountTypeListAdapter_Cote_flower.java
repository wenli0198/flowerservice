package com.test.aoner.fanow.test.adapter_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.aoner.fanow.test.bean_flower.config_info_flower.AppShowInfo_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WalletAccountTypeListAdapter_Cote_flower extends BaseAdapter {

    private final LayoutInflater inflater;
    private final ArrayList<AppShowInfo_flower.WalletChannel> walletChannels;

    public WalletAccountTypeListAdapter_Cote_flower(Context context) {
        inflater = LayoutInflater.from(context);
        this.walletChannels = AppShowInfo_flower.getInstance().getWalletChannels();
    }

    @Override
    public int getCount() {
        return walletChannels.size();
    }

    @Override
    public AppShowInfo_flower.WalletChannel getItem(int i) {
        return walletChannels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.item_wallet_account_type_cote_flower,viewGroup,false);

        AppShowInfo_flower.WalletChannel walletChannel = walletChannels.get(i);

        ImageView accountTypeIv = view.findViewById(R.id.item_wallet_account_type_iv_account_type);
        TextView accountTypeTv = view.findViewById(R.id.item_wallet_account_type_tv_account_type);

        accountTypeTv.setText(walletChannel.getDesc());

        if (walletChannel.getBitmap()!=null){
            accountTypeIv.setImageBitmap(walletChannel.getBitmap());
        }else {
            ThreadUtil_flower.getInstance().runOnChildThread(() -> {
                try {
                    if (TextUtils.isEmpty(walletChannel.getLogo())) return;
                    URL url = new URL(walletChannel.getLogo());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    if (connection.getResponseCode()==200){
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        walletChannel.setBitmap(bitmap);
                        ThreadUtil_flower.getInstance().runOnUiThread(() -> {
                            if (accountTypeIv!=null) accountTypeIv.setImageBitmap(bitmap);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return view;
    }

}
