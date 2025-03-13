package com.test.aoner.fanow.test.activity_fragment_flower;

import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TimeZone_Ta;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.login_flower.LoginPhoneNumActivity_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.dialog_flower.LanguageSelectDialog_flower;
import com.test.aoner.fanow.test.util_flower.PermissionUtil_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.v50.dialog.AllPermissionDialog_flowerloan;

import java.util.TimeZone;

import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity_flower extends BaseActivity_flower {

    public static boolean isSmsPermissionDeny_flower = false;

    private static final String[] permissions_flower = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_SMS};

    private AllPermissionDialog_flowerloan permissionDialog_flower;

    private View operatedView;
    private ImageView operatedIv;
    private TextView operatedCompanyNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flower);

        operatedView = findViewById(R.id.activity_main_view_operated);
        operatedIv = findViewById(R.id.activity_main_iv_operated);
        operatedCompanyNameTv = findViewById(R.id.activity_main_tv_operated_company_name);

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            runOnUiThread(() -> {
                findViewById(R.id.activity_main_layout_login).setOnClickListener(v -> init());
                init();
            });
        }).start();
    }

    private void init() {
        StringUtil_flower.parseAddress(this);

        if (TimeZone.getDefault().getID().equalsIgnoreCase(Constant_flower.TimeZone_Ta)) {
            if (UserInfoHelper_flower.getInstance().hasSetLanguage()) {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(UserInfoHelper_flower.getInstance().isLanguageEn() ? "en" : "sw"));
                initIp();
            }
            else {
                new LanguageSelectDialog_flower(MainActivity_flower.this, this::initIp, false).show();
            }
        }else initIp();
    }

    public void initIp(){

        if (!TimeZone.getDefault().getID().equalsIgnoreCase(TimeZone_Ta)){
            HttpManager_flower.getInstance().queryGlobal();
            return;
        }

        HttpManager_flower.getInstance().queryGlobal();
    }

    public void onGlobalResponse_flower() {

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                operatedView,
                operatedIv,
                operatedCompanyNameTv);

        process_flower();
    }

    public void process_flower() {
        if (needRequestPermission_flower()) {
            if (permissionDialog_flower == null)
                permissionDialog_flower = new AllPermissionDialog_flowerloan(this, this::process_flower);
            permissionDialog_flower.show();
            return;
        }

        if (permissionDialog_flower != null) permissionDialog_flower.hide();

        startActivityForSingleTop_flower(LoginPhoneNumActivity_flower.class);
    }

    private boolean needRequestPermission_flower() {
        if (!PermissionUtil_flower.checkLaunchPermissions(this, Manifest.permission.READ_PHONE_STATE))
            return true;

        if (!isSmsPermissionDeny_flower && !PermissionUtil_flower.checkLaunchPermissions(this, permissions_flower))
            return true;

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionDialog_flower != null && permissionDialog_flower.isShowing())
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, permissionDialog_flower);
    }

    @Override
    public String getPagetag() {
        return "LAUNCH";
    }

    @Override
    protected void onDestroy() {
        if (permissionDialog_flower != null) permissionDialog_flower.dismiss();
        super.onDestroy();
    }
}