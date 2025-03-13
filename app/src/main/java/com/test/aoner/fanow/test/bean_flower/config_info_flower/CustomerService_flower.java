package com.test.aoner.fanow.test.bean_flower.config_info_flower;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;

public class CustomerService_flower {

    private final CustomerServiceType serviceType;
    private final String serviceTitle, serviceText;

    public CustomerService_flower(CustomerServiceType serviceType, String serviceTitle, String serviceText) {
        this.serviceType = serviceType;
        this.serviceTitle = serviceTitle;
        this.serviceText = serviceText;
    }

    public CustomerServiceType getServiceType() {
        return serviceType;
    }

    public String getServiceTitle() {
        return StringUtil_flower.getSafeString(serviceTitle);
    }

    public String getServiceText() {
        return StringUtil_flower.getSafeString(serviceText);
    }

    public enum CustomerServiceType {
        Hotline,
        Email
    }

}
