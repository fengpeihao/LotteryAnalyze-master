package com.fph.lotteryanalyze.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.base.BaseActivity;
import com.fph.lotteryanalyze.contract.SplashContract;
import com.fph.lotteryanalyze.presenter.SplashPresenter;

import yanzhikai.textpath.SyncTextPathView;

/**
 * Created by fengpeihao on 2018/4/24.
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {

    private SplashPresenter mPresenter = new SplashPresenter(this);
    private boolean isSSQLoaded = false;
    private boolean isDLTLoaded = false;
    private boolean isAnimFinish = false;
    private SyncTextPathView mTv_dream_come_true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
        } else {
            mPresenter.getData();
            mPresenter.getDLTData();
        }
        mTv_dream_come_true = findViewById(R.id.tv_dream_come_true);
        mTv_dream_come_true.startAnimation(0, 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isAnimFinish = true;
                skipToMain();
            }
        }, 5000);
    }

    private synchronized void skipToMain() {
        if (isSSQLoaded && isDLTLoaded && isAnimFinish) {
            mTv_dream_come_true.stopAnimation();
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    public void ssqLoaded() {
        isSSQLoaded = true;
        skipToMain();
    }

    public void dltLoaded() {
        isDLTLoaded = true;
        skipToMain();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 222:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.getData();
                    mPresenter.getDLTData();
                }
                break;
            default:
                break;
        }
    }
}
