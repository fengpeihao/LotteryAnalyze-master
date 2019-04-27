package com.fph.lotteryanalyze.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.feilu.kotlindemo.base.BaseActivity;
import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.AnalyzeAdapter;
import com.fph.lotteryanalyze.adapter.NewestDataAdapter;
import com.fph.lotteryanalyze.bean.AnalyzeBean;
import com.fph.lotteryanalyze.contract.AnalyzeContract;
import com.fph.lotteryanalyze.db.LotteryDaoUtils;
import com.fph.lotteryanalyze.db.LotteryEntity;
import com.fph.lotteryanalyze.presenter.AnalyzePresenter;
import com.fph.lotteryanalyze.widget.StatisticsView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by fengpeihao on 2018/4/24.
 */

public class AnalyzeActivity extends BaseActivity implements AnalyzeContract.View {
    private AnalyzePresenter mPresenter = new AnalyzePresenter(this);
    private NewestDataAdapter mAdapter = new NewestDataAdapter();
    private List<LotteryEntity> mList;
    private HashMap<Integer, Integer> redMap = new HashMap<>();
    private HashMap<Integer, Integer> blueMap = new HashMap<>();
    private StatisticsView mStatisticsView;
    private TextView mTvExists;
    private TextView mTvRedExists;
    private TextView mTvAnalyze;
    private TextView mTvCode;
    private TextView mTvRandom;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewAnalyze;
    private ArrayList<AnalyzeBean> mAnalyzeList = new ArrayList<>();
    private AnalyzeAdapter mAnalyzeAdapter;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    mStatisticsView.setType("ssq", redMap, blueMap);
                    cancelLoading();
                    break;
                case 2:
                    String s1 = msg.obj.toString() + "  没出现过";
                    SpannableString spannableString1 = new SpannableString(s1);
                    spannableString1.setSpan(new ForegroundColorSpan(Color.RED), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString1.setSpan(new ForegroundColorSpan(Color.BLACK), 17, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString1.setSpan(new ForegroundColorSpan(Color.BLUE), 18, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString1.setSpan(new ForegroundColorSpan(Color.BLACK), 20, s1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTvExists.setText(spannableString1);
                    break;
                case 3:
                    String s2 = msg.obj.toString() + "  没出现过";
                    SpannableString spannableString2 = new SpannableString(s2);
                    spannableString2.setSpan(new ForegroundColorSpan(Color.RED), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString2.setSpan(new ForegroundColorSpan(Color.BLACK), 17, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString2.setSpan(new ForegroundColorSpan(Color.BLUE), 18, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString2.setSpan(new ForegroundColorSpan(Color.BLACK), 20, s2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTvExists.setText(msg.obj.toString() + "  已出现过");
                    break;
                case 4:
                    cancelLoading();
                    mTvAnalyze.setVisibility(View.VISIBLE);
                    Collections.sort(mAnalyzeList, new Comparator<AnalyzeBean>() {
                        @Override
                        public int compare(AnalyzeBean bean1, AnalyzeBean bean2) {
                            return bean1.getRedCode().length() - bean2.getRedCode().length();
                        }
                    });
                    mAnalyzeAdapter.setData(mAnalyzeList);
                    break;
                case 5:
                    String s = msg.obj.toString() + "出现了 " + msg.arg1 + " 次";
                    SpannableString spannableString = new SpannableString(s);
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 17, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTvRedExists.setText(spannableString);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_analyze;
    }

    @Override
    protected void init() {
        initvViews();
        mList = new LotteryDaoUtils(this).queryAllLottery();
        if (mList == null) mList = new ArrayList<>();
        getBallNum();
        mPresenter.getNewestData("ssq");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerViewAnalyze.setLayoutManager(gridLayoutManager);
        mAnalyzeAdapter = new AnalyzeAdapter();
        mRecyclerViewAnalyze.setAdapter(mAnalyzeAdapter);
        mTvRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                mAnalyzeList.clear();
                random();
            }
        });
        mTvAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                mAnalyzeList.clear();
                analyze(mTvCode.getText().toString());
            }
        });
    }

    private void initvViews() {
        mTvCode = findViewById(R.id.tv_code);
        mTvExists = findViewById(R.id.tv_exists);
        mTvRandom = findViewById(R.id.tv_random);
        mTvAnalyze = findViewById(R.id.tv_analyze);
        mTvRedExists = findViewById(R.id.tv_red_exists);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerViewAnalyze = findViewById(R.id.recycler_view_analyze);
        mStatisticsView = findViewById(R.id.statistics_view);
        TextView text_blue = findViewById(R.id.text_blue);
        text_blue.setVisibility(View.GONE);
    }

    private void random() {
        int redMax = 33;
        int blueMax = 16;
        TreeSet<Integer> redBallSet = new TreeSet<>();
        while (redBallSet.size() < 6) {
            int redBall = (int) (Math.random() * redMax + 1);
            redBallSet.add(redBall);
        }
        int blueBall = (int) (Math.random() * blueMax + 1);
        StringBuilder redBallBuilder = new StringBuilder();
        for (int item : redBallSet) {
            if (item < 10) {
                redBallBuilder.append("0");
            }
            redBallBuilder.append(item).append(",");
        }
        String code = redBallBuilder.toString().substring(0, redBallBuilder.length() - 1) + "+" + blueBall;
        if (blueBall < 10) {
            code = redBallBuilder.toString().substring(0, redBallBuilder.length() - 1) + "+0" + blueBall;
        }
        mTvCode.setText(code);
        analyze(code);
    }

    private void analyze(final String code) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                boolean isExists = false;
                String s = code.split("\\+")[0];
                int num = 0;
                for (LotteryEntity item : mList) {
                    if (code.equals(item.getOpencode())) {
                        isExists = true;
                    }
                    if (item.getOpencode().contains(s)) {
                        num++;
                    }
                }
                Message msg = new Message();
                msg.obj = code;
                if (isExists) {
                    msg.what = 3;
                } else {
                    msg.what = 2;
                }
                mHandler.sendMessage(msg);
                Message message = new Message();
                message.what = 5;
                message.obj = s;
                message.arg1 = num;
                mHandler.sendMessage(message);
                aa(code);
            }
        }.start();
    }

    private void aa(String code) {
        String[] split = code.split("\\+");
        String[] redBalls = split[0].split(",");
        String blueBall = split[1];
        for (int i = 0; i < redBalls.length; i++) {
            for (int j = i + 1; j < redBalls.length; j++) {
                String redBall1 = redBalls[i];
                String redBall2 = redBalls[j];
                String s1 = redBall1 + "," + redBall2;
                int num1 = 0;
                int blueNum1 = 0;
                for (LotteryEntity item : mList) {
                    String redCode = item.getOpencode().split("\\+")[0];
                    String blueCode = item.getOpencode().split("\\+")[1];
                    if (redCode.contains(redBall1) && redCode.contains(redBall2)) {
                        num1++;
                        if (blueCode.equals(blueBall)) {
                            blueNum1++;
                        }
                    }
                }
                AnalyzeBean bean1 = new AnalyzeBean();
                bean1.setRedCode(s1);
                bean1.setNum(num1);
                mAnalyzeList.add(bean1);
                AnalyzeBean bean11 = new AnalyzeBean();
                bean11.setRedCode(s1);
                bean11.setBlueCode(blueBall);
                bean11.setNum(blueNum1);
                mAnalyzeList.add(bean11);
                for (int k = j + 1; k < redBalls.length; k++) {
                    String redBall3 = redBalls[k];
                    String s2 = s1 + "," + redBall3;
                    int num2 = 0;
                    int blueNum2 = 0;
                    for (LotteryEntity item : mList) {
                        String redCode = item.getOpencode().split("\\+")[0];
                        String blueCode = item.getOpencode().split("\\+")[1];
                        if (redCode.contains(redBall1) && redCode.contains(redBall2) && redCode.contains(redBall3)) {
                            num2++;
                            if (blueCode.equals(blueBall)) {
                                blueNum2++;
                            }
                        }
                    }
                    AnalyzeBean bean2 = new AnalyzeBean();
                    bean2.setRedCode(s2);
                    bean2.setNum(num2);
                    mAnalyzeList.add(bean2);
                    AnalyzeBean bean12 = new AnalyzeBean();
                    bean12.setRedCode(s2);
                    bean12.setBlueCode(blueBall);
                    bean12.setNum(blueNum2);
                    mAnalyzeList.add(bean12);
                    for (int l = k + 1; l < redBalls.length; l++) {
                        String redBall4 = redBalls[l];
                        String s3 = s2 + "," + redBall4;
                        int num3 = 0;
                        int blueNum3 = 0;
                        for (LotteryEntity item : mList) {
                            String redCode = item.getOpencode().split("\\+")[0];
                            String blueCode = item.getOpencode().split("\\+")[1];
                            if (redCode.contains(redBall1) && redCode.contains(redBall2) && redCode.contains(redBall3) && redCode.contains(redBall4)) {
                                num3++;
                                if (blueCode.equals(blueBall)) {
                                    blueNum3++;
                                }
                            }
                        }
                        AnalyzeBean bean3 = new AnalyzeBean();
                        bean3.setRedCode(s3);
                        bean3.setNum(num3);
                        mAnalyzeList.add(bean3);
                        AnalyzeBean bean13 = new AnalyzeBean();
                        bean13.setRedCode(s3);
                        bean13.setBlueCode(blueBall);
                        bean13.setNum(blueNum3);
                        mAnalyzeList.add(bean13);
                        for (int m = l + 1; m < redBalls.length; m++) {
                            String redBall5 = redBalls[m];
                            String s4 = s3 + "," + redBall5;
                            int num4 = 0;
                            int blueNum4 = 0;
                            for (LotteryEntity item : mList) {
                                String redCode = item.getOpencode().split("\\+")[0];
                                String blueCode = item.getOpencode().split("\\+")[1];
                                if (redCode.contains(redBall1) && redCode.contains(redBall2) && redCode.contains(redBall3) && redCode.contains(redBall4) && redCode.contains(redBall5)) {
                                    num4++;
                                    if (blueCode.equals(blueBall)) {
                                        blueNum4++;
                                    }
                                }
                            }
                            AnalyzeBean bean4 = new AnalyzeBean();
                            bean4.setRedCode(s4);
                            bean4.setNum(num4);
                            mAnalyzeList.add(bean4);
                            AnalyzeBean bean14 = new AnalyzeBean();
                            bean14.setRedCode(s4);
                            bean14.setBlueCode(blueBall);
                            bean14.setNum(blueNum4);
                            mAnalyzeList.add(bean14);
                        }
                    }
                }
            }
        }
        mHandler.sendEmptyMessage(4);
    }

    private void getBallNum() {
        showLoading();
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (mList == null) mList = new ArrayList<>();
                for (LotteryEntity item : mList) {
                    String[] split = item.getOpencode().split("\\+");
                    String[] redBall = split[0].split(",");
                    String[] blueBall = split[1].split(",");
                    for (String i : redBall) {
                        int key = Integer.parseInt(i);
                        redMap.put(key, redMap.get(key) == null ? 1 : redMap.get(key) + 1);
                    }
                    for (String j : blueBall) {
                        int key = Integer.parseInt(j);
                        blueMap.put(key, blueMap.get(key) == null ? 1 : blueMap.get(key) + 1);
                    }
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    @Override
    public void getNewestData(@NotNull List<? extends LotteryEntity> data) {
        mAdapter.setList(data);
    }
}
