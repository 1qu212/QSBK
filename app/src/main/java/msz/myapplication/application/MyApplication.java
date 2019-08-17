package msz.myapplication.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import msz.myapplication.Constant;
import msz.myapplication.okhttp.OkHttpClientUtils;
import msz.myapplication.picasso.MyDownLoader;

/**
 * @Title: MyApplication
 * @Package msz.myapplication.application
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/24 12:50
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static MyApplication mApplication;
    private SharedPreferences mSharedPreferences;
//    public DaoMaster.DevOpenHelper helper;
//    public SQLiteDatabase db;
//    public DaoMaster daoMaster;
//    public DaoSession daoSession;

    public static MyApplication getInstance(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        initOkHttpUtils();
        initPicasso();
//        initDataBases();
        mSharedPreferences=this.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences(){
        return mSharedPreferences;
    }

    public static Context getContext(){
        return mApplication;
    }

    /**
     * 可以在此处配置自己需要的配置，默认为鸿洋的配置
     */
    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = OkHttpClientUtils.getOkHttpSingletonInstance();
//        OkHttpClient okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
//        OkHttpUtils.initClient(okHttpClient);
//        Log.d(TAG, "---->initOkHttpUtils(): " + okHttpClient.toString());
    }

    /**
     * 初始化单例Picasso对象
     */
    private void initPicasso() {
        //配置Picasso
        Picasso picasso = new Picasso.Builder(this)
                //设置内存缓存大小，10M
                .memoryCache(new LruCache(10 << 20))
                //下载图片的格式，这样可以节省一半的内存
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                //配置下载器，这里用的是OkHttp，必需单独加OkHttp，同时设置了磁盘缓存的位置和大小
                //.downloader(new UrlConnectionDownloader())
//                .downloader(new OkHttpDownloader(getCacheDir(), 20 << 20))
                .downloader(new MyDownLoader(getCacheDir(), 20 << 20))
//                .downloader(new MyImageDownloader(getCacheDir(), 20 << 20))
                //设置图片左上角的标记
                //红色：代表从网络下载的图片
                //蓝色：代表从磁盘缓存加载的图片
                //绿色：代表从内存中加载的图片
//                .indicatorsEnabled(true)
                //.loggingEnabled(true)
                .build();
        Picasso.setSingletonInstance(picasso);
    }

//    /**
//     * 初始化数据库
//     */
//    private void initDataBases() {
//        helper=new DaoMaster.DevOpenHelper(this,"msz",null);
//        db=helper.getWritableDatabase();
//        daoMaster=new DaoMaster(db);
//        daoSession=daoMaster.newSession();
//    }
//
//    /**
//     * 返回所有表单管理类，可以通过它操作每一个具体的表单
//     * @return
//     */
//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
//
//    public SQLiteDatabase getDb() {
//        return db;
//    }
}
