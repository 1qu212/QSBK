package msz.myapplication.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import msz.myapplication.R;
import msz.myapplication.util.PixelUtil;
import msz.myapplication.util.SaveImageUtil;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @Title: PhotoViewActivity
 * @Package msz.myapplication.ui.activity
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/31 14:09
 */
public class PhotoViewActivity extends Activity {
    private PhotoView photoview;
    private int originViewLeft;
    private int originViewTop;
    private int originViewWidth;
    private int originViewHeight;
    private int deltaX;
    private int deltaY;
    private float scaleX;
    private float scaleY;
    private ImageView iv_download;
    private Uri uri;

    public static void launch(Activity activity, String url, View view) {
        Intent intent=new Intent(activity,PhotoViewActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        intent.putExtras(bundle);
        ActivityOptions options= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(activity,view,"photoview");
            activity.startActivity(intent,options.toBundle());
        }else {
            activity.startActivity(intent);
        }
    }

    public static void launch(Activity activity,String url,int left,int top,int width,int height){
        Intent intent=new Intent(activity,PhotoViewActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        bundle.putInt("left",left);
        bundle.putInt("top",top);
        bundle.putInt("width",width);
        bundle.putInt("height",height);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_photo_view);
        photoview = (PhotoView) findViewById(R.id.photoview);
        iv_download = (ImageView) findViewById(R.id.iv_download);
        originViewLeft = getIntent().getExtras().getInt("left");
        originViewTop = getIntent().getExtras().getInt("top");
        originViewWidth = getIntent().getExtras().getInt("width");
        originViewHeight = getIntent().getExtras().getInt("height");
        uri = Uri.parse(getIntent().getExtras().getString("url"));
        Picasso.with(this).load(uri).into(photoview);
        onUiReady();
        //photoview的单击返回事件
        photoview.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                onBackPressed();
            }
        });
        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImageUtil.save(uri,true);
            }
        });
    }

    private void onUiReady() {
        photoview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                // remove previous listener
                photoview.getViewTreeObserver().removeOnPreDrawListener(this);
                //准备场景
                prepareScene();
                //播放动画
                photoview.animate()
                        .setDuration(500)
                        .scaleX(1f)
                        .scaleY(1f)
                        .translationX(0)
                        .translationY(0)
                        .start();
                return true;
            }
        });
    }

    private void prepareScene() {
        Matrix matrix=photoview.getImageMatrix();
        Rect rect=photoview.getDrawable().getBounds();
        float[] values=new float[9];
        matrix.getValues(values);
        //缩放到起始view大小
        scaleX = (float) originViewWidth / (rect.width()*values[0]);
        scaleY = (float) originViewHeight / (rect.height()*values[4]);
        photoview.setScaleX(scaleX);
        photoview.setScaleY(scaleY);
        //移动到起始view位置
        WindowManager wm = this.getWindowManager();
        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int screenHeight = wm.getDefaultDisplay().getHeight();
        int imageViewHeight = wm.getDefaultDisplay().getHeight()- PixelUtil.getStatusBarHeight();
        int viewLeft=  (screenWidth-originViewWidth)/2;
//        int viewTop=  (screenHeight-originViewHeight)/2;
        int viewTop=  (imageViewHeight-originViewHeight)/2+PixelUtil.getStatusBarHeight();
        deltaX = originViewLeft - viewLeft;
        deltaY = originViewTop - viewTop;
        photoview.setTranslationX(deltaX);
        photoview.setTranslationY(deltaY);
    }

    @Override
    public void onBackPressed() {
        photoview.animate()
                .setDuration(500)
                .scaleX(scaleX)
                .scaleY(scaleY)
                .translationX(deltaX)
                .translationY(deltaY)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }).start();
    }
}
