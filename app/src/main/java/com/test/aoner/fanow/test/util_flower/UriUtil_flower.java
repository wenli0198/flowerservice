package com.test.aoner.fanow.test.util_flower;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UriUtil_flower {

    public static String getFilePathFromUri(Context context, Uri uri){
        File rootDataDir = context .getExternalFilesDir(null);
        String fileName = getFileName(uri);
        if (!TextUtils.isEmpty(fileName)){
            File copyFile = new File(rootDataDir+File.separator+fileName);
            copyFile(context,uri,copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri){
        if (uri==null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf("/");
        if (cut != -1){
            fileName = path.substring(cut+1);
        }
        return fileName;
    }

    public static void copyFile(Context context,Uri srcUri,File dstFile){
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copyStream(inputStream,outputStream);
            inputStream.close();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int copyStream(InputStream inputStream,OutputStream outputStream) throws IOException{
        final int BUFFER_SIZE = 1024*2;
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream in = new BufferedInputStream(inputStream,BUFFER_SIZE);
        int count = 0,n=0;
        try {
            while ((n=in.read(buffer,0,BUFFER_SIZE))!=-1){
                outputStream.write(buffer,0,n);
                count += n;
            }
            outputStream.flush();
        }finally {
            outputStream.close();
            in.close();
        }
        return count;
    }
}
