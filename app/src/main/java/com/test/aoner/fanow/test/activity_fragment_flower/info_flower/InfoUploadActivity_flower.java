package com.test.aoner.fanow.test.activity_fragment_flower.info_flower;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseInfoActivity_flower;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessData_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessInfo_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessStep_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.base_flower.BaseInfoInputViewManager_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.AddressPickViewManager_Niri_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.AddressPickViewManager_Tanzan_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.AddressPickViewManager_Viet_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.ContactSelectViewManager_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.CreditReportViewManager_Niri_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.DatePickViewManager_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.GenderSelectViewManager_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.InputViewManager_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.SelectViewManager_flower;
import com.test.aoner.fanow.test.view_flower.module_flower.ContactWidgetManager;
import com.test.aoner.fanow.test.view_flower.widget_flower.TitleView_flower;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeMap;


public class InfoUploadActivity_flower extends BaseInfoActivity_flower {

    private LinearLayout groupLayout;

    private ArrayList<BaseInfoInputViewManager_flower> infoInputViewManagers = new ArrayList<>();
    private ArrayList<ContactWidgetManager> contactWidgetManagers = new ArrayList<>();

    private ProcessStep_flower step;

    private String paramNameBankCode = "";
    private ProcessData_flower bankNameData;
    private final TreeMap<String,String> map_bankName_bankCode = new TreeMap<>();

    private int selectContactIndex = -1;

    private long time1 = 0;

    private final ActivityResultLauncher<Intent> selectContactArl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();

                if (data!=null && result.getResultCode()==RESULT_OK && selectContactIndex>=0 && contactWidgetManagers.size()>selectContactIndex){
                    Uri contactUri = data.getData();
                    String[] queryFields = new String[]{
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    Cursor cursor = getContentResolver().query(contactUri,queryFields,null,null,null);
                    if (cursor!=null){
                        if (cursor.moveToFirst()){
                            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            @SuppressLint("Range") String mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            final long time2 = System.currentTimeMillis();
                            HttpManager_flower.getInstance().saveUserBuriedPoint(
                                    getPagetag()+"_CONTACT_SELECT_"+selectContactIndex+"_INPUT",name+":"+mobile+"$$"+(time2-time1));
                            contactWidgetManagers.get(selectContactIndex).setShow(name,mobile);
                        }
                        cursor.close();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_upload_flower);
        init();
    }

    @SuppressLint("InflateParams")
    private void init(){
        TitleView_flower titleView = findViewById(R.id.activity_info_upload_view_title);
        setBackBtn_flower(titleView.getBackIb());

        groupLayout = findViewById(R.id.activity_info_upload_layout_group);

        findViewById(R.id.activity_info_upload_btn_next).setOnClickListener(v -> {

            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_SAVE_CLICK");

            for (BaseInfoInputViewManager_flower widgetsManager: infoInputViewManagers){
                if (!widgetsManager.checkInput()){
                    showToast_flower(getString(R.string.word_input_tip));
                    return;
                }
            }
            if (step ==null) return;
            String url = step.getApiUrl();
            if (url.isEmpty()) return;
            TreeMap<String,String> dataMap = new TreeMap<>();

            for (BaseInfoInputViewManager_flower baseInfoInputViewManager: infoInputViewManagers){

                ProcessData_flower data = baseInfoInputViewManager.getData();
                if (data==null) continue;
                switch (data.getAction()){

                    case Constant_flower.Data_Action_Address_Pick:
                        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Nigeria)){
                            if (!(baseInfoInputViewManager instanceof AddressPickViewManager_Niri_flower)) continue;
                            AddressPickViewManager_Niri_flower addressPickViewManager = (AddressPickViewManager_Niri_flower) baseInfoInputViewManager;
                            dataMap.put(data.getParamNameState(),addressPickViewManager.getState());
                            dataMap.put(data.getParamNameCity(),addressPickViewManager.getCity());
                            dataMap.put(data.getParamNameArea(),addressPickViewManager.getArea());
                        }
                        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Vietnam)){
                            if (!(baseInfoInputViewManager instanceof AddressPickViewManager_Viet_flower)) continue;
                            AddressPickViewManager_Viet_flower addressPickViewManager = (AddressPickViewManager_Viet_flower) baseInfoInputViewManager;
                            dataMap.put(data.getParamName(),addressPickViewManager.getState());
                            dataMap.put(data.getParamNameCity(),addressPickViewManager.getCity());
                        }
                        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Tanzan)){
                            if (!(baseInfoInputViewManager instanceof AddressPickViewManager_Tanzan_flower)) continue;
                            AddressPickViewManager_Tanzan_flower addressPickTanzanManager = (AddressPickViewManager_Tanzan_flower) baseInfoInputViewManager;
                            dataMap.put(data.getParamName(),addressPickTanzanManager.getState());
                            dataMap.put(data.getParamNameCity(),addressPickTanzanManager.getCity());
                        }
                        break;

