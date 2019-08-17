package msz.myapplication.ui.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import msz.myapplication.presenter.base.BasePresenter;

/**
 * @Title: BaseActivity
 * @Package msz.myapplication.ui.activity.base
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/23 22:40
 */
public abstract class BaseActivity<V,P extends BasePresenter<V>> extends AppCompatActivity{
    /**@Field: (与Presenter建立关系) */
    protected P mPresenter;

    /**
     * 创建Presenter
     * @return
     */
    protected abstract P createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();
        mPresenter.attachView((V)this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
