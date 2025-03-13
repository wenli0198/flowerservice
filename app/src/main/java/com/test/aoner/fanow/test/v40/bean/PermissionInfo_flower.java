package com.test.aoner.fanow.test.v40.bean;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.v40.listener.PermissionListener;
import com.test.aoner.fanow.test.v50.adapter.PermissionViewAdapter_flowerloan;


public class PermissionInfo_flower {

    private final Integer iconId;
    private final String title;
    private final String title1;
    private final String text1;
    private final String title2;
    private final String text2;
    private final String title3;
    private final String text3;
    private final String title4;
    private final String text4;
    private final PermissionListener listener;

    private PermissionViewAdapter_flowerloan.PermissionDialogHolder_flower holder;

    public PermissionInfo_flower(int tupianId, String zhubiaoti,
                                 String title1, String text1, String title2, String text2, String title3, String text3, String title4, String text4,
                                 PermissionListener listener) {
        this.iconId = tupianId;
        this.title = zhubiaoti;
        this.title1 = title1;
        this.text1 = text1;
        this.title2 = title2;
        this.text2 = text2;
        this.title3 = title3;
        this.text3 = text3;
        this.title4 = title4;
        this.text4 = text4;
        this.listener = listener;
    }

    public Integer getIconId_flower() {
        return iconId;
    }

    public String getTitle_flower() {
        return StringUtil_flower.getSafeString(title);
    }

    public String getTitle1_flower() {
        return StringUtil_flower.getSafeString(title1);
    }

    public String getText1_flower() {
        return StringUtil_flower.getSafeString(text1);
    }

    public String getTitle2_flower() {
        return StringUtil_flower.getSafeString(title2);
    }

    public String getText2_flower() {
        return StringUtil_flower.getSafeString(text2);
    }

    public String getTitle3_flower() {
        return StringUtil_flower.getSafeString(title3);
    }

    public String getText3_flower() {
        return StringUtil_flower.getSafeString(text3);
    }

    public String getTitle4_flower() {
        return StringUtil_flower.getSafeString(title4);
    }

    public String getText4_flower() {
        return StringUtil_flower.getSafeString(text4);
    }

    public PermissionListener getListener_flower() {
        return listener;
    }

    public void setHolder_flower(PermissionViewAdapter_flowerloan.PermissionDialogHolder_flower holder) {
        this.holder = holder;
    }

    public PermissionViewAdapter_flowerloan.PermissionDialogHolder_flower getHolder_flower() {
        return holder;
    }

    public void setViewAgree_flower(){
        if (holder!=null) holder.agree();
    }

}
