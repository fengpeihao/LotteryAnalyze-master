package com.fph.lotteryanalyze.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.base.BaseActivity;
import com.fph.lotteryanalyze.fragment.SsqAnalyzeFragment;

import butterknife.BindView;

public class SsqAnalyzeActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private String[] mTitles = {"红球", "蓝球"};
    private String[] mTypes = {"red", "blue"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_analyze_history;
    }

    @Override
    protected void init() {
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                SsqAnalyzeFragment sssqAnalyzeFragment = SsqAnalyzeFragment.getInstance(mTypes[i]);
                return sssqAnalyzeFragment;
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
