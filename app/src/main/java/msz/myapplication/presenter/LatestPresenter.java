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
import msz.myapplication.ui.fragment.LatestFragment;
import okhttp3.Call;

/**
 * @Title: LatestPresenter
 * @Package msz.myapplication.presenter
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/25 19:23
 */
public class LatestPresenter extends BasePresenter {
    private LatestFragment mLatestFragment;
    private LatestModel mLatestModel=new LatestModel();
    private int curPage = 1;
    public LatestPresenter(LatestFragment latestFragment){
        mLatestFragment=latestFragment;
    }

    public void loadNetworkData(){
        loadNetworkData(curPage);
    }

    public void reLoadMetworkData(){
        curPage=1;
        loadNetworkData(curPage);
    }

    public void loadNetworkData(int curPage){
        OkHttpUtils.get().url(String.format(Constant.URL_LATEST, curPage)).build().execute(new LatestStringCallback());
    }

    class LatestStringCallback extends StringCallback{
        @Override
        public void onError(Call call, Exception e, int id) {
            mLatestFragment.hideProgressBar();
            mLatestFragment.quitRefresh();
            mLatestFragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mLatestFragment.getContext(), "网络异常，加载失败！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onResponse(String response, int id) {
            LatestEntity latestEntity=mLatestModel.praseJsonToLatestEntity(response);
            List<ItemsEntity> list_item_entity=latestEntity.getItems();
            mLatestFragment.hideProgressBar();
            mLatestFragment.showLatest(list_item_entity,curPage);
            mLatestFragment.quitRefresh();
            curPage+=1;
        }
    }
}
