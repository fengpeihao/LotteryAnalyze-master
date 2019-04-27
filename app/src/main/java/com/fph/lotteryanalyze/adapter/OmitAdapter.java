package com.fph.lotteryanalyze.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.bean.FrequencyBean;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OmitAdapter extends RecyclerView.Adapter<OmitAdapter.ViewHolder> {

    private List<FrequencyBean> mList;
    private String mType;

    public OmitAdapter(String type) {
        mType = type;
    }

    public void setList(List<FrequencyBean> list) {
        mList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ormit, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FrequencyBean frequencyBean = mList.get(i);
        //回补几率
        viewHolder.mTvAnaplerosisFrequency.setText(frequencyBean.getAnaplerosisFrequency().setScale(3, BigDecimal.ROUND_HALF_UP).toString());
        //出现次数
        viewHolder.mTvAriseCount.setText(String.valueOf(frequencyBean.getTotalCount()));
        //出现频率
        viewHolder.mTvAriseFrequency.setText(frequencyBean.getAriseFrequency().setScale(3, BigDecimal.ROUND_HALF_UP).toString());
        //平均遗漏
        viewHolder.mTvAveOmit.setText(frequencyBean.getAveOmitCount().setScale(3, BigDecimal.ROUND_HALF_UP).toString());
        //当前遗漏
        viewHolder.mTvCurrentOmit.setText(String.valueOf(frequencyBean.getCurrentOmit()));
        //预出几率
        viewHolder.mTvBeforehandFrequency.setText(frequencyBean.getBeforehandFrequency().setScale(3, BigDecimal.ROUND_HALF_UP).toString());
        //最大遗漏
        viewHolder.mTvMaxOmit.setText(String.valueOf(frequencyBean.getMaxOmit()));
        viewHolder.mTvNumber.setText(frequencyBean.getNumber());
        //上期遗漏
        viewHolder.mTvPreOmit.setText(frequencyBean.getOmitPeriods().get(frequencyBean.getOmitPeriods().size() - 2).toString());
        //理论出现次数
        int sumCount = frequencyBean.getTotalCount() + frequencyBean.getTotalOmitCount();
        int ballCount = "blue".equals(mType) ? 16 : 33;
        viewHolder.mTvTheoryCount.setText(String.valueOf(sumCount / ballCount));
        if (i % 2 == 0) {
            viewHolder.mLayoutRoot.setBackgroundColor(ContextCompat.getColor(viewHolder.mLayoutRoot.getContext(), R.color.color_white));
        } else {
            viewHolder.mLayoutRoot.setBackgroundColor(ContextCompat.getColor(viewHolder.mLayoutRoot.getContext(), R.color.color_ADD8E6));
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_number)
        TextView mTvNumber;
        @BindView(R.id.tv_arise_count)
        TextView mTvAriseCount;
        @BindView(R.id.tv_theory_count)
        TextView mTvTheoryCount;
        @BindView(R.id.tv_arise_frequency)
        TextView mTvAriseFrequency;
        @BindView(R.id.tv_ave_omit)
        TextView mTvAveOmit;
        @BindView(R.id.tv_max_omit)
        TextView mTvMaxOmit;
        @BindView(R.id.tv_pre_omit)
        TextView mTvPreOmit;
        @BindView(R.id.tv_current_omit)
        TextView mTvCurrentOmit;
        @BindView(R.id.tv_beforehand_frequency)
        TextView mTvBeforehandFrequency;
        @BindView(R.id.tv_anaplerosis_frequency)
        TextView mTvAnaplerosisFrequency;
        @BindView(R.id.layout_root)
        LinearLayout mLayoutRoot;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
