package com.fph.lotteryanalyze.activity;

import com.feilu.kotlindemo.base.BaseActivity;
import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.utils.NumberUtils;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OmitLimitActivity extends BaseActivity {

    private EditText mEdtInput;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_omit_limit;
    }

    @Override
    protected void init() {
        mEdtInput = findViewById(R.id.edt_input);
        int limit = NumberUtils.getInteger(mEdtInput.getText().toString());
        Button btnConfirm = findViewById(R.id.btn_confirm);
        final OmitFragment omitFragment = OmitFragment.getInstace(limit);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.frame_layout,omitFragment).show(omitFragment).commit();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int limit = NumberUtils.getInteger(mEdtInput.getText().toString());
                omitFragment.refreshData(limit);
            }
        });
    }
}
