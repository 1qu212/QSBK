package msz.myapplication.presenter.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Title: BasePresenter
 * @Package msz.myapplication.presenter.base
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/23 22:45
 */
public abstract class BasePresenter<V> {
    /**@Field: (View接口类型的弱引用) */
    protected Reference<V> mViewRef;

    /**
     * 建立关联
     * @param view
     */
    public void attachView(V view){
        mViewRef=new WeakReference<V>(view);
    }

    /**
     * 解除关联
     */
    public void detachView(){
        if (mViewRef!=null){
            mViewRef.clear();
            mViewRef=null;
        }
    }

    /**
     * 判断是否与View建立了关联
     * @return
     */
    public boolean isViewAttached(){
        return mViewRef!=null&&mViewRef.get()!=null;
    }

    /**
     * 获取View
     * @return
     */
    protected V getView(){
        return mViewRef.get();
    }
}
