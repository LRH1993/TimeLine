package com.lvr.timeline.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lvr.timeline.R;
import com.lvr.timeline.base.BaseActivity;
import com.lvr.timeline.home.fragment.CountDownFragment;
import com.lvr.timeline.home.fragment.TodayFragment;
import com.lvr.timeline.home.fragment.YesterdayFragment;
import com.lvr.timeline.utils.StatusBarSetting;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation)
    NavigationView mNavigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.fl_root)
    FrameLayout mFl_root;
    @BindView(R.id.rb_today)
    RadioButton mRbToday;
    @BindView(R.id.rb_time)
    RadioButton mRbTime;
    @BindView(R.id.rb_yesterday)
    RadioButton mRbYesterday;
    @BindView(R.id.rg_root)
    RadioGroup mRgRoot;
    private CountDownFragment mCountDownFragment;
    private TodayFragment mTodayFragment;
    private YesterdayFragment mYesterdayFragment;



    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        StatusBarSetting.setColorForDrawerLayout(this, mDrawerLayout, getResources().getColor(R.color.colorPrimary), StatusBarSetting.DEFAULT_STATUS_BAR_ALPHA);
        setToolBar();
        setNavigationView();
        setRadioGroup();

    }

    private void setRadioGroup() {
        mRbToday.setOnClickListener(this);
        mRbYesterday.setOnClickListener(this);
        mRbTime.setOnClickListener(this);
        mRbToday.setChecked(true);
        mRbToday.setTextColor(getResources().getColor(R.color.tab_selecet));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化Framgent，考虑到异常杀死的情况
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //异常情况下
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                if (fragment instanceof TodayFragment) {
                    mTodayFragment = (TodayFragment) fragment;
                }
                if (fragment instanceof YesterdayFragment) {
                    mYesterdayFragment = (YesterdayFragment) fragment;
                }
                if (fragment instanceof CountDownFragment) {
                    mCountDownFragment = (CountDownFragment) fragment;
                }
            }
        } else {
            //添加到FragmentManger，异常时，自动保存Fragment状态
            mTodayFragment = new TodayFragment();
            mYesterdayFragment = new YesterdayFragment();
            mCountDownFragment = new CountDownFragment();
            FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fl_root, mTodayFragment);
            transaction.add(R.id.fl_root, mYesterdayFragment);
            transaction.add(R.id.fl_root, mCountDownFragment);
            transaction.commit();
        }

        //显示与隐藏正确的Fragment
        getSupportFragmentManager().beginTransaction().
                show(mTodayFragment).
                hide(mYesterdayFragment).
                hide(mCountDownFragment).commit();
    }


    private void setNavigationView() {
        //NavigationView初始化
        mNavigation.setItemIconTintList(null);
        View headerView = mNavigation.getHeaderView(0);

    }

    private void setToolBar() {
        mToolbar.setTitle("今天");//设置标题
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        //菜单按钮可用
        actionBar.setHomeButtonEnabled(true);
        //回退按钮可用
        actionBar.setDisplayHomeAsUpEnabled(true);
        //将drawlayout与toolbar绑定在一起
        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        abdt.syncState();//初始化状态
        //设置drawlayout的监听事件 打开/关闭
        mDrawerLayout.setDrawerListener(abdt);
        //actionbar中的内容进行初始化
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.rb_today):{
                clearState();
                mRbToday.setChecked(true);
                mRbToday.setTextColor(getResources().getColor(R.color.tab_selecet));
                getSupportFragmentManager().beginTransaction().show(mTodayFragment)
                        .hide(mYesterdayFragment)
                        .hide(mCountDownFragment)
                        .commit();
                break;
            }
            case(R.id.rb_time):{
                clearState();
                mRbTime.setChecked(true);
                mRbTime.setTextColor(getResources().getColor(R.color.tab_selecet));
                getSupportFragmentManager().beginTransaction().show(mCountDownFragment)
                        .hide(mYesterdayFragment)
                        .hide(mTodayFragment)
                        .commit();
                break;
            }
            case(R.id.rb_yesterday):{
                clearState();
                mRbYesterday.setChecked(true);
                mRbYesterday.setTextColor(getResources().getColor(R.color.tab_selecet));
                getSupportFragmentManager().beginTransaction().show(mYesterdayFragment)
                        .hide(mTodayFragment)
                        .hide(mCountDownFragment)
                        .commit();
                break;
            }
        }
    }

    //清空之前的状态
    private void clearState() {
        mRgRoot.clearCheck();
        mRbTime.setTextColor(getResources().getColor(R.color.black));
        mRbToday.setTextColor(getResources().getColor(R.color.black));
        mRbYesterday.setTextColor(getResources().getColor(R.color.black));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolabr, menu);
        MenuItem item = menu.findItem(R.id.mn_it_edit);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }
}
