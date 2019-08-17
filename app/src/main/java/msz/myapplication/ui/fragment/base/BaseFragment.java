package msz.myapplication.ui.fragment.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import msz.myapplication.presenter.base.BasePresenter;

/**
 * @Title: BaseFragment
 * @Package msz.myapplication.ui.fragment.base
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/24 0:31
 */
public abstract class BaseFragment<V,P extends BasePresenter<V>> extends Fragment {
    /**@Field: (与Presenter建立关系) */
    protected P mPresenter;

    /**
     * 创建Presenter
     * @return
     */
    protected abstract P createPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();
        mPresenter.attachView((V)this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
