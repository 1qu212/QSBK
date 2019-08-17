package msz.myapplication.presenter;

import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import msz.myapplication.Constant;
import msz.myapplication.entity.ItemsEntity;
import msz.myapplication.entity.LatestEntity;
import msz.myapplication.model.LatestModel;
import msz.myapplication.presenter.base.BasePresenter;
import msz.myapplication.ui.fragment.PicFragment;
import okhttp3.Call;

/**
 * @Title: PicPresenter
 * @Package msz.myapplication.presenter
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/25 19:23
 */
public class PicPresenter extends BasePresenter {
    private PicFragment mPicFragment;
    private LatestModel mLatestModel=new LatestModel();
    private int curPage = 1;
    public PicPresenter(PicFragment picFragment){
        mPicFragment=picFragment;
    }

    public void loadNetworkData(){
        loadNetworkData(curPage);
    }

    public void reLoadMetworkData(){
        curPage=1;
        loadNetworkData(curPage);
    }

    public void loadNetworkData(int curPage){
        OkHttpUtils.get().url(String.format(Constant.URL_PIC, curPage)).build().execute(new PicStringCallback());
    }

    public void loadHeaderNetworkData(){
        OkHttpUtils.get().url(String.format(Constant.URL_VIDEO, 1)).build().execute(new HeaderStringCallback());
    }

    class PicStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            mPicFragment.hideProgressBar();
            mPicFragment.quitRefresh();
            mPicFragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mPicFragment.getContext(), "网络异常，加载失败！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onResponse(String response, int id) {
            LatestEntity latestEntity=mLatestModel.praseJsonToLatestEntity(response);
            List<ItemsEntity> list_item_entity=latestEntity.getItems();
            mPicFragment.hideProgressBar();
            mPicFragment.showLatest(list_item_entity,curPage);
            mPicFragment.quitRefresh();
            curPage+=1;
        }
    }

    class HeaderStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
        }

        @Override
        public void onResponse(String response, int id) {
            LatestEntity latestEntity=mLatestModel.praseJsonToLatestEntity(response);
            List<ItemsEntity> header_list_item_entity=latestEntity.getItems();
            mPicFragment.showHeader(header_list_item_entity);
        }
    }
}
