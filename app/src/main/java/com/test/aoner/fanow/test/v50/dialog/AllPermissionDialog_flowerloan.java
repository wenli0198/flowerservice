package com.test.aoner.fanow.test.v50.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.MainActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.PagetagInterface;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.v40.bean.PermissionInfo_flower;
import com.test.aoner.fanow.test.v40.listener.PermissionListener;
import com.test.aoner.fanow.test.v50.adapter.PermissionViewAdapter_flowerloan;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class AllPermissionDialog_flowerloan extends Dialog implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks, PagetagInterface {

    private static boolean isShow = false;

    private static final int Permission_Code_Phonestate = 89;
    private static final int Permission_Code_Sms = 21;

    private ViewPager2 viewPager2;
    private PermissionViewAdapter_flowerloan permissionViewAdapter;
    private final ArrayList<PermissionInfo_flower> infos = new ArrayList<>();

    private final Context context;

    private final Runnable processRunnable;

    public AllPermissionDialog_flowerloan(@NonNull Context context, @NonNull Runnable processRunnable) {
        super(context);
        this.context = context;
        this.processRunnable = processRunnable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_permission_flower);

        setCancelable(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(R.color.trans);

        viewPager2 = findViewById(R.id.dialog_permission_vp_page);

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)){
            infos.add(new PermissionInfo_flower(
                    R.drawable.icon_permission_sms,
                    getContext().getString(R.string.permission_flower_sms_title),
                    getContext().getString(R.string.permission_flower_sms_title_1),
                    getContext().getString(R.string.permission_flower_sms_text_1),
                    getContext().getString(R.string.permission_flower_sms_title_2),
                    getContext().getString(R.string.permission_flower_sms_text_2),
                    getContext().getString(R.string.permission_flower_sms_title_3),
                    getContext().getString(R.string.permission_flower_sms_text_3),
                    "",
                    "",
                    smsPermissionListneer)
            );

            infos.add(new PermissionInfo_flower(
                    R.drawable.icon_permission_phone,
                    getContext().getString(R.string.permission_flower_applist_title),
                    getContext().getString(R.string.permission_flower_applist_title_1),
                    getContext().getString(R.string.permission_flower_applist_text_1),
                    getContext().getString(R.string.permission_flower_applist_title_2),
                    getContext().getString(R.string.permission_flower_applist_text_2),
                    getContext().getString(R.string.permission_flower_applist_title_3),
                    getContext().getString(R.string.permission_flower_applist_text_3),
                    getContext().getString(R.string.permission_flower_applist_title_4),
                    getContext().getString(R.string.permission_flower_applist_text_4),
                    applistPermissionListneer)
            );
        }

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE)){
            infos.add(new PermissionInfo_flower(
                    R.drawable.icon_permission_phone,
                    getContext().getString(R.string.permission_flower_phonestate_title),
                    "",
                    getContext().getString(R.string.permission_flower_phonestate_text_1),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    phonestatePermissionListneer)
            );
        }

        permissionViewAdapter = new PermissionViewAdapter_flowerloan(getContext(),infos);
        viewPager2.setAdapter(permissionViewAdapter);

    }

    private final PermissionListener smsPermissionListneer = new PermissionListener() {
        @Override
        public void onFlowerPermissionAgree() {

            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_SMS_AGREE_CLICK");

            Runnable agreeRunnable = () -> {
                if (context instanceof Activity)
                    EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.launch_permissions_sms), Permission_Code_Sms, Manifest.permission.READ_SMS);
            };


            PermissionViewAdapter_flowerloan.PermissionDialogHolder_flower holder = permissionViewAdapter.getInfo(viewPager2.getCurrentItem()).getHolder_flower();

            if (holder.getCheck()) agreeRunnable.run();
            else {
                new PermissionAgreeDialog_flowerloan(context,null,() -> {
                    holder.setCheck(true);
                    agreeRunnable.run();
                }).show();
            }

        }

        @Override
        public void onFlowerPermissionDeny() {
            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_SMS_DENY_CLICK");
            MainActivity_flower.isSmsPermissionDeny_flower = true;
            int i1 = viewPager2.getCurrentItem();
            if (permissionViewAdapter.getItemCount()>i1+1)  viewPager2.setCurrentItem(i1+1);
            else processRunnable.run();
        }
    };

    private final PermissionListener applistPermissionListneer = new PermissionListener() {
        @Override
        public void onFlowerPermissionAgree() {

            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_APPS_AGREE_CLICK");

            Runnable agreeRunnable = () -> {
                MainActivity_flower.isSmsPermissionDeny_flower = true;
                permissionViewAdapter.getInfo(viewPager2.getCurrentItem()).setViewAgree_flower();
                int i1 = viewPager2.getCurrentItem();
                if (permissionViewAdapter.getItemCount()>i1+1)  viewPager2.setCurrentItem(i1+1);
                else processRunnable.run();
            };

            PermissionViewAdapter_flowerloan.PermissionDialogHolder_flower holder = permissionViewAdapter.getInfo(viewPager2.getCurrentItem()).getHolder_flower();

            if (holder.getCheck()) agreeRunnable.run();
            else {
                new PermissionAgreeDialog_flowerloan(context,null,() -> {
                    holder.setCheck(true);
                    agreeRunnable.run();
                }).show();
            }
        }

        @Override
        public void onFlowerPermissionDeny() {
            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_APPS_DENY_CLICK");
            MainActivity_flower.isSmsPermissionDeny_flower = true;
            int i1 = viewPager2.getCurrentItem();
            if (permissionViewAdapter.getItemCount()>i1+1) viewPager2.setCurrentItem(i1+1);
            else processRunnable.run();

        }
    };

    private final PermissionListener phonestatePermissionListneer = new PermissionListener() {
        @Override
        public void onFlowerPermissionAgree() {

            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_PHONE_STATE_AGREE_CLICK");

            Runnable agreeRunnable = () -> {
                if (context instanceof Activity)
                    EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.launch_permissions_phone), Permission_Code_Phonestate, Manifest.permission.READ_PHONE_STATE);
            };

            PermissionViewAdapter_flowerloan.PermissionDialogHolder_flower holder = permissionViewAdapter.getInfo(viewPager2.getCurrentItem()).getHolder_flower();

            if (holder.getCheck()) agreeRunnable.run();
            else {
                new PermissionAgreeDialog_flowerloan(context,
                        null,
                        () -> {
                            holder.setCheck(true);
                            agreeRunnable.run();
                }).show();
            }
        }

        @Override
        public void onFlowerPermissionDeny() {
            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_PHONE_STATE_DENY_CLICK");
            if (context!=null && context instanceof Activity) {
                dismiss();
                ((Activity)context).finish();
            }
        }
    };

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if (EasyPermissions.somePermissionPermanentlyDenied((Activity) context, perms))
            new AppSettingsDialog.Builder((Activity) context).build().show();
        else
            switch (requestCode){
                case Permission_Code_Phonestate:
                    dismiss();
                    ((Activity)context).finish();
                    break;
                case Permission_Code_Sms:
                    MainActivity_flower.isSmsPermissionDeny_flower = true;
                    int i1 = viewPager2.getCurrentItem();
                    if (permissionViewAdapter.getItemCount()>i1+1)  viewPager2.setCurrentItem(i1+1);
                    else processRunnable.run();
                    break;
            }

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode){
            case Permission_Code_Phonestate:
                if (viewPager2 == null) {
                    processRunnable.run();
                    return;
                }
                permissionViewAdapter.getInfo(viewPager2.getCurrentItem()).setViewAgree_flower();
                int i1 = viewPager2.getCurrentItem();
                if (permissionViewAdapter.getItemCount()>i1+1) viewPager2.setCurrentItem(i1+1);
                else processRunnable.run();
                break;
            case Permission_Code_Sms:
                permissionViewAdapter.getInfo(viewPager2.getCurrentItem()).setViewAgree_flower();
                PermissionViewAdapter_flowerloan.PermissionDialogHolder_flower holder = infos.get(0).getHolder_flower();
                if (holder!=null) {
                    int i2 = viewPager2.getCurrentItem();
                    if (permissionViewAdapter.getItemCount()>i2+1) viewPager2.setCurrentItem(i2+1);
                    else processRunnable.run();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        switch (requestCode){
            case Permission_Code_Phonestate:
                if (context instanceof Activity)
                    EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.launch_permissions_phone), Permission_Code_Phonestate, Manifest.permission.READ_PHONE_STATE);
                break;
            case Permission_Code_Sms:
                if (context instanceof Activity)
                    EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.launch_permissions_sms), Permission_Code_Sms, Manifest.permission.READ_SMS);
                break;
        }
    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }

    @Override
    public void show() {
        try {
            if (isShow) return;
            isShow = true;
            super.show();
        }catch (Exception e){
            if (Constant_flower.DebugFlag) e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        isShow = false;
        super.dismiss();
    }

    @Override
    public String getPagetag() {
        return "DIALOG_PERMISSION";
    }
}
