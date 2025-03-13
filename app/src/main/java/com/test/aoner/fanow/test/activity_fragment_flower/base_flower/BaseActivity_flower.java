package com.test.aoner.fanow.test.activity_fragment_flower.base_flower;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.loan_flower.LoanMainActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.login_flower.LoginPhoneNumActivity_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.PermissionUtil_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.ActivityHelper_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpPresenter_flower;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public abstract class BaseActivity_flower extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks,PagetagInterface {

    private Toast toast_flower;

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Constant_flower.DebugFlag) getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        finish();
    }

    public void recordPagePauseEvent_flower() {
        HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag() + "_END").toUpperCase());
    }

    public void onUserProcessResponse_flower() {
        startActivityForSingleTop_flower(LoanMainActivity_flower.class);
    }

    public void showToastOnUiThread_flower(String text) {
        runOnUiThread(() -> showToast_flower(text));
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpPresenter_flower.getInstance().setActivity(this);
        recordPageResumeEvent_flower();
    }

    public void showToast_flower(String text) {
        if (toast_flower == null) toast_flower = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        else toast_flower.setText(text);
        toast_flower.show();
    }

    public <T extends BaseActivity_flower> void startActivityForSingleTop_flower(Class<T> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity_flower(activityClass);
        finish();
    }

    public void setBackBtn_flower(View btnView) {
        btnView.setOnClickListener(view -> onBackPressed());
    }

    public void recordPageResumeEvent_flower() {
        HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag() + "_START").toUpperCase());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null) {
                if (ActivityHelper_flower.canHideKeyboard(view, ev)) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        ActivityHelper_flower.hideKeyboard(this);
                        EditText editText = (EditText) view;
                        editText.clearFocus();
                    }
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    protected void onLaunchPermissionsAllPass_flower() {}

    public <T extends BaseActivity_flower> void startActivity_flower(Class<T> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @AfterPermissionGranted(PermissionUtil_flower.REQ_CODE_PERMISSIONS)
    public void onEasyPermissionsAllPass() {
        Log.d(this.getClass().getSimpleName(), "on permissions all pass");
        onLaunchPermissionsAllPass_flower();
    }


    @Override
    protected void onPause() {
        super.onPause();
        HttpPresenter_flower.getInstance().setActivity(null);
        recordPagePauseEvent_flower();
    }

    public void requestLaunchPermissions_flower(String[] permissions) {
        EasyPermissions.requestPermissions(this, getString(R.string.launch_permissions), PermissionUtil_flower.REQ_CODE_PERMISSIONS, permissions);
    }



    protected boolean onCheckLaunchPermissions_flower() {
        return true;
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        if (GlobalConfig_flower.getInstance().getPermissionArr().length==0) return;
        if (!PermissionUtil_flower.checkLaunchPermissions(this, GlobalConfig_flower.getInstance().getPermissionArr()))
            requestLaunchPermissions_flower(GlobalConfig_flower.getInstance().getPermissionArr());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) requestLaunchPermissions_flower(GlobalConfig_flower.getInstance().getPermissionArr());
    }

    public void onUserCancelSuccess(){
        runOnUiThread(() -> {
            UserInfoHelper_flower.getInstance().setMobile("");
            UserInfoHelper_flower.getInstance().setToken("");
            UserInfoHelper_flower.getInstance().setUserId("");
            startActivityForSingleTop_flower(LoginPhoneNumActivity_flower.class);
        });
    }

}