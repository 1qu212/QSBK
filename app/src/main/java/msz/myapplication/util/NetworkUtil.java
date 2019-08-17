package msz.myapplication.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import msz.myapplication.application.MyApplication;

/**
 * @Title: NetworkUtil
 * @Package msz.myapplication.util
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/29 1:31
 */
public class NetworkUtil {

    /**
     * 检查是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            boolean available = info.isAvailable();
            if (!available) {
                Toast.makeText(MyApplication.getInstance(), "当前无可用网络", Toast.LENGTH_SHORT).show();
            }
            return available;
        }
        return false;
    }

    /**
     * 检查是否是WIFI
     */
    public static boolean isWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
        }
        return false;
    }

    /**
     * 检查是否是移动网络
     */
    public static boolean isMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

    public static NetworkInfo getNetworkInfo(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
