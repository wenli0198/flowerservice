package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

public class FlowerASStoreUtil {

    public static final String KEY_TOTAL = "total";
    public static final String KEY_USED = "used";
    public static final String KEY_AVAILABLE = "available";

    public static String getTotalInternalStoreSize(Context context) {
        String sizeStr = "0";
        try {
            JSONObject jsonObject = getInternalStoreSize(context);
            sizeStr = jsonObject.getString(KEY_TOTAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sizeStr;
    }

    public static String getAvailaInternalStoreSize(Context context) {
        String sizeStr = "0";
        try {
            JSONObject jsonObject = getInternalStoreSize(context);
            sizeStr = jsonObject.getString(KEY_AVAILABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sizeStr;
    }

    @SuppressLint({"DiscouragedPrivateApi", "UsableSpace"})
    public static JSONObject getInternalStoreSize(Context context) {
        float unit = 1024;
        int version = Build.VERSION.SDK_INT;
        if (version < Build.VERSION_CODES.M) {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            JSONObject storeObj = new JSONObject();
            try {
                Method getVolumeList = StorageManager.class.getDeclaredMethod("getVolumeList");
                StorageVolume[] volumeList = (StorageVolume[]) getVolumeList.invoke(storageManager);
                long totalSize = 0, availableSize = 0;
                if (volumeList != null) {
                    Method getPathFile = null;
                    for (StorageVolume volume : volumeList) {
                        if (getPathFile == null) {
                            getPathFile = volume.getClass().getDeclaredMethod("getPathFile");
                        }
                        File file = (File) getPathFile.invoke(volume);
                        if (file != null) {
                            totalSize += file.getTotalSpace();
                            availableSize += file.getUsableSpace();
                        }
                    }
                }

                String totalStore = getUnit(totalSize, unit);
                String usedStore = "0";
                String availableStore = getUnit(availableSize, unit);

                storeObj.put(KEY_TOTAL, totalStore);
                storeObj.put(KEY_AVAILABLE, availableStore);
                storeObj.put(KEY_USED, usedStore);
                return storeObj;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return storeObj;

        } else {
            JSONObject storeObj = new JSONObject();
            try {
                StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
                Method getVolumes = StorageManager.class.getDeclaredMethod("getVolumes");
                List<Object> getVolumeInfo = (List<Object>) getVolumes.invoke(storageManager);
                long total = 0L, used = 0L;
                if (getVolumeInfo != null) {
                    for (Object obj : getVolumeInfo) {
                        Field getType = obj.getClass().getField("type");
                        int type = getType.getInt(obj);
                        if (type == 1) {
                            long totalSize = 0L;

                            if (version >= Build.VERSION_CODES.O) {
                                unit = 1000;
                                Method getFsUuid = obj.getClass().getDeclaredMethod("getFsUuid");
                                String fsUuid = (String) getFsUuid.invoke(obj);
                                totalSize = getTotalSize(context, fsUuid);//8.0
                            }

                            Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                            boolean readable = (boolean) isMountedReadable.invoke(obj);
                            if (readable) {
                                Method file = obj.getClass().getDeclaredMethod("getPath");
                                File f = (File) file.invoke(obj);
                                if (f != null) {
                                    if (totalSize == 0) {
                                        totalSize = f.getTotalSpace();
                                    }
                                    used += totalSize - f.getFreeSpace();
                                    total += totalSize;
                                }
                            }
                        } else if (type == 0) {
                            Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                            boolean readable = (boolean) isMountedReadable.invoke(obj);
                            if (readable) {
                                Method file = obj.getClass().getDeclaredMethod("getPath");
                                File f = (File) file.invoke(obj);
                                if (f != null) {
                                    used += f.getTotalSpace() - f.getFreeSpace();
                                    total += f.getTotalSpace();
                                }
                            }
                        }
                    }
                }

                String totalStore = getUnit(total, unit);
                String usedStore = getUnit(used, unit);
                String availableStore = getUnit((total - used), unit);

                storeObj.put(KEY_TOTAL, totalStore);
                storeObj.put(KEY_AVAILABLE, availableStore);
                storeObj.put(KEY_USED, usedStore);
                return storeObj;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return storeObj;
        }
    }

    public static long getTotalSize(Context paramContext, String fsUuid) {
        try {
            UUID id;
            StorageStatsManager stats = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (fsUuid == null) {
                    id = StorageManager.UUID_DEFAULT;
                } else {
                    id = UUID.fromString(fsUuid);
                }
                stats = (StorageStatsManager) paramContext.getSystemService(Context.STORAGE_STATS_SERVICE);
                return stats.getTotalBytes(id);
            }
        } catch (NoSuchFieldError | NoClassDefFoundError | NullPointerException | IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private static String getUnit(float size, float unit) {
        String result = "";
        try {
            String[] units = {"B", "KB", "MB", "GB", "TB"};
            int index = 0;
            while (size > unit && index < 4) {
                size = size / unit;
                index++;
            }
            BigDecimal resultDec = new BigDecimal(size);
            resultDec = resultDec.setScale(2, RoundingMode.HALF_UP);
            result = resultDec.toPlainString() + units[index];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getRamCanUse(Context paramContext) {
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) paramContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
            return getUnit(memoryInfo.availMem, 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";
    }

    public static String getRamDidUsed(Context paramContext) {
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) paramContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
            long didUsed = (memoryInfo.totalMem - memoryInfo.availMem);
            return getUnit(didUsed, 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";
    }

    public static String getRamTotal(Context paramContext) {
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) paramContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
            return getUnit(memoryInfo.totalMem, 1024);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";
    }

    public static String getCashCanUse(Context paramContext) {
        try {
            File file = Environment.getDataDirectory();
            StatFs statFs = new StatFs(file.getPath());
            return getUnit(statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong(), 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";
    }

    public static String getCashDidUsed(Context paramContext) {
        try {
            File file = Environment.getDataDirectory();
            StatFs statFs = new StatFs(file.getPath());
            long total = (statFs.getBlockCountLong() * statFs.getBlockSizeLong());
            long avail = (statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong());
            long didUsed = (total - avail);
            return getUnit(didUsed, 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";
    }

    public static String getCashTotal(Context paramContext) {
        try {
            File file = Environment.getDataDirectory();
            StatFs statFs = new StatFs(file.getPath());
            return getUnit(statFs.getBlockCountLong() * statFs.getBlockSizeLong(), 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";
    }

    public static String getContainSD() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return "YES";
        }

        return "NO";
    }

    public static String getExtraSD(Context paramContext, int paramInt) {
        return "NO";
    }

    public static String getSDCardTotal(Context paramContext) {
        try {
            if (Environment.getExternalStorageState().equalsIgnoreCase("mounted")) {
                File file = Environment.getExternalStorageDirectory();
                StatFs statFs = new StatFs(file.getPath());
                long l = statFs.getBlockSizeLong();
                return getUnit(statFs.getBlockCountLong() * l, 1024);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0GB";
    }

}
