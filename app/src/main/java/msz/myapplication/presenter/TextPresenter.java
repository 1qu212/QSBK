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
import msz.myapplication.ui.fragment.TextFragment;
import okhttp3.Call;

/**
 * @Title: TextPresenter
 * @Package msz.myapplication.presenter
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/25 19:24
 */
public class TextPresenter extends BasePresenter {
    private TextFragment mTextFragment;
    private LatestModel mLatestModel=new LatestModel();
    private int curPage = 1;
    public TextPresenter(TextFragment textFragment){
        mTextFragment=textFragment;
    }

    public void loadNetworkData(){
        loadNetworkData(curPage);
    }

    public void reLoadMetworkData(){
        curPage=1;
        loadNetworkData(curPage);
    }

    public void loadNetworkData(int curPage){
        OkHttpUtils.get().url(String.format(Constant.URL_TEXT, curPage)).build().execute(new TextStringCallback());
    }

    class TextStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            mTextFragment.hideProgressBar();
            mTextFragment.quitRefresh();
            mTextFragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mTextFragment.getContext(), "网络异常，加载失败！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onResponse(String response, int id) {
            LatestEntity latestEntity=mLatestModel.praseJsonToLatestEntity(response);
            List<ItemsEntity> list_item_entity=latestEntity.getItems();
            mTextFragment.hideProgressBar();
            mTextFragment.showLatest(list_item_entity,curPage);
            mTextFragment.quitRefresh();
            curPage+=1;
        }
    }
}
