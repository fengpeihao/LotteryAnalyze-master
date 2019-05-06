package com.fph.lotteryanalyze.utils;

import android.content.Context;
import android.util.SparseArray;

import com.fph.lotteryanalyze.bean.FrequencyBean;
import com.fph.lotteryanalyze.db.LotteryDaoUtils;
import com.fph.lotteryanalyze.db.LotteryEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AnalyzeUtils {
    private List<FrequencyBean> mList = new ArrayList<>();
    private String[] ssqBlueNumbers = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"};
    private String[] ssqRedNumbers = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33"};
    private String mType;

    public AnalyzeUtils(String type) {
        mType = type;
        if ("blue".equals(type)) {
            initSsqBlueMap();
        } else if ("red".equals(type)) {
            initSsqRedMap();
        }
    }

    private void initSsqBlueMap() {
        for (String ssqBlueNumber : ssqBlueNumbers) {
            FrequencyBean frequencyBean = new FrequencyBean();
            frequencyBean.setNumber(ssqBlueNumber);
            frequencyBean.setContinuousCount(new SparseArray<Integer>());
            frequencyBean.setOmitCount(new SparseArray<Integer>());
            frequencyBean.setOmitPeriods(new ArrayList<Integer>());
            mList.add(frequencyBean);
        }
    }

    private void initSsqRedMap() {
        for (String ssqRedNumber : ssqRedNumbers) {
            FrequencyBean frequencyBean = new FrequencyBean();
            frequencyBean.setNumber(ssqRedNumber);
            frequencyBean.setContinuousCount(new SparseArray<Integer>());
            frequencyBean.setOmitCount(new SparseArray<Integer>());
            frequencyBean.setOmitPeriods(new ArrayList<Integer>());
            mList.add(frequencyBean);
        }
    }

    public List<FrequencyBean> getSsqAnalyzeData(Context context, int limit) {
        LotteryDaoUtils lotteryDaoUtils = new LotteryDaoUtils(context);
        List<LotteryEntity> lotteryEntities = lotteryDaoUtils.queryAllLottery();
        Collections.sort(lotteryEntities, new Comparator<LotteryEntity>() {
            @Override
            public int compare(LotteryEntity o1, LotteryEntity o2) {
                return o1.getOpentime().compareTo(o2.getOpentime());
            }
        });
        for (int i = 0; i < lotteryEntities.size() - limit; i++) {
            LotteryEntity lotteryEntity = lotteryEntities.get(i);
            String split = lotteryEntity.getOpencode().split("\\+")[1];
            if ("red".equals(mType)) {
                split = lotteryEntity.getOpencode().split("\\+")[0];
            }
            for (FrequencyBean frequencyBean : mList) {
                if (split.contains(frequencyBean.getNumber())) {
                    frequencyBean.setTotalCount(frequencyBean.getTotalCount() + 1);//出现的总次数
                    frequencyBean.setContinuous(frequencyBean.getContinuous() + 1);//出现的连续长度
                    frequencyBean.setMaxContinuous(Math.max(frequencyBean.getContinuous(), frequencyBean.getMaxContinuous()));//历史最大连续长度
                    //历史出现连续长度的次数
                    SparseArray<Integer> continuousCount = frequencyBean.getContinuousCount();
                    continuousCount.put(frequencyBean.getContinuous(), continuousCount.get(frequencyBean.getContinuous(), 0) + 1);
                    if (frequencyBean.getContinuous() > 1) {
                        continuousCount.put(frequencyBean.getContinuous() - 1, continuousCount.get(frequencyBean.getContinuous() - 1) - 1);
                    }
                    //历史遗漏长度次数
                    SparseArray<Integer> omitCount = frequencyBean.getOmitCount();
                    omitCount.put(frequencyBean.getCurrentOmit(), omitCount.get(frequencyBean.getCurrentOmit(), 0) + 1);
                    //历史遗漏期数
                    frequencyBean.getOmitPeriods().add(frequencyBean.getCurrentOmit());
                    frequencyBean.setCurrentOmit(0);//当前出现遗漏的期数
                } else {
                    frequencyBean.setCurrentOmit(frequencyBean.getCurrentOmit() + 1);//当前出现遗漏的期数
                    frequencyBean.setMaxOmit(Math.max(frequencyBean.getMaxOmit(), frequencyBean.getCurrentOmit()));//历史最大遗漏
                    frequencyBean.setContinuous(0);//出现的连续长度
                    frequencyBean.setTotalOmitCount(frequencyBean.getTotalOmitCount() + 1);
                }
                if (i == lotteryEntities.size() - limit - 1) {
                    //历史遗漏期数
                    frequencyBean.getOmitPeriods().add(frequencyBean.getCurrentOmit());
                }
            }
        }
        Collections.sort(mList, new Comparator<FrequencyBean>() {
            @Override
            public int compare(FrequencyBean o1, FrequencyBean o2) {
                return o1.getNumber().compareTo(o2.getNumber());
            }
        });
        return mList;
    }
}
