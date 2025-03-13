package com.test.aoner.fanow.test.util_flower;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class PermissionUtil_flower {

    public static final int REQ_CODE_PERMISSIONS = 300;
    public static final int REQ_CODE_PERMISSION_CAMERA = 299;


    public static boolean checkLaunchPermissions(@NonNull Activity activity, String... permissions) {
        try {
            for (String permission:permissions){
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(activity, permission)) {
                    Log.d(PermissionUtil_flower.class.getSimpleName(), "checkPermissions: "+permission);
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
