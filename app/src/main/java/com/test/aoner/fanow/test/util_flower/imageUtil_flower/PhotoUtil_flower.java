package com.test.aoner.fanow.test.util_flower.imageUtil_flower;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.exifinterface.media.ExifInterface;

import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;

import java.io.File;
import java.io.IOException;

public class PhotoUtil_flower {


    public static Bitmap changeToRoundBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final Paint paint = new Paint();
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);

        return output;
    }


    public static String imageFilePath() {
        String pictureId = System.currentTimeMillis() + "";
        String imgDir = sandboxFolderPath();
        return (imgDir + File.separator + pictureId + ".jpg");
    }

    public static String sandboxFolderPath() {
        String inAppFolder = BaseApplication_flower.getApplication_flower().getFilesDir().getAbsolutePath();
        inAppFolder += File.separator;
        inAppFolder += Constant_flower.PRODUCT;
        inAppFolder += "_PICS";

        File folderFile = new File(inAppFolder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }

        return inAppFolder;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, String imageFilePath) {
        try {

            ExifInterface exif = new ExifInterface(imageFilePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Matrix matrix = new Matrix();

            switch (orientation) {
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    matrix.setScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(180);
                    break;
                default:
                    return bitmap;
            }

            try {
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return rotatedBitmap;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
