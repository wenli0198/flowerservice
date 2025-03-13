package com.test.aoner.fanow.test.util_flower.helper_flower;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class ActivityHelper_flower {


    public static Activity getActivityFromView(View view) {
        if (null != view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }

    public static void hideKeyboard(View view) {
        Activity activity = getActivityFromView(view);
        if (activity != null) {
            hideKeyboard(activity);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View decorView = activity.getWindow().peekDecorView();
        if (null != decorView) {
            inputMethodManager.hideSoftInputFromWindow(decorView.getWindowToken(), 0);
        }
    }

    public static boolean canHideKeyboard(@NonNull View view, @NonNull MotionEvent motionEvent) {
        if ((view instanceof EditText)) {
            int[] rectI = new int[]{0, 0};
            view.getLocationInWindow(rectI);
            int left = rectI[0];
            int top = rectI[1];
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            return !(motionEvent.getX() >= left && motionEvent.getX() <= right &&
                    motionEvent.getY() >= top && motionEvent.getY() <= bottom);
        }
        return false;
    }
}
