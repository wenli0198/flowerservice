package com.test.aoner.fanow.test.activity_fragment_flower.info_flower;

import static com.test.aoner.fanow.test.util_flower.PermissionUtil_flower.REQ_CODE_PERMISSION_CAMERA;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseInfoActivity_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.dialog_flower.PhotoSelectDialog_flower;
import com.test.aoner.fanow.test.util_flower.PermissionUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.util_flower.imageUtil_flower.ImageConvert_flower;
import com.test.aoner.fanow.test.util_flower.imageUtil_flower.PhotoUtil_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.SelectView_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.TitleView_flower;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class OcrUploadActivity_flower extends BaseInfoActivity_flower{

    private SelectView_flower typeSv;
    private ImageView imgFrontIv, imgBackIv;
    private TextView descFrontTv, descBackTv;
    private View imgBackView;

    public static final String Type_1_IdCard ="IDCARD", Type_2_Driving ="DRIVING", Type_3_Passport ="PASSPORT";

    private String selectType = Type_1_IdCard;

    private String frontImgPath = "",backImgPath = "";
    private Uri frontImgUri,backImgUri;
    private Bitmap frontImgBm,backImgBm;
    private String frontImgUrl,backImgUrl;

    private boolean uploadImgFlag = true;

    private Runnable runnableWithCameraPermission = null;

    private final ActivityResultLauncher<Intent> startCameraForFrontArl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    if (frontImgUri == null || TextUtils.isEmpty(frontImgPath)){
                        frontImgUrl = "";
                        Toast.makeText(this,getString(R.string.select_photo_fail_tips),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    new Thread(() -> ImageConvert_flower.startConvertImage(
                            new File(frontImgPath),
                            (localPath, thumpImg) -> {
                                frontImgBm = thumpImg;
                                uploadImgFlag = true;
                                HttpManager_flower.getInstance().uploadImage(localPath);
                            }
                    )).start();
                }else Toast.makeText(this,getString(R.string.select_photo_fail_tips),Toast.LENGTH_SHORT).show();
            }
    );

    private final ActivityResultLauncher<Intent> startCameraForBackArl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    if (backImgUri == null || TextUtils.isEmpty(backImgPath)){
                        backImgUrl = "";
                        Toast.makeText(this,getString(R.string.select_photo_fail_tips),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    new Thread(() -> ImageConvert_flower.startConvertImage(
                            new File(backImgPath),
                            (localPath, thumpImg) -> {
                                backImgBm = thumpImg;
                                uploadImgFlag = false;
                                HttpManager_flower.getInstance().uploadImage(localPath);
                            }
                    )).start();
                }else Toast.makeText(this,getString(R.string.select_photo_fail_tips),Toast.LENGTH_SHORT).show();
            }
    );

    private final ActivityResultLauncher<PickVisualMediaRequest> selectImgFromAlbumForFrontArl = registerForActivityResult(
            new ActivityResultContracts.PickVisualMedia(),
            uri -> {
                frontImgUri = uri;
                frontImgPath = ImageConvert_flower.getBitmapFilePathFromURI(this,frontImgUri);
                if (frontImgUri == null || TextUtils.isEmpty(frontImgPath)){
                    frontImgUrl = "";
                    Toast.makeText(this,getString(R.string.select_photo_fail_tips),Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(() -> ImageConvert_flower.startConvertImage(
                        new File(frontImgPath),
                        (localPath, thumpImg) -> {
                            frontImgBm = thumpImg;
                            uploadImgFlag = true;
                            HttpManager_flower.getInstance().uploadImage(localPath);
                        }
                )).start();
            }
    );

    private final ActivityResultLauncher<PickVisualMediaRequest> selectImgFromAlbumForBackArl = registerForActivityResult(
            new ActivityResultContracts.PickVisualMedia(),
            uri -> {
                backImgUri = uri;
                backImgPath = ImageConvert_flower.getBitmapFilePathFromURI(this,backImgUri);
                if (backImgUri == null || TextUtils.isEmpty(backImgPath)){
                    backImgUrl = "";
                    Toast.makeText(this,getString(R.string.select_photo_fail_tips),Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(() -> ImageConvert_flower.startConvertImage(
                        new File(backImgPath),
                        (localPath, thumpImg) -> {
                            backImgBm = thumpImg;
                            uploadImgFlag = false;
                            HttpManager_flower.getInstance().uploadImage(localPath);
                        }
                )).start();
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_upload_flower);
        init();
    }

    private void init(){

        TitleView_flower titleView = findViewById(R.id.activity_ocr_upload_view_title);
        setBackBtn_flower(titleView.getBackIb());

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                findViewById(R.id.activity_ocr_upload_view_operated),
                findViewById(R.id.activity_ocr_upload_iv_operated_logo),
                findViewById(R.id.activity_ocr_upload_tv_company_name));

        typeSv = findViewById(R.id.activity_ocr_upload_sv_type);
        imgFrontIv = findViewById(R.id.activity_ocr_upload_iv_img_1);
        imgBackIv = findViewById(R.id.activity_ocr_upload_iv_img_2);
        descFrontTv = findViewById(R.id.activity_ocr_upload_tv_desc_1);
        descBackTv = findViewById(R.id.activity_ocr_upload_tv_desc_2);
        imgBackView = findViewById(R.id.activity_ocr_upload_view_img_2);

        final String type1 = getString(R.string.ocr_type_1);
        final String type2 = getString(R.string.ocr_type_2);
        final String type3 = getString(R.string.ocr_type_3);
        typeSv.addListItem(type1,type2,type3);
        typeSv.setInput(type1);
        typeSv.setOnPickListener(pickStr -> {
            if (type1.equalsIgnoreCase(pickStr)) selectType1();
            else if (type2.equalsIgnoreCase(pickStr)) selectType2();
            else if (type3.equalsIgnoreCase(pickStr)) selectType3();
        });

        imgFrontIv.setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("OCR_PHOTO_FRONT_CLICK");
            new PhotoSelectDialog_flower(this,
                () -> runnWithCameraPermission(() -> {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    String imageFilePath = PhotoUtil_flower.imageFilePath();
                    File imageFile = new File(imageFilePath);
                    frontImgPath = imageFilePath;
                    String providerAuthority = BaseApplication_flower.getApplication_flower().getPackageName() + ".file_provider";
                    frontImgUri = FileProvider.getUriForFile(BaseApplication_flower.getApplication_flower(), providerAuthority, imageFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, frontImgUri);
                    startCameraForFrontArl.launch(intent);
                }),
                () -> selectImgFromAlbumForFrontArl.launch(
                        new PickVisualMediaRequest.Builder()
                                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                .build()
                )
            ).show();
        });

        imgBackIv.setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("OCR_PHOTO_BACK_CLICK");
            new PhotoSelectDialog_flower(this,
                    () -> runnWithCameraPermission(() -> {
                        Intent intent = new Intent();
                        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        String imageFilePath = PhotoUtil_flower.imageFilePath();
                        File imageFile = new File(imageFilePath);
                        backImgPath = imageFilePath;
                        String providerAuthority = BaseApplication_flower.getApplication_flower().getPackageName() + ".file_provider";
                        backImgUri = FileProvider.getUriForFile(BaseApplication_flower.getApplication_flower(), providerAuthority, imageFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, backImgUri);
                        startCameraForBackArl.launch(intent);
                    }),
                    () -> selectImgFromAlbumForBackArl.launch(
                            new PickVisualMediaRequest.Builder()
                                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                    .build()
                    )
            ).show();
        });

        findViewById(R.id.activity_ocr_upload_btn_submit).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint("OCR_SUBMIT_CLICK");


            if (TextUtils.isEmpty(frontImgUrl) || (TextUtils.isEmpty(backImgUrl) && !Type_3_Passport.equalsIgnoreCase(selectType))){
                Toast.makeText(this,getString(R.string.tips_ocr_no_img),Toast.LENGTH_SHORT).show();
                return;
            }

            HttpManager_flower.getInstance().uploadOcrInfo(selectType,frontImgUrl,backImgUrl);

        });

    }

    private void selectType1(){
        if (selectType.equalsIgnoreCase(Type_1_IdCard)) return;
        HttpManager_flower.getInstance().saveUserBuriedPoint("OCR_TYPE_ID_CARD_CLICK");
        selectType = Type_1_IdCard;
        clearPhotos();
        imgBackView.setVisibility(View.VISIBLE);
        descFrontTv.setText(getString(R.string.ocr_1_desc_front));
        descBackTv.setText(getString(R.string.ocr_1_desc_back));
    }

    private void selectType2(){
        if (selectType.equalsIgnoreCase(Type_2_Driving)) return;
        HttpManager_flower.getInstance().saveUserBuriedPoint("OCR_TYPE_LICENSE_CLICK");
        selectType = Type_2_Driving;
        clearPhotos();
        imgBackView.setVisibility(View.VISIBLE);
        descFrontTv.setText(getString(R.string.ocr_2_desc_front));
        descBackTv.setText(getString(R.string.ocr_2_desc_back));
    }

    private void selectType3(){
        if (selectType.equalsIgnoreCase(Type_3_Passport)) return;
        HttpManager_flower.getInstance().saveUserBuriedPoint("OCR_TYPE_PASSPORT_CLICK");
        selectType = Type_3_Passport;
        clearPhotos();
        imgBackView.setVisibility(View.GONE);
        descFrontTv.setText(getString(R.string.ocr_3_desc));
    }

    private void clearPhotos(){
        imgFrontIv.setImageResource(R.drawable.bg_ocr_front);
        imgBackIv.setImageResource(R.drawable.bg_ocr_back);
    }

    public void onPhotoUploadSuccess(String url){

        if (uploadImgFlag){
            runOnUiThread(() -> {
                if (TextUtils.isEmpty(url) || frontImgBm == null){
                    Toast.makeText(this,getString(R.string.upload_photo_fail_tips),Toast.LENGTH_SHORT).show();
                    return;
                }
                frontImgUrl = url;
                imgFrontIv.setImageBitmap(frontImgBm);
            });
        }

        else {
            runOnUiThread(() -> {
                if (TextUtils.isEmpty(url) || backImgBm == null){
                    Toast.makeText(this,getString(R.string.upload_photo_fail_tips),Toast.LENGTH_SHORT).show();
                    return;
                }
                backImgUrl = url;
                imgBackIv.setImageBitmap(backImgBm);
            });
        }

    }

    private void runnWithCameraPermission(Runnable runnable){
        if (PermissionUtil_flower.checkLaunchPermissions(this, Manifest.permission.CAMERA)){
            if (runnable!=null) runnable.run();
        }else {
            this.runnableWithCameraPermission = runnable;
            EasyPermissions.requestPermissions(this,getString(R.string.launch_permissions_camera),REQ_CODE_PERMISSION_CAMERA,Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == REQ_CODE_PERMISSION_CAMERA && runnableWithCameraPermission!=null){
            HttpManager_flower.getInstance().saveUserBuriedPoint("OCR_CAMERA_PERMISSION_AGREE_CLICK");
            runnableWithCameraPermission.run();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms) && requestCode == REQ_CODE_PERMISSION_CAMERA){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        if (requestCode == REQ_CODE_PERMISSION_CAMERA){
            EasyPermissions.requestPermissions(this,getString(R.string.launch_permissions_camera),REQ_CODE_PERMISSION_CAMERA,Manifest.permission.CAMERA);
        }
    }


}