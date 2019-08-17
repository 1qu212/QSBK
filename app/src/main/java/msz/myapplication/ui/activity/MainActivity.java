package msz.myapplication.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.androidkun.xtablayout.XTabLayout;

import java.util.ArrayList;
import java.util.List;

import msz.myapplication.R;
import msz.myapplication.adapter.MyPagerAdapter;
import msz.myapplication.presenter.MainPresenter;
import msz.myapplication.presenter.base.BasePresenter;
import msz.myapplication.ui.activity.base.BaseActivity;
import msz.myapplication.ui.fragment.LatestFragment;
import msz.myapplication.ui.fragment.PicFragment;
import msz.myapplication.ui.fragment.TextFragment;

public class MainActivity extends BaseActivity {
    private Toolbar toolbar_main;
    private DrawerLayout drawerLayout_main;
    private NavigationView navigation_view;
    private ViewPager viewPager_main;
    private XTabLayout tabLayout_main;
    private List<Fragment> fragmentsList = new ArrayList<Fragment>();
    private FragmentTransaction ft;

    @Override
    protected BasePresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initView();
        initTabsAndViewPager();
    }

    private void initToolbar() {
        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);
        toolbar_main.setSubtitle("精简版");
        toolbar_main.setTitleTextColor(Color.WHITE);
        toolbar_main.setSubtitleTextColor(Color.YELLOW);
    }

    private void initView() {
        drawerLayout_main = (DrawerLayout) findViewById(R.id.activity_main);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        tabLayout_main = (XTabLayout) findViewById(R.id.tabLayout_main);
        viewPager_main = (ViewPager) findViewById(R.id.viewPager_main);
        fragmentsList.add(LatestFragment.getInstance());
        fragmentsList.add(TextFragment.getInstance());
        fragmentsList.add(PicFragment.getInstance());
        //设置Toolbar上的logo与抽屉同步，实现动画切换效果
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout_main, toolbar_main, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout_main.setDrawerListener(toggle);
        toggle.syncState();
        navigation_view.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        item.setChecked(true);
                        int tabIndex = 0;
                        switch (item.getItemId()) {
                            case R.id.latest:
                                tabIndex = 0;
                                break;
                            case R.id.text:
                                tabIndex = 1;
                                break;
                            case R.id.pic:
                                tabIndex = 2;
                                break;
                        }
                        //滑动ViewPager，实现TAB切换
                        viewPager_main.setCurrentItem(tabIndex);
                        //关闭抽屉
                        drawerLayout_main.closeDrawers();
                        return true;
                    }
                });
    }


    private void initTabsAndViewPager() {
        String[] arrTabTitles = getResources().getStringArray(R.array.arrTabTitles);
        PagerAdapter adapter = new MyPagerAdapter(
                getSupportFragmentManager(), fragmentsList, arrTabTitles);
        viewPager_main.setAdapter(adapter);
        viewPager_main.setOnPageChangeListener(new MyOnPageChangeListener());
        tabLayout_main.setupWithViewPager(viewPager_main);
        tabLayout_main.setTabsFromPagerAdapter(adapter);
    }

    private void hideAllFragment(){
        for (Fragment f:fragmentsList
             ) {
            if (f!=null){
                ft.hide(f);
            }
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ft = getSupportFragmentManager().beginTransaction();
            hideAllFragment();
            if (position==0){
                if (fragmentsList.get(position)!=null){
                    ft.show(fragmentsList.get(position));
                }else {
                    ft.replace(R.id.fragment_latest,fragmentsList.get(position));
                }
            }
            if (position==1){
                if (fragmentsList.get(position)!=null){
                    ft.show(fragmentsList.get(position));
                }else {
                    ft.replace(R.id.fragment_text,fragmentsList.get(position));
                }
            }
            if (position==2){
                if (fragmentsList.get(position)!=null){
                    ft.show(fragmentsList.get(position));
                }else {
                    ft.replace(R.id.fragment_pic,fragmentsList.get(position));
                }
            }
            ft.commit();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
