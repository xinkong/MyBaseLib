package com.grass.parent.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.UUID;

public class Tools {

    /**
     * 得到唯一字符串序列
     *
     * @return
     */
    public static synchronized String getNonce() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取android系统的版本
     *
     * @return
     */
    public static int getAndroidVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取当前程序的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取当前程序的版本号
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取基带版本号
     *
     * @return
     */
    public static String getBaseband() {
        try {

            Class cl = Class.forName("android.os.SystemProperties");

            Object invoker = cl.newInstance();

            Method m = cl.getMethod("get", new Class[]{String.class, String.class});

            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});

            return (String) result;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 获取sd卡剩余空间大小
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSdcardAvailableSpace() {

        if (!isSdcardAvailable()) {

            return -1;
        }

        File pathFile = Environment.getExternalStorageDirectory();

        StatFs statFs = new StatFs(pathFile.getPath());

        long block = statFs.getAvailableBlocksLong();

        long size = statFs.getBlockCountLong();

        return size * block;
    }

    /**
     * 检查sd是否可用
     *
     * @return
     */
    public static boolean isSdcardAvailable() {
        boolean exist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        return exist;
    }

    /**
     * 获取屏幕密度
     *
     * @param ctx
     * @return
     */
    public static float getDisplayDensity(Context ctx) {
        float desity = ctx.getResources().getDisplayMetrics().density;
        return desity;
    }


    /**
     * 得到设备名字
     */
    public static String getDeviceName() {
        String model = Build.MODEL;
        if (model == null || model.length() <= 0) {
            return "";
        } else {
            return model;
        }
    }

    /**
     * 得到品牌名字
     */
    public static String getBrandName() {
        String brand = Build.BRAND;
        if (brand == null || brand.length() <= 0) {
            return "";
        } else {
            return brand;
        }
    }

    /**
     * 获得手机厂商信息
     */
    public static String getManufacturer() {
        String brand = Build.MANUFACTURER;
        if (brand == null || brand.length() <= 0) {
            return "";
        } else {
            return brand;
        }
    }

    /**
     * 得到操作系统版本号
     */
    public static String getOSVersionName() {
        return Build.VERSION.RELEASE;
    }


    // 设备唯一标识
    public static String getDeviceId(Context context) {
        String deviceId = "";
        deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        return deviceId;
    }


    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String macAddress = null;
            WifiInfo wifiInfo = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
            macAddress = wifiInfo.getMacAddress();
            if (macAddress == null || macAddress.length() <= 0) {
                return "";
            } else {
                return macAddress;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取分辨率 按xxx_xxx格式输出
     *
     * @param context
     * @return
     */
    public static String getResolution(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return new StringBuilder().append(dm.widthPixels).append("*").append(dm.heightPixels).toString();
    }

    /**
     * 功能:MD5加密
     *
     * @param s
     */
    public static String getMD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5加密
     * @param str
     * @return
     */
    public static String MD5(String str)
    {
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }catch(Exception e)
        {
            Log.e("StringUtils", "md5 error:"+e.toString());
            //e.printStackTrace();
            return null;
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for(int i = 0; i < charArray.length; i++)
        {
            byteArray[i] = (byte)charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for( int i = 0; i < md5Bytes.length; i++)
        {
            int val = ((int)md5Bytes[i])&0xff;
            if(val < 16)
            {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    /**
     * 是否包含表情
     *
     * @param codePoint
     * @return 如果不包含 返回false,包含 则返回true
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static boolean filterEmoji(String source) {
        source = source + " ";

        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (!isEmojiCharacter(codePoint)) {// 如果不包含 则将字符append
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            } else {
            }
        }

        if (buf == null) {
            return false;// 如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return false;
            } else {
                return true;
            }
        }

    }

}
