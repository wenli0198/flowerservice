package com.test.aoner.fanow.test.bean_flower.config_info_flower;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;

import java.io.Serializable;


public class AdAccountInfo_flower implements Serializable {

    private String accM;

    private String accTk;

    public String getAccM() {
        return StringUtil_flower.getSafeString(accM);
    }

    public void setAccM(String accM) {
        this.accM = accM;
    }

    public String getAccTk() {
        return StringUtil_flower.getSafeString(accTk);
    }

    public void setAccTk(String accTk) {
        this.accTk = accTk;
    }
}
