package msz.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import msz.myapplication.R;
import msz.myapplication.adapter.LatestRecyclerAdapter;
import msz.myapplication.entity.ItemsEntity;
import msz.myapplication.presenter.LatestPresenter;
import msz.myapplication.presenter.base.BasePresenter;
import msz.myapplication.ui.fragment.base.BaseFragment;

/**
 * @Title: LatestFragment
 * @Package msz.myapplication.ui.fragment
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/25 18:49
 */
public class LatestFragment extends BaseFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private FrameLayout fragment_latest;
    private LatestPresenter mLatestPresenter;
    private RecyclerView recyclerView_fragment;
    private ProgressBar progressBar_latest;
    private FloatingActionButton fab_latest;
    private List<ItemsEntity> list_item_entity=new ArrayList<>();
    private LatestRecyclerAdapter mAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return new LatestPresenter(this);
    }

    public static LatestFragment getInstance() {
        LatestFragment fragment = new LatestFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment_latest = (FrameLayout) inflater.inflate(R.layout.fragment_latest,container,false);
        swipeRefreshLayout= (SwipeRefreshLayout) fragment_latest.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.colorPrimaryDark));
        recyclerView_fragment = (RecyclerView) fragment_latest.findViewById(R.id.recyclerView_fragment);
        progressBar_latest = (ProgressBar) fragment_latest.findViewById(R.id.progressBar_latest);
        fab_latest= (FloatingActionButton) fragment_latest.findViewById(R.id.fab_latest);
        return fragment_latest;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView_fragment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new LatestRecyclerAdapter(list_item_entity,getActivity());
        recyclerView_fragment.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLatestPresenter.reLoadMetworkData();
            }
        });
        recyclerView_fragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (ViewCompat.canScrollVertically(recyclerView, -1)){
                    fab_latest.setVisibility(View.VISIBLE);
                    if (!ViewCompat.canScrollVertically(recyclerView,1)){
                        mLatestPresenter.loadNetworkData();
                    }
                }else {
                    fab_latest.setVisibility(View.GONE);
                }
            }
        });
        fab_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView_fragment.scrollToPosition(0);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            mLatestPresenter= (LatestPresenter) createPresenter();
            mLatestPresenter.loadNetworkData();
        }
    }

    public void showLatest(List<ItemsEntity> list,int curPage){
        list_item_entity=list;
        if (curPage == 1) {
            mAdapter.reloadData(list_item_entity,true);
        } else {
            mAdapter.reloadData(list_item_entity,false);
        }
    }

    public void hideProgressBar(){
        progressBar_latest.setVisibility(View.GONE);
    }

    public void quitRefresh(){
        swipeRefreshLayout.setRefreshing(false);
    }
}