                    case Constant_flower.Data_Action_Bank_Pick:
                        String bankName = ((SelectViewManager_flower) baseInfoInputViewManager).getTextInput();
                        String bankCode = map_bankName_bankCode.get(bankName);
                        dataMap.put(data.getParamNameBankName(),bankName);
                        dataMap.put(data.getParamNameBankCode(),bankCode);
                        break;


                    default: dataMap.put(data.getParamName(),baseInfoInputViewManager.getInput());
                }

            }

            if (!TextUtils.isEmpty(step.getContactParamName())){
                JSONArray jsonArray = new JSONArray();
                for (ContactWidgetManager contactWidgetManager:contactWidgetManagers){
                    jsonArray.put(contactWidgetManager.toJson());
                }
                dataMap.put(step.getContactParamName(),jsonArray.toString());
            }

            HttpManager_flower.getInstance().uploadInfo(url,dataMap);
        });

        step = ProcessInfo_flower.getInstance().getStep(0);
        String stepName = ProcessInfo_flower.getInstance().getStepName(0);

        if (step ==null) return;

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Cote) ||
                StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Vietnam)){
            titleView.setTitle(ProcessInfo_flower.getInstance().getStepTitle());
        }else titleView.setTitle(StringUtil_flower.processTitleFormat(ProcessInfo_flower.getInstance().getStepName(0)));

        if (step.isContact()){

            for (int i = 0; i< step.getExtDataListSize(); i++){
                ProcessData_flower data = step.getExtProcessData(i);
                if (data==null) continue;
                addInputView(data,null);
            }

            if (step.getExtDataListSize()>0) groupLayout.addView(LayoutInflater.from(this).inflate(R.layout.view_line_flower,null));

            int count = 0;

            while (count< step.getItemCount()){

                for (int i = 0; i< step.getDataListSize(); i++){
                    ProcessData_flower data = step.getProcessData(i);
                    if (data==null) continue;
                    addInputView(data, step.getRelationShips(count));
                }

                if (++count< step.getItemCount()) groupLayout.addView(LayoutInflater.from(this).inflate(R.layout.view_line_flower,null));

            }

            return;
        }

        for (int i = 0; i< step.getDataListSize(); i++){
            ProcessData_flower data = step.getProcessData(i);
            if (data==null) continue;
            addInputView(data,null);
        }

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                findViewById(R.id.activity_info_upload_view_operated),
                findViewById(R.id.activity_info_upload_iv_operated_logo),
                findViewById(R.id.activity_info_upload_tv_company_name));

    }

    @SuppressLint("InflateParams")
    private void addInputView(ProcessData_flower data, ArrayList<String> relationships){

        View inputView = null;

        switch (data.getAction()){

            case Constant_flower.Data_Action_Select:
                inputView = LayoutInflater.from(this).inflate(R.layout.view_select_flower,null);
                SelectViewManager_flower selectViewManager = new SelectViewManager_flower(inputView,data,getPagetag());
                infoInputViewManagers.add(selectViewManager);
                break;

            case Constant_flower.Data_Action_Date_Pick:
                inputView = LayoutInflater.from(this).inflate(R.layout.view_date_picker_flower,null);
                DatePickViewManager_flower datePickWidgetsManager = new DatePickViewManager_flower(inputView,data,getPagetag());
                infoInputViewManagers.add(datePickWidgetsManager);
                break;

            case Constant_flower.Data_Action_Contact_Relation:
                if (relationships!=null) data.setRelationships(relationships);
                inputView = LayoutInflater.from(this).inflate(R.layout.view_select_flower,null);
                SelectViewManager_flower contactSelectViewManager = new SelectViewManager_flower(inputView,data,getPagetag());
                addContactRelationWidget(contactSelectViewManager);
                break;

            case Constant_flower.Data_Action_Gender_Pick:
                inputView = LayoutInflater.from(this).inflate(R.layout.view_gender_select_flower,null);
                GenderSelectViewManager_flower genderSelectViewManager = new GenderSelectViewManager_flower(inputView,data,getPagetag());
                infoInputViewManagers.add(genderSelectViewManager);
                break;

            case Constant_flower.Data_Action_Credit_Report:
                inputView = LayoutInflater.from(this).inflate(R.layout.view_credit_report_niri_flower,null);
                CreditReportViewManager_Niri_flower creditReportViewManager = new CreditReportViewManager_Niri_flower(inputView,data,getPagetag());
                infoInputViewManagers.add(creditReportViewManager);
                break;

            case Constant_flower.Data_Action_Address_Pick:
                if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Nigeria)){
                    inputView = LayoutInflater.from(this).inflate(R.layout.view_address_pick_flower,null);
                    AddressPickViewManager_Niri_flower addressPickViewManager = new AddressPickViewManager_Niri_flower(inputView,data,getPagetag());
                    infoInputViewManagers.add(addressPickViewManager);
                }
                if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Vietnam)){
                    inputView = LayoutInflater.from(this).inflate(R.layout.view_address_pick_flower,null);
                    AddressPickViewManager_Viet_flower addressPickViewManager = new AddressPickViewManager_Viet_flower(inputView,data,getPagetag());
                    infoInputViewManagers.add(addressPickViewManager);
                }
                if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Tanzan)){
                    inputView = LayoutInflater.from(this).inflate(R.layout.view_address_pick_flower,null);
                    AddressPickViewManager_Tanzan_flower addressPickViewManager = new AddressPickViewManager_Tanzan_flower(inputView,data,getPagetag());
                    infoInputViewManagers.add(addressPickViewManager);
                }
                break;

            case Constant_flower.Data_Action_Bank_Pick:
                bankNameData = data;
                HttpManager_flower.getInstance().fetchBankCodes_Niri();
                break;

            case Constant_flower.Data_Action_Contact_Name:
                inputView = LayoutInflater.from(this).inflate(R.layout.view_select_flower,null);
                final int num = contactWidgetManagers.size()-1;
                ContactSelectViewManager_flower contactNameSelectViewManager = new ContactSelectViewManager_flower(inputView,data,getPagetag(),v -> {
                    selectContactIndex = num;
                    time1 = System.currentTimeMillis();
                    selectContactArl.launch(new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI));
                });
                addContactNameWidget(contactNameSelectViewManager);
                break;
            case Constant_flower.Data_Action_Contact_Phone:
                inputView = LayoutInflater.from(this).inflate(R.layout.view_select_flower,null);
                final int num1 = contactWidgetManagers.size()-1;
                ContactSelectViewManager_flower contactmobileSelectViewManager = new ContactSelectViewManager_flower(inputView,data,getPagetag(),v -> {
                    selectContactIndex = num1;
                    time1 = System.currentTimeMillis();
                    selectContactArl.launch(new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI));
                });
                addContactMobileWidget(contactmobileSelectViewManager);
                break;
            case Constant_flower.Data_Action_Input:
            default:
                inputView = LayoutInflater.from(this).inflate(R.layout.view_input_flower,null);
                InputViewManager_flower inputWidgetsManager = new InputViewManager_flower(inputView,data,getPagetag());
                infoInputViewManagers.add(inputWidgetsManager);
                break;
        }

        if (inputView!=null) groupLayout.addView(inputView);
    }

    public void onFetchBankCodeResponse(JSONArray jsonArray){

        if (bankNameData == null || jsonArray == null) return;

        map_bankName_bankCode.clear();
        String[] bankNames = new String[jsonArray.length()];

        for (int i = 0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            String bankName = jsonObject.optString("bankName");
            String bankCode = jsonObject.optString("bankCode");
            bankNames[i] = bankName;
            map_bankName_bankCode.put(bankName,bankCode);
        }

        bankNameData.setBankNames(bankNames);
        @SuppressLint("InflateParams") View inputView = LayoutInflater.from(this).inflate(R.layout.view_select_flower,null);
        SelectViewManager_flower selectViewManager = new SelectViewManager_flower(inputView, bankNameData,getPagetag());
        infoInputViewManagers.add(selectViewManager);

        groupLayout.addView(inputView);
    }

    private void addContactRelationWidget(SelectViewManager_flower selectViewManager_flower){
        if (contactWidgetManagers.isEmpty() || contactWidgetManagers.get(contactWidgetManagers.size()-1).relationWidget!=null){
            contactWidgetManagers.add(new ContactWidgetManager());
        }
        contactWidgetManagers.get(contactWidgetManagers.size()-1).relationWidget = selectViewManager_flower;
    }

    private void addContactMobileWidget(ContactSelectViewManager_flower contactSelectViewManager){
        if (contactWidgetManagers.isEmpty() || contactWidgetManagers.get(contactWidgetManagers.size()-1).mobileWidget!=null){
            contactWidgetManagers.add(new ContactWidgetManager());
        }
        contactWidgetManagers.get(contactWidgetManagers.size()-1).mobileWidget = contactSelectViewManager;
    }

    private void addContactNameWidget(ContactSelectViewManager_flower contactSelectViewManager){
        if (contactWidgetManagers.isEmpty() || contactWidgetManagers.get(contactWidgetManagers.size()-1).nameWidget!=null){
            contactWidgetManagers.add(new ContactWidgetManager());
        }
        contactWidgetManagers.get(contactWidgetManagers.size()-1).nameWidget = contactSelectViewManager;
    }

}