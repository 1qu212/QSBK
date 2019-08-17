package msz.myapplication.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import msz.myapplication.R;
import msz.myapplication.presenter.VideoPresenter;
import msz.myapplication.ui.activity.base.BaseActivity;

/**
 * @Title: VideoActivity
 * @Package msz.myapplication.ui.activity
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/29 15:43
 */
public class VideoActivity extends BaseActivity {

    private VideoView videoView;

    @Override
    protected VideoPresenter createPresenter() {
        return new VideoPresenter();
    }

    public static void launch(Activity activity, String url) {
        Intent intent=new Intent(activity,VideoActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = (VideoView) findViewById(R.id.video_view);
        Uri uri=Uri.parse(getIntent().getExtras().getString("url"));
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
    }
}
