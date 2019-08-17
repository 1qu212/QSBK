package msz.myapplication.util;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.IOException;

import msz.myapplication.application.MyApplication;
import okhttp3.Call;

/**
 * @Title: SaveImageUtil
 * @Package msz.myapplication.util
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/31 22:00
 */
public class SaveImageUtil {

    public static void save(final Uri uri, final boolean show) {
        String destFileDir=Environment.getExternalStorageDirectory().getPath() + "/photoview";
        File Dir=new File(destFileDir);
        if (!Dir.exists()){
            Dir.mkdirs();
        }
        File destFile=new File(Dir,(SaveImageUtil.UrlToFileName(uri.getPath())).split("\\.")[0]+".jpg");
        String destFileName=destFile.getName();
        if (destFile.exists()){
            destFile.delete();
        }
        try {
            destFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OkHttpUtils.get().url(uri.toString()).build().execute(new FileCallBack(destFileDir,destFileName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (show)
                    Toast.makeText(MyApplication.getContext(), "图片保存失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(File response, int id) {
                String file = response.getAbsolutePath();
                SharedPreferences.Editor editor = MyApplication.getInstance().getSharedPreferences().edit();
                editor.putString(uri.getPath(), file);
                editor.commit();
                if (show)
                    Toast.makeText(MyApplication.getContext(), "图片保存成功" + file, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 将URL中文件名分析出来 为防止冲突 取倒数第二个斜杠
     *
     * @param url
     * @return
     */
    public static String UrlToFileName(String url) {
        if (url.length() > 0) {
            String[] strs = url.split("/");
            return strs[strs.length - 2] + strs[strs.length - 1];
        } else {
            return "";
        }
    }
}
