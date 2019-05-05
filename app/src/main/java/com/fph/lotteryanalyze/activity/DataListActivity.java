package com.fph.lotteryanalyze.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.base.BaseActivity;
import com.fph.lotteryanalyze.fragment.DataListFragment;

import butterknife.BindView;

public class DataListActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private String[] mTitles = {"双色球", "大乐透"};
    private String[] mTypes = {"ssq", "dlt"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_list;
    }

    @Override
    protected void init() {
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                DataListFragment dataListFragment = DataListFragment.getInstance(mTypes[i]);
                return dataListFragment;
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
    }
}
