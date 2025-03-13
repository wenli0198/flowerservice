package com.test.aoner.fanow.test.dialog_flower;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.R;

public class InitOfflinePayChannelDialog_flower extends Dialog {

    private final Runnable mFinishRunable = this::stopCutdown;

    private OnDismissListener mListener;

    private TextView secCountDownTv;

    public static void showDialog(Context context, OnDismissListener onDismissListener) {
        InitOfflinePayChannelDialog_flower dialog = new InitOfflinePayChannelDialog_flower(context);
        dialog.mListener = onDismissListener;
        dialog.show();
    }

    public InitOfflinePayChannelDialog_flower(@NonNull Context context) {
        super(context);
    }

    public InitOfflinePayChannelDialog_flower(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected InitOfflinePayChannelDialog_flower(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_init_offline_pay_channel_flower);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        secCountDownTv = findViewById(R.id.tv_second_count_down);
    }

    @Override
    public void show() {
        super.show();
        ThreadUtil_flower.getInstance().postDelay(mFinishRunable, 10500);
        new Thread(() -> {
            int count = 10;
            while (count-- > 0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = count;
                ThreadUtil_flower.getInstance().runOnUiThread(() -> secCountDownTv.setText(String.valueOf(i)));
            }

        }).start();
    }

    public void stopCutdown() {
        if (mListener != null) {
            mListener.onDismiss(null);
        }
        dismiss();
    }
}
