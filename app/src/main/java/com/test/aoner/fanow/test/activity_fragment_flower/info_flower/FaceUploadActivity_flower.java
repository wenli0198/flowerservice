package com.test.aoner.fanow.test.activity_fragment_flower.info_flower;

import static com.test.aoner.fanow.test.util_flower.PermissionUtil_flower.REQ_CODE_PERMISSION_CAMERA;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseInfoActivity_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.PermissionUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.util_flower.imageUtil_flower.ImageConvert_flower;
import com.test.aoner.fanow.test.util_flower.imageUtil_flower.PhotoUtil_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.TitleView_flower;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class FaceUploadActivity_flower extends BaseInfoActivity_flower {

    private PreviewView previewView;
    private ImageButton photoIb;
    private View cameraView;

    private Runnable runnableWithCameraPermission = null;

    private ListenableFuture<ProcessCameraProvider> listenableFuture;
    private ImageCapture imageCapture;
    private Bitmap faceBm;

    private String faceImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_upload_flower);

        TitleView_flower titleView = findViewById(R.id.activity_face_upload_view_title);
        setBackBtn_flower(titleView.getBackIb());

        previewView = findViewById(R.id.activity_face_upload_preview);
        photoIb = findViewById(R.id.activity_face_upload_ib_photo);
        cameraView = findViewById(R.id.activity_face_upload_view_camera);

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                findViewById(R.id.activity_face_upload_view_operated),
                findViewById(R.id.activity_face_upload_iv_operated_logo),
                findViewById(R.id.activity_face_upload_tv_company_name));

        photoIb.setOnClickListener(v -> {

            HttpManager_flower.getInstance().saveUserBuriedPoint("SEFIE_INFO_START_TAKING_CLICK");

            runnWithCameraPermission(() -> {
                try {
                    faceImgUrl = "";
                    listenableFuture = ProcessCameraProvider.getInstance(this);
                    listenableFuture.addListener(() -> {
                        try {
                            ProcessCameraProvider provider = listenableFuture.get();

                            previewView.setVisibility(View.VISIBLE);

                            provider.unbindAll();

                            CameraSelector cameraSelector = new CameraSelector.Builder()
                                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                                    .build();

                            Preview preview = new Preview.Builder().build();
                            preview.setSurfaceProvider(previewView.getSurfaceProvider());

                            imageCapture = new ImageCapture.Builder()
                                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                                    .build();

                            photoIb.setImageBitmap(null);
                            cameraView.setVisibility(View.VISIBLE);

                            provider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
                        }catch (Exception e){
                            if (Constant_flower.DebugFlag) e.printStackTrace();
                        }
                    }, ContextCompat.getMainExecutor(this));
                }catch (Exception e){
                    if (Constant_flower.DebugFlag) e.printStackTrace();
                }
            });
        });

        cameraView.setOnClickListener(v -> {
            runnWithCameraPermission(() -> {

                if (!TextUtils.isEmpty(faceImgUrl)){
                    HttpManager_flower.getInstance().saveUserBuriedPoint("SEFIE_INFO_START_TAKING_CLICK");
                    try {
                        faceImgUrl = "";
                        listenableFuture = ProcessCameraProvider.getInstance(this);
                        listenableFuture.addListener(() -> {
                            try {
                                ProcessCameraProvider provider = listenableFuture.get();
                                previewView.setVisibility(View.VISIBLE);
                                provider.unbindAll();
                                CameraSelector cameraSelector = new CameraSelector.Builder()
                                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                                        .build();
                                Preview preview = new Preview.Builder().build();
                                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                                imageCapture = new ImageCapture.Builder()
                                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                                        .build();

                                photoIb.setImageBitmap(null);
                                cameraView.setVisibility(View.VISIBLE);
                                provider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, ContextCompat.getMainExecutor(this));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {
                    HttpManager_flower.getInstance().saveUserBuriedPoint("SEFIE_INFO_TAKE_PICTURE_CLICK");
                    faceImgUrl = PhotoUtil_flower.imageFilePath();
                    ImageCapture.OutputFileOptions options = new ImageCapture.OutputFileOptions.Builder(new File(faceImgUrl)).build();
                    imageCapture.takePicture(
                            options,
                            ContextCompat.getMainExecutor(this),
                            new ImageCapture.OnImageSavedCallback() {
                                @Override
                                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                                    try {
                                        Bitmap bitmap = BitmapFactory.decodeFile(faceImgUrl);
                                        faceBm = PhotoUtil_flower.rotateBitmap(bitmap, faceImgUrl);
                                        if (!TextUtils.isEmpty(faceImgUrl)) {
                                            ImageConvert_flower.startConvertImage(
                                                    new File(faceImgUrl),
                                                    (localPath, thumpImg) -> {
                                                        faceBm = thumpImg;
                                                        new Thread(() -> {
                                                            HttpManager_flower.getInstance().uploadImage(localPath);
                                                        }).start();
                                                    }
                                            );
                                        } else {
                                            faceImgUrl = null;
                                            Toast.makeText(FaceUploadActivity_flower.this,getString(R.string.upload_photo_fail_tips),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onError(@NonNull ImageCaptureException exception) {
                                    if (Constant_flower.DebugFlag) Log.e("Sefie_Info", "Save Fail" );
                                }
                            }
                    );
                }
            });
        });


        findViewById(R.id.activity_face_upload_btn_next).setOnClickListener(v -> {

            if (TextUtils.isEmpty(faceImgUrl)){
                HttpManager_flower.getInstance().saveUserBuriedPoint("SEFIE_INFO_START_TAKING_CLICK");
                runnWithCameraPermission(() -> {
                    try {
                        faceImgUrl = "";
                        listenableFuture = ProcessCameraProvider.getInstance(this);
                        listenableFuture.addListener(() -> {
                            try {
                                ProcessCameraProvider provider = listenableFuture.get();
                                previewView.setVisibility(View.VISIBLE);
                                provider.unbindAll();
                                CameraSelector cameraSelector = new CameraSelector.Builder()
                                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                                        .build();
                                Preview preview = new Preview.Builder().build();
                                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                                imageCapture = new ImageCapture.Builder()
                                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                                        .build();

                                photoIb.setImageBitmap(null);
                                cameraView.setVisibility(View.VISIBLE);
                                provider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, ContextCompat.getMainExecutor(this));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }else {
                HttpManager_flower.getInstance().saveUserBuriedPoint("SEFIE_INFO_SAVE_CLICK");
                HttpManager_flower.getInstance().uploadSefieInfo(faceImgUrl);
            }

        });

    }

    public void onImageUploadSuccess(String url){

        runOnUiThread(() -> {
            if (TextUtils.isEmpty(url)) {
                Toast.makeText(this, getString(R.string.upload_photo_fail_tips),Toast.LENGTH_SHORT).show();
                return;
            }
            faceImgUrl = url;
            photoIb.setImageBitmap(faceBm);
            previewView.setVisibility(View.GONE);
        });
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
            HttpManager_flower.getInstance().saveUserBuriedPoint("SEFIE_INFO_CAMERA_PERMISSION_AGREE_CLICK");
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