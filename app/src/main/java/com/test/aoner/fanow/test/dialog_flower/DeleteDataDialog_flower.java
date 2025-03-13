package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

public class DeleteDataDialog_flower extends Dialog {

    private Thread countdownThread;

    public DeleteDataDialog_flower(@NonNull Context context) {
        super(context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_data_flower);

        getWindow().setBackgroundDrawableResource(R.color.trans);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        findViewById(R.id.dialog_delete_data_tv_return).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_DELETE_DATA_RETURN_CLICK");
            dismiss();
        });

        TextView confirmBtn = findViewById(R.id.dialog_delete_data_tv_confirm);


        Handler handler = new Handler();

        countdownThread = new Thread(() -> {
            int count = 10;
            while (count-->0){
                int finalCount = count;
                handler.post(() -> confirmBtn.setText(getContext().getString(R.string.flower_confirm)+" ("+ finalCount +"s)"));
                try {
                    Thread.sleep(999);
                } catch (InterruptedException e) {
                    if (Constant_flower.DebugFlag) e.printStackTrace();
                }
            }

            handler.post(() -> {
                confirmBtn.setText(getContext().getString(R.string.flower_confirm));
                confirmBtn.setBackgroundResource(R.drawable.ic_bg_btn);
                confirmBtn.setOnClickListener(v -> {
                    HttpManager_flower.getInstance().saveUserBuriedPoint("DIALOG_DELETE_DATA_RETURN_CLICK");
                    dismiss();
                    HttpManager_flower.getInstance().userCancel();
                });
            });
        });

        countdownThread.start();

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (countdownThread!=null&&countdownThread.isAlive()) countdownThread.interrupt();
    }

}
