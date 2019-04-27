package com.fph.lotteryanalyze.activity;

import android.support.v4.app.FragmentManager;

import com.feilu.kotlindemo.base.BaseActivity;
import com.fph.lotteryanalyze.R;

public class OmitActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_omit;
    }

    @Override
    protected void init() {
        OmitFragment omitFragment = new OmitFragment();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.frame_layout,omitFragment).show(omitFragment).commit();
    }
}
