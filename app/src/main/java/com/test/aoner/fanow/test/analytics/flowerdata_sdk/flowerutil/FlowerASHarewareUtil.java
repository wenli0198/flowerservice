package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class FlowerASHarewareUtil {

    public static String getSensorList(Context context) {
        JSONArray jsonArray = new JSONArray();
        String sensorStr = "";
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
            for (int i = 0; i < sensors.size(); i++) {
                Sensor sensor = sensors.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", sensor.getType() + "");
                jsonObject.put("name", FlowerASUtil.safeString(sensor.getName()));
                jsonObject.put("version", sensor.getVersion() + "");
                jsonObject.put("maxRange", FlowerASUtil.safeString(String.valueOf(sensor.getMaximumRange())));
                jsonObject.put("vendor", FlowerASUtil.safeString(sensor.getVendor()));
                jsonObject.put("minDelay", FlowerASUtil.safeString(String.valueOf(sensor.getMinDelay())));
                jsonObject.put("power", FlowerASUtil.safeString(String.valueOf(sensor.getPower())));
                jsonObject.put("resolution", FlowerASUtil.safeString(String.valueOf(sensor.getResolution())));
                jsonArray.put(jsonObject);
            }
            sensors = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        sensorStr = jsonArray.toString();
        jsonArray = null;
        return sensorStr;
    }

    private static boolean checkRootMethod1() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {
                "/system/app/Superuser.apk",
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su"};

        for (String path : paths) {
            if (new File(path).exists()) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return in.readLine() != null;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    public static String getIsRoot(Context context) {
        if (isDeviceRooted()) {
            return "YES";
        } else {
            return "NO";
        }
    }

    public static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }

    public static String getKeyboard(Context context) {
        String keyboard = "";
        try {
            Configuration config = context.getResources().getConfiguration();
            keyboard = FlowerASUtil.safeString(String.valueOf(config.keyboard));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyboard;
    }

    public static String getIsSimulator(Context context) {
        boolean isMatch = (
                Build.BRAND.startsWith("generic") ||
                        Build.FINGERPRINT.startsWith("generic") ||
                        Build.FINGERPRINT.startsWith("unknown") ||
                        Build.HARDWARE.contains("goldfish") ||
                        Build.HARDWARE.contains("ranchu") ||
                        Build.MODEL.contains("google_sdk") ||
                        Build.MODEL.contains("Emulator") ||
                        Build.MODEL.contains("Android SDK built for x86") ||
                        Build.MANUFACTURER.contains("Genymotion") ||
                        (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
                        "google_sdk".equals(Build.PRODUCT));

        if (isMatch) {
            return "YES";
        } else {
            return "NO";
        }
    }

    public static Boolean notHasLightSensorManager(Context context) {
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            return null == sensor8;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isFeatures() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    public static boolean checkIsNotRealPhone() {
        String cpuInfo = readCpuInfo();
        return cpuInfo.contains("intel") || cpuInfo.contains("amd");
    }

    public static String readCpuInfo() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            StringBuilder sb = new StringBuilder();
            String readLine = "";
            String mUtf8Char = "UTF-8";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), mUtf8Char));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static final String[] known_pipes = {"/dev/socket/qemud", "/dev/qemu_pipe"};

    public static boolean checkPipes() {
        try {
            for (String pipes : known_pipes) {
                File qemu_socket = new File(pipes);
                if (qemu_socket.exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getMobileDbm(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }

        String dbmStr = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission")
            List<CellInfo> cellInfoList = tm.getAllCellInfo();
            if (null != cellInfoList) {
                for (CellInfo cellInfo : cellInfoList) {
                    if (cellInfo instanceof CellInfoGsm) {
                        CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                        int dbm = cellSignalStrengthGsm.getDbm();
                        dbmStr = FlowerASUtil.safeString(String.valueOf(dbm));
                    } else if (cellInfo instanceof CellInfoCdma) {
                        CellSignalStrengthCdma cellSignalStrengthCdma = ((CellInfoCdma) cellInfo).getCellSignalStrength();
                        int dbm = cellSignalStrengthCdma.getDbm();
                        dbmStr = FlowerASUtil.safeString(String.valueOf(dbm));
                    } else if (cellInfo instanceof CellInfoWcdma) {
                        CellSignalStrengthWcdma cellSignalStrengthWcdma = ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                        int dbm = cellSignalStrengthWcdma.getDbm();
                        dbmStr = FlowerASUtil.safeString(String.valueOf(dbm));
                    } else if (cellInfo instanceof CellInfoLte) {
                        CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte) cellInfo).getCellSignalStrength();
                        int dbm = cellSignalStrengthLte.getDbm();
                        dbmStr = FlowerASUtil.safeString(String.valueOf(dbm));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dbmStr;
    }

    public static String getFrontCameraPixels(Context paramContext) {
        int cameraId = hasFrontCamera(paramContext);
        return getCameraPixels(paramContext, cameraId);
    }

    public static String getBackCameraPixels(Context paramContext) {
        int cameraId = hasBackCamera(paramContext);
        return getCameraPixels(paramContext, cameraId);
    }

    public static int hasFrontCamera(Context paramContext) {
        int cid = -1;
        CameraManager cameraManager = (CameraManager) paramContext.getSystemService(Context.CAMERA_SERVICE);
        String[] cameraIds = {};
        try {
            cameraIds = cameraManager.getCameraIdList();
            for (String cID : cameraIds) {
                if (cID.equalsIgnoreCase(CameraCharacteristics.LENS_FACING_BACK + "")) {
                    cid = Integer.parseInt(cID);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cameraIds = null;
        return cid;
    }

    public static int hasBackCamera(Context paramContext) {
        int cid = -1;
        try {
            CameraManager cameraManager = (CameraManager) paramContext.getSystemService(Context.CAMERA_SERVICE);
            String[] cameraIds = cameraManager.getCameraIdList();
            for (String cID : cameraIds) {
                if (cID.equalsIgnoreCase(CameraCharacteristics.LENS_FACING_FRONT + "")) {
                    cid = Integer.parseInt(cID);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cid;
    }

    public static String getCameraPixels(Context paramContext, int paramInt) {
        String pixelValue = "0";
        if (paramInt == -1)
            return pixelValue;

        try {
            CameraManager cameraManager = (CameraManager) paramContext.getSystemService(Context.CAMERA_SERVICE);
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(paramInt + "");
            StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size[] sizes = streamConfigurationMap.getOutputSizes(ImageFormat.JPEG);
            if (sizes.length > 0) {
                Size fistSize = sizes[0];
                int gwidth = fistSize.getWidth();
                int gheight = fistSize.getHeight();
                int pixels = (gwidth * gheight / 10000);
                pixelValue = (pixels + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pixelValue;
    }

    public static int getMaxNumber(int[] paramArray) {
        int temp = 0;
        try {
            if (paramArray.length > 0) {
                temp = paramArray[0];
                for (int j : paramArray) {
                    if (temp < j) {
                        temp = j;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static String getPhySicalSize(Context paramContext) {
        String sizeStr = "";
        try {
            Point point = new Point();
            WindowManager wm = (WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getRealSize(point);
            DisplayMetrics dm = paramContext.getResources().getDisplayMetrics();
            double x = Math.pow(point.x / dm.xdpi, 2);
            double y = Math.pow(point.y / dm.ydpi, 2);
            double screenInches = Math.sqrt(x + y);
            sizeStr = String.valueOf(screenInches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sizeStr;
    }

}
