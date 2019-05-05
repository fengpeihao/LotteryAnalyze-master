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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fph.lotteryanalyze.base.BaseActivity;
import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.adapter.AnalyzeAdapter;
import com.fph.lotteryanalyze.adapter.NewestDataAdapter;
import com.fph.lotteryanalyze.bean.AnalyzeBean;
import com.fph.lotteryanalyze.contract.AnalyzeContract;
import com.fph.lotteryanalyze.db.DltLotteryDaoUtils;
import com.fph.lotteryanalyze.db.DltLotteryEntity;
import com.fph.lotteryanalyze.db.LotteryEntity;
import com.fph.lotteryanalyze.presenter.AnalyzePresenter;
import com.fph.lotteryanalyze.utils.DeviceUtils;
import com.fph.lotteryanalyze.widget.StatisticsView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by fengpeihao on 2018/5/10.
 */

public class DltAnalyzeActivity extends BaseActivity implements AnalyzeContract.View {
    private AnalyzePresenter mPresenter = new AnalyzePresenter(this);
    private NewestDataAdapter mAdapter = new NewestDataAdapter();
    private List<DltLotteryEntity> mDltList;
    private HashMap<Integer, Integer> redMap = new HashMap<>();
    private HashMap<Integer, Integer> blueMap = new HashMap<>();
    private StatisticsView mStatisticsView;
    private TextView mTvExists;
    private TextView mTvRedExists;
    private EditText mTvCode;
    private TextView mTvRandom;
    private TextView mTvStatistics;
    private TextView mTvAnalyze;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewAnalyze;
    private RecyclerView mRecyclerViewBlue;
    private ArrayList<AnalyzeBean> mAnalyzeList = new ArrayList<>();
    private ArrayList<AnalyzeBean> mBlueList = new ArrayList<>();
    private AnalyzeAdapter mAnalyzeAdapter;
    private String[] mBlueBalls = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    mStatisticsView.setType("dlt", redMap, blueMap);
                    cancelLoading();
                    break;
                case 2:
                    String s1 = msg.obj.toString() + "  没出现过";
                    SpannableString spannableString1 = new SpannableString(s1);
                    spannableString1.setSpan(new ForegroundColorSpan(Color.RED), 0, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString1.setSpan(new ForegroundColorSpan(Color.BLACK), 14, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString1.setSpan(new ForegroundColorSpan(Color.BLUE), 15, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString1.setSpan(new ForegroundColorSpan(Color.BLACK), 20, s1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTvExists.setText(spannableString1);
                    break;
                case 3:
                    String s2 = msg.obj.toString() + "  没出现过";
                    SpannableString spannableString2 = new SpannableString(s2);
                    spannableString2.setSpan(new ForegroundColorSpan(Color.RED), 0, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString2.setSpan(new ForegroundColorSpan(Color.BLACK), 14, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString2.setSpan(new ForegroundColorSpan(Color.BLUE), 15, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString2.setSpan(new ForegroundColorSpan(Color.BLACK), 20, s2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTvExists.setText(msg.obj.toString() + "  已出现过");
                    break;
                case 4:
                    cancelLoading();
                    mTvAnalyze.setVisibility(View.VISIBLE);
                    Collections.sort(mAnalyzeList, new Comparator<AnalyzeBean>() {
                        @Override
                        public int compare(AnalyzeBean bean1, AnalyzeBean bean2) {
                            int length1 = 0;
                            int length2 = 0;
                            if (bean1.getRedCode() != null) {
                                length1 = bean1.getRedCode().length();
                            }
                            if (bean2.getRedCode() != null) {
                                length2 = bean2.getRedCode().length();
                            }
                            return length1 - length2;
                        }
                    });
                    mAnalyzeAdapter.setData(mAnalyzeList);
                    break;
                case 5:
                    String s = msg.obj.toString() + "出现了 " + msg.arg1 + " 次";
                    SpannableString spannableString = new SpannableString(s);
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 15, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTvRedExists.setText(spannableString);
                    break;
            }
        }
    };
    private AnalyzeAdapter mBlueAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_analyze;
    }

    @Override
    protected void init() {
        initvViews();
        mDltList = new DltLotteryDaoUtils(this).queryAllLottery();
        if (mDltList == null) mDltList = new ArrayList<>();
        getBallNum();
        mPresenter.getNewestData("dlt");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mBlueAdapter = new AnalyzeAdapter();
        mRecyclerViewBlue.setLayoutManager(new GridLayoutManager(this, 8));
        mRecyclerViewBlue.setAdapter(mBlueAdapter);
        getBlueBallCounts();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mAnalyzeAdapter = new AnalyzeAdapter();
        mRecyclerViewAnalyze.setLayoutManager(gridLayoutManager);
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
        mTvRedExists = findViewById(R.id.tv_red_exists);
        mTvAnalyze = findViewById(R.id.tv_analyze);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerViewAnalyze = findViewById(R.id.recycler_view_analyze);
        mRecyclerViewBlue = findViewById(R.id.recycler_view_blue);
        mStatisticsView = findViewById(R.id.statistics_view);
        mTvStatistics = findViewById(R.id.tv_statistics);
        ViewGroup.LayoutParams layoutParams = mStatisticsView.getLayoutParams();
        layoutParams.height = DeviceUtils.dip2px(this, 150);
    }

    private void getBlueBallCounts() {
        int maxNum = 0;
        int minNum = 0;
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = i + 1; j < 12; j++) {
                String ball1 = mBlueBalls[i];
                String ball2 = mBlueBalls[j];
                int num = 0;
                for (DltLotteryEntity item : mDltList) {
                    String[] split = item.getOpencode().split("\\+");
                    if (split[1].contains(ball1) && split[1].contains(ball2)) {
                        num++;
                        sum++;
                    }
                }
                maxNum = Math.max(maxNum, num);
                minNum = minNum == 0 ? num : Math.min(minNum, num);
                mBlueList.add(new AnalyzeBean("", ball1 + "," + ball2, num));
            }
        }
        mBlueAdapter.setData(mBlueList);
        mTvStatistics.setText("出现最多次数为:" + maxNum + "次 " + "最少次数为:" + minNum + "次 " + "平均次数为:" + (sum / mBlueList.size()) + "次");
    }

    private void random() {
        int redMax = 35;
        int blueMax = 12;
        TreeSet<Integer> redBallSet = new TreeSet<>();
        while (redBallSet.size() < 5) {
            int redBall = (int) (Math.random() * redMax + 1);
            redBallSet.add(redBall);
        }
        TreeSet<Integer> blueBallSet = new TreeSet<>();
        while (blueBallSet.size() < 2) {
            int blueBall = (int) (Math.random() * blueMax + 1);
            blueBallSet.add(blueBall);
        }
        StringBuilder redBallBuilder = new StringBuilder();
        for (int item : redBallSet) {
            if (item < 10) {
                redBallBuilder.append("0");
            }
            redBallBuilder.append(item).append(",");
        }
        StringBuilder blueBallBuilder = new StringBuilder();
        for (int item : blueBallSet) {
            if (item < 10) {
                blueBallBuilder.append("0");
            }
            blueBallBuilder.append(item).append(",");
        }
        String code = redBallBuilder.toString().substring(0, redBallBuilder.length() - 1) + "+" + blueBallBuilder.toString().substring(0, blueBallBuilder.length() - 1);
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
                for (DltLotteryEntity item : mDltList) {
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
        String[] blueBalls = split[1].split(",");
        for (int i = 0; i < redBalls.length; i++) {
            for (int j = i + 1; j < redBalls.length; j++) {
                String redBall1 = redBalls[i];
                String redBall2 = redBalls[j];
                String s1 = redBall1 + "," + redBall2;
                int num1 = 0;
                int blueNum1 = 0;
                int blueNum2 = 0;
                int blueNum3 = 0;
                for (DltLotteryEntity item : mDltList) {
                    String redCode = item.getOpencode().split("\\+")[0];
                    String blueCode = item.getOpencode().split("\\+")[1];
                    if (redCode.contains(redBall1) && redCode.contains(redBall2)) {
                        num1++;
                        if (blueCode.contains(blueBalls[0])) {
                            blueNum1++;
                        }
                        if (blueCode.contains(blueBalls[1])) {
                            blueNum2++;
                        }
                        if (blueCode.contains(blueBalls[0]) && blueCode.contains(blueBalls[1])) {
                            blueNum3++;
                        }
                    }
                }
                mAnalyzeList.add(new AnalyzeBean(s1, num1));
                mAnalyzeList.add(new AnalyzeBean(s1, blueBalls[0], blueNum1));
                mAnalyzeList.add(new AnalyzeBean(s1, blueBalls[1], blueNum2));
                mAnalyzeList.add(new AnalyzeBean(s1, split[1], blueNum3));
                for (int k = j + 1; k < redBalls.length; k++) {
                    String redBall3 = redBalls[k];
                    String s2 = s1 + "," + redBall3;
                    int num2 = 0;
                    int blueNum12 = 0;
                    int blueNum22 = 0;
                    int blueNum32 = 0;
                    for (DltLotteryEntity item : mDltList) {
                        String redCode = item.getOpencode().split("\\+")[0];
                        String blueCode = item.getOpencode().split("\\+")[1];
                        if (redCode.contains(redBall1) && redCode.contains(redBall2) && redCode.contains(redBall3)) {
                            num2++;
                            if (blueCode.contains(blueBalls[0])) {
                                blueNum12++;
                            }
                            if (blueCode.contains(blueBalls[1])) {
                                blueNum22++;
                            }
                            if (blueCode.contains(blueBalls[0]) && blueCode.contains(blueBalls[1])) {
                                blueNum32++;
                            }
                        }
                    }
                    mAnalyzeList.add(new AnalyzeBean(s2, num2));
                    mAnalyzeList.add(new AnalyzeBean(s2, blueBalls[0], blueNum12));
                    mAnalyzeList.add(new AnalyzeBean(s2, blueBalls[1], blueNum22));
                    mAnalyzeList.add(new AnalyzeBean(s2, split[1], blueNum32));
                    for (int l = k + 1; l < redBalls.length; l++) {
                        String redBall4 = redBalls[l];
                        String s3 = s2 + "," + redBall4;
                        int num3 = 0;
                        int blueNum13 = 0;
                        int blueNum23 = 0;
                        int blueNum33 = 0;
                        for (DltLotteryEntity item : mDltList) {
                            String redCode = item.getOpencode().split("\\+")[0];
                            String blueCode = item.getOpencode().split("\\+")[1];
                            if (redCode.contains(redBall1) && redCode.contains(redBall2) && redCode.contains(redBall3) && redCode.contains(redBall4)) {
                                num3++;
                                if (blueCode.contains(blueBalls[0])) {
                                    blueNum13++;
                                }
                                if (blueCode.contains(blueBalls[1])) {
                                    blueNum23++;
                                }
                                if (blueCode.contains(blueBalls[0]) && blueCode.contains(blueBalls[1])) {
                                    blueNum33++;
                                }
                            }
                        }
                        mAnalyzeList.add(new AnalyzeBean(s3, num3));
                        mAnalyzeList.add(new AnalyzeBean(s3, blueBalls[0], blueNum13));
                        mAnalyzeList.add(new AnalyzeBean(s3, blueBalls[1], blueNum23));
                        mAnalyzeList.add(new AnalyzeBean(s3, split[1], blueNum33));
//                        for (int m = l + 1; m < redBalls.length; m++) {
//                            String redBall5 = redBalls[m];
//                            String s4 = s3 + "," + redBall5;
//                            int num4 = 0;
//                            int blueNum14 = 0;
//                            int blueNum24 = 0;
//                            int blueNum34 = 0;
//                            for (DltLotteryEntity item : mDltList) {
//                                String redCode = item.getOpencode().split("\\+")[0];
//                                String blueCode = item.getOpencode().split("\\+")[1];
//                                if (redCode.contains(redBall1) && redCode.contains(redBall2) && redCode.contains(redBall3) && redCode.contains(redBall4) && redCode.contains(redBall5)) {
//                                    num4++;
//                                    if (blueCode.contains(blueBalls[0])) {
//                                        blueNum14++;
//                                    }
//                                    if (blueCode.contains(blueBalls[1])) {
//                                        blueNum24++;
//                                    }
//                                    if (blueCode.contains(blueBalls[0]) && blueCode.contains(blueBalls[1])) {
//                                        blueNum34++;
//                                    }
//                                }
//                            }
//                            mAnalyzeList.add(new AnalyzeBean(s4, num4));
//                            mAnalyzeList.add(new AnalyzeBean(s4, blueBalls[0], blueNum14));
//                            mAnalyzeList.add(new AnalyzeBean(s4, blueBalls[1], blueNum24));
//                            mAnalyzeList.add(new AnalyzeBean(s4, split[1], blueNum34));
//                        }
                    }
                }
            }
        }
        int blueNum = 0;
        for (DltLotteryEntity item : mDltList) {
            String blueCode = item.getOpencode().split("\\+")[1];
            if (blueCode.contains(blueBalls[0]) && blueCode.contains(blueBalls[1])) {
                blueNum++;
            }
        }
        mAnalyzeList.add(new AnalyzeBean(null, split[1], blueNum));
        mHandler.sendEmptyMessage(4);
    }

    private void getBallNum() {
        showLoading();
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (DltLotteryEntity item : mDltList) {
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
