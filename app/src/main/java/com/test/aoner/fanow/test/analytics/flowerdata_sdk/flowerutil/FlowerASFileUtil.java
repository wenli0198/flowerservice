package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.IntDef;

import com.test.aoner.fanow.test.constant_flower.Constant_flower;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FlowerASFileUtil {

    public static File getFileByPath(final String filePath) {
        return TextUtils.isEmpty(filePath) ? null : new File(filePath);
    }

    public static boolean isFileExists(Context context, final String filePath) {
        File file = getFileByPath(filePath);
        if (file == null) return false;
        if (file.exists()) {
            return true;
        }
        return isFileExistsApi29(context, filePath);
    }

    private static boolean isFileExistsApi29(Context context, String filePath) {
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                Uri uri = Uri.parse(filePath);
                ContentResolver cr = context.getContentResolver();
                AssetFileDescriptor afd = cr.openAssetFileDescriptor(uri, "r");
                if (afd == null) return false;
                try {
                    afd.close();
                } catch (IOException ignore) {
                }
            } catch (FileNotFoundException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static long getFileLastModified(final File file) {
        if (file == null) return -1;
        return file.lastModified();
    }

    public static long getFileLastModified(final String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static String getFileName(final File file) {
        if (file == null) return "";
        return getFileName(file.getAbsolutePath());
    }

    public static String getFileName(final String filePath) {
        if (TextUtils.isEmpty(filePath)) return "";
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    public static String getSize(final String filePath) {
        return getSize(getFileByPath(filePath));
    }

    public static String getSize(final File file) {
        if (file == null) return "";
        if (file.isDirectory()) {
            return getDirSize(file);
        }
        return getFileSize(file);
    }

    private static String getDirSize(final File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    private static String getFileSize(final File file) {
        long len = getFileLength(file);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    public static long getLength(final String filePath) {
        return getLength(getFileByPath(filePath));
    }

    public static long getLength(final File file) {
        if (file == null) return 0;
        if (file.isDirectory()) {
            return getDirLength(file);
        }
        return getFileLength(file);
    }

    private static long getDirLength(final File dir) {
        if (!isDir(dir)) return 0;
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    public static long getFileLength(final String filePath) {
        boolean isURL = filePath.matches("[a-zA-z]+://[^\\s]*");
        if (isURL) {
            try {
                HttpsURLConnection conn = (HttpsURLConnection) new URL(filePath).openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity");
                conn.connect();
                if (conn.getResponseCode() == 200) {
                    return conn.getContentLength();
                }
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getFileLength(getFileByPath(filePath));
    }

    private static long getFileLength(final File file) {
        if (!isFile(file)) return -1;
        return file.length();
    }

    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteSize) {
        int precision = 3;
        if (byteSize < 0) {
            if (Constant_flower.DebugFlag) Log.e("byte2FitMemorySize","byteSize shouldn't be less than zero!");
            return "";
        } else if (byteSize < MemoryConstants.KB) {
            return String.format("%." + precision + "fB", (double) byteSize);
        } else if (byteSize < MemoryConstants.MB) {
            return String.format("%." + precision + "fKB", (double) byteSize / MemoryConstants.KB);
        } else if (byteSize < MemoryConstants.GB) {
            return String.format("%." + precision + "fMB", (double) byteSize / MemoryConstants.MB);
        } else {
            return String.format("%." + precision + "fGB", (double) byteSize / MemoryConstants.GB);
        }
    }

    public static final class MemoryConstants {
        public static final int BYTE = 1;
        public static final int KB   = 1024;
        public static final int MB   = 1048576;
        public static final int GB   = 1073741824;

        @IntDef({BYTE, KB, MB, GB})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Unit {
        }
    }


}
