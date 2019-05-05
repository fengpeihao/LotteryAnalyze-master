package com.fph.lotteryanalyze.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.EditText;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.base.BaseActivity;
import com.fph.lotteryanalyze.fragment.OmitFragment;
import com.fph.lotteryanalyze.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OmitLimitActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.edt_input)
    EditText mEdtInput;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    private String[] mTitles = {"红球", "蓝球"};
    private String[] mTypes = {"red", "blue"};
    private List<OmitFragment> mList = new ArrayList<>();
    private int mCurrentPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_omit_limit;
    }

    @Override
    protected void init() {
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                OmitFragment omitFragment = OmitFragment.getInstance(0, mTypes[i]);
                mList.add(omitFragment);
                return omitFragment;
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
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mCurrentPosition = position;
            }
        });
    }

    @OnClick(R.id.btn_confirm)
    public void onBtnConfirmClicked() {
        int limit = NumberUtils.getInteger(mEdtInput.getText().toString());
        mList.get(mCurrentPosition).refreshData(limit);
    }
}
