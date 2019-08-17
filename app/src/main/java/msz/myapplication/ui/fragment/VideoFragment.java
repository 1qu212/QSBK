package msz.myapplication.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import msz.myapplication.R;
import msz.myapplication.presenter.VideoPresenter;
import msz.myapplication.presenter.base.BasePresenter;
import msz.myapplication.ui.fragment.base.BaseFragment;

/**
 * @Title: VideoFragment
 * @Package msz.myapplication.ui.fragment
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/25 18:50
 */
public class VideoFragment extends BaseFragment{
    private RelativeLayout fragment_video;
    @Override
    protected BasePresenter createPresenter() {
        return new VideoPresenter();
    }

    public static VideoFragment getInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment_video = (RelativeLayout) inflater.inflate(R.layout.activity_video,container,false);
        return fragment_video;
    }
}
