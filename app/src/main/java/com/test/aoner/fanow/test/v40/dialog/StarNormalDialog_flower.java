package com.test.aoner.fanow.test.v40.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.aoner.fanow.test.BuildConfig;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.ServiceFeedbackActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.PagetagInterface;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.AppShowInfo_flower;
import com.test.aoner.fanow.test.util_flower.AppReviewUtil;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.v40.bean.NormalWindowConfig_flower;


public class StarNormalDialog_flower extends Dialog implements PagetagInterface {

    private final AppCompatActivity activity;
    private final String url = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
    private int score = 0;
    private static boolean isShow = false;
    private final ImageButton[] starIbs = new ImageButton[5];

    public StarNormalDialog_flower(AppCompatActivity activity){
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_star_normal_flower);

        setCancelable(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);

        findViewById(R.id.dialog_star_normal_btn_notnow).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint((getClass().getSimpleName()+"_CANCEL_CLICK").toUpperCase());
            dismiss();
        });

        TextView textTv = findViewById(R.id.dialog_star_normal_tv_text);

        String content = NormalWindowConfig_flower.getInstance_flower().getContent_flower();
        if (!TextUtils.isEmpty(content)) textTv.setText(content);

        starIbs[0] = findViewById(R.id.dialog_star_normal_ib_star_1);
        starIbs[1] = findViewById(R.id.dialog_star_normal_ib_star_2);
        starIbs[2] = findViewById(R.id.dialog_star_normal_ib_star_3);
        starIbs[3] = findViewById(R.id.dialog_star_normal_ib_star_4);
        starIbs[4] = findViewById(R.id.dialog_star_normal_ib_star_5);

        findViewById(R.id.dialog_star_normal_btn_rate).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag()+"_RATE_CLICK").toUpperCase(),String.valueOf(score));

            if (score==0) {
                Toast.makeText(activity,activity.getString(R.string.flower_star_dialog_0rate_tip),Toast.LENGTH_SHORT).show();
                return;
            }

            if (score>=4){
                if (AppShowInfo_flower.getInstance().showSysReview()){
                    AppReviewUtil.INSTANCE.launchReviewFlow(activity, aBoolean -> {
                        if (!aBoolean) activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        dismiss();
                        return null;
                    });
                }else activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }else activity.startActivity(new Intent(activity, ServiceFeedbackActivity_flower.class));
            dismiss();
        });

        for (int i=0;i<starIbs.length;i++){
            ImageButton scoreIb = starIbs[i];
            final int fScore = i+1;
            if (scoreIb!=null) scoreIb.setOnClickListener(v -> setScore(fScore));
        }

    }


    private void setScore(int score){

        if (score>5) score=5;
        else if (score<1) score=1;

        this.score = score;

        HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag()+"_STAR").toUpperCase(),String.valueOf(score));

        for (int i=0;i<starIbs.length;i++){
            ImageButton scoreIb = starIbs[i];
            if (scoreIb==null) continue;
            scoreIb.setBackgroundResource(i<score ? R.drawable.icon_star_true:R.drawable.icon_star_false);
        }
    }

    @Override
    public void show() {
        if (isShow) return;
        isShow = true;
        super.show();
        HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag()+"_START").toUpperCase());
        UserInfoHelper_flower.getInstance().addNormaldialogCount();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isShow = false;
        HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag()+"_END").toUpperCase());
    }

    @Override
    public String getPagetag() {
        return "DIALOG_REVIEW_NORMAL";
    }
}
