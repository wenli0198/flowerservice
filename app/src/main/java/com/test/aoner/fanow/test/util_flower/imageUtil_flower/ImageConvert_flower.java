package com.test.aoner.fanow.test.util_flower.imageUtil_flower;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageConvert_flower {

    public static final String TAG = "FileChecker";

    public static String getImageDir() {
        String imgDir = BaseApplication_flower.getApplication_flower().getApplicationContext().getFilesDir() + "/" + Constant_flower.PRODUCT + "Pics";
        File dirFile = new File(imgDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return imgDir;
    }

    public static String getImageFilePath() {
        String pictureId = (System.currentTimeMillis() + "");
        String imgDir = getImageDir();
        return imgDir + "/" + pictureId + ".jpg";
    }

    public static String getImageAbsolutePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null) {
            return null;
        }

        if (Build.VERSION.SDK_INT >= 29) {
            return getBitmapFilePathFromURI(context, imageUri);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getImageDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            if (isGooglePhotosUri(imageUri)) {
                return imageUri.getLastPathSegment();
            }
            return getImageDataColumn(context, imageUri, null, null);
        } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getImageDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String getBitmapFilePathFromURI(Context context, Uri contentURI) {
        try {
            InputStream imgInputStream = context.getContentResolver().openInputStream(contentURI);
            if (imgInputStream == null) {
                return "";
            }

            Bitmap bitmap = BitmapFactory.decodeStream(imgInputStream);
            if (bitmap == null) {
                return "";
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            String outFilePath = getImageFilePath();
            File outFile = new File(outFilePath);

            FileOutputStream fos = new FileOutputStream(outFile);
            fos.write(stream.toByteArray());
            fos.flush();
            fos.close();
            stream.flush();
            stream.close();
            imgInputStream.close();

            return outFilePath;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static Bitmap getBitmapFromBytes(byte[] bytes) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap getBitmapFromFilePath(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static File getBitmapFile(Bitmap bitmap) throws IOException {
        if (bitmap == null) {
            return null;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String targetPath = ImageConvert_flower.getImageFilePath();
        File outFile = new File(targetPath);

        FileOutputStream fos = new FileOutputStream(outFile);
        fos.write(stream.toByteArray());
        fos.flush();
        fos.close();
        stream.flush();
        stream.close();

        return outFile;
    }

    public static String getFilePathFromBitmap(Bitmap bitmap) throws IOException {
        if (bitmap == null) {
            return null;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String targetPath = ImageConvert_flower.getImageFilePath();
        File outFile = new File(targetPath);

        FileOutputStream fos = new FileOutputStream(outFile);
        fos.write(stream.toByteArray());
        fos.flush();
        fos.close();
        stream.flush();
        stream.close();

        return targetPath;
    }

    public static String getFileExtendSuffix(File file) {
        try {
            FileInputStream input = new FileInputStream(file);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, options);
            return options.outMimeType.replace("image/", ".");
        } catch (Exception e) {
            return ".jpg";
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static void startConvertImage(File file, final OnImageConvertListener onOnImageConvertListener) {
        try {
            if (file != null) {
                String suffix = getFileExtendSuffix(file);
                String targetDir = ImageConvert_flower.getImageDir();
                String targetPath = targetDir + "/" +
                        System.currentTimeMillis() +
                        (TextUtils.isEmpty(suffix) ? ".jpg" : suffix);

                File outFile = new File(targetPath);
                File result = ImageChecker_flower.SINGLE.needCompress(300, file.getAbsolutePath()) ?
                        new ImageCompress_flower(file, outFile, false).compress() : file;

                String localPath = result.getAbsolutePath();
                Bitmap thumpBitmap = getBitmapFromFilePath(localPath);

                if (onOnImageConvertListener != null) {
                    onOnImageConvertListener.onSuccess(localPath, thumpBitmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnImageConvertListener {

        void onSuccess(String localPath, Bitmap thumpImg);
    }
}
