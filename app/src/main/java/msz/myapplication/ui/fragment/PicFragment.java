package msz.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.kevin.wraprecyclerview.WrapAdapter;

import java.util.ArrayList;
import java.util.List;

import msz.myapplication.R;
import msz.myapplication.adapter.HeaderRecyclerAdapter;
import msz.myapplication.adapter.PicRecyclerAdapter;
import msz.myapplication.entity.ItemsEntity;
import msz.myapplication.presenter.PicPresenter;
import msz.myapplication.presenter.base.BasePresenter;
import msz.myapplication.ui.fragment.base.BaseFragment;

/**
 * @Title: PicFragment
 * @Package msz.myapplication.ui.fragment
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/25 18:49
 */
public class PicFragment extends BaseFragment{

    private SwipeRefreshLayout swipeRefreshLayout;
    private PicPresenter mPicPresenter;
    private RecyclerView recyclerView_fragment;
    private ProgressBar progressBar_latest;
    private FloatingActionButton fab_latest;
    private List<ItemsEntity> list_item_entity=new ArrayList<>();
    private PicRecyclerAdapter mAdapter;
    private FrameLayout fragment_pic;
    private WrapAdapter mWrapAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private List<ItemsEntity> header_list_item_entity=new ArrayList<>();
    private HeaderRecyclerAdapter headerRecyclerAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return new PicPresenter(this);
    }

    public static PicFragment getInstance() {
        PicFragment fragment = new PicFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment_pic = (FrameLayout) inflater.inflate(R.layout.fragment_pic,container,false);
        swipeRefreshLayout= (SwipeRefreshLayout) fragment_pic.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.colorPrimaryDark));
        recyclerView_fragment = (RecyclerView) fragment_pic.findViewById(R.id.recyclerView_fragment);
        progressBar_latest = (ProgressBar) fragment_pic.findViewById(R.id.progressBar_latest);
        fab_latest= (FloatingActionButton) fragment_pic.findViewById(R.id.fab_latest);
        return fragment_pic;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView_fragment.setLayoutManager(layoutManager);
        recyclerView_fragment.setPadding(0,0,0,0);
        mAdapter = new PicRecyclerAdapter(list_item_entity,getActivity());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPicPresenter.reLoadMetworkData();
            }
        });
        recyclerView_fragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (ViewCompat.canScrollVertically(recyclerView, -1)){
                    fab_latest.setVisibility(View.VISIBLE);
                    if (!ViewCompat.canScrollVertically(recyclerView,1)){
                        mPicPresenter.loadNetworkData();
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
        mWrapAdapter = new WrapAdapter(mAdapter);
        mWrapAdapter.adjustSpanSize(recyclerView_fragment);
        recyclerView_fragment.setAdapter(mWrapAdapter);
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.header,null);
        RecyclerView recyclerView_header= (RecyclerView) view.findViewById(R.id.recyclerView_header);
        recyclerView_header.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        headerRecyclerAdapter = new HeaderRecyclerAdapter(header_list_item_entity,getActivity());
        recyclerView_header.setAdapter(headerRecyclerAdapter);
        mWrapAdapter.addHeaderView(view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            mPicPresenter= (PicPresenter) createPresenter();
            mPicPresenter.loadHeaderNetworkData();
            mPicPresenter.loadNetworkData();
        }
    }

    public void showLatest(List<ItemsEntity> list,int curPage){
        list_item_entity=list;
        if (curPage == 1) {
            mAdapter.reloadData(list_item_entity,true);
            mWrapAdapter.notifyDataSetChanged();
        } else {
            mAdapter.reloadData(list_item_entity,false);
            mWrapAdapter.notifyDataSetChanged();
        }
    }

    public void hideProgressBar(){
        progressBar_latest.setVisibility(View.GONE);
    }

    public void quitRefresh(){
        swipeRefreshLayout.setRefreshing(false);
    }

    public void showHeader(List<ItemsEntity> list){
        header_list_item_entity=list;
        headerRecyclerAdapter.reloadData(header_list_item_entity,true);
    }
}
