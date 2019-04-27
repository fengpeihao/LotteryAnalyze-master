package com.fph.lotteryanalyze.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.bean.FrequencyBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnalyzeHistoryAdapter extends RecyclerView.Adapter<AnalyzeHistoryAdapter.ViewHolder> {
    public AnalyzeHistoryAdapter(String type) {
        mType = type;
    }

    private List<FrequencyBean> mList;

    private String mType;

    public void setList(List<FrequencyBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_analyze_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FrequencyBean frequencyBean = mList.get(position);
        if("red".equals(mType)){
            holder.mTvNumber.setBackgroundResource(R.drawable.shape_circle_red);
        }
        holder.mTvNumber.setText(frequencyBean.getNumber());
        holder.mTvTotalCount.setText(String.valueOf(frequencyBean.getTotalCount()));
        holder.mTvMaxOmit.setText(String.valueOf(frequencyBean.getMaxOmit()));
        holder.mTvCurrentOmit.setText(String.valueOf(frequencyBean.getCurrentOmit()));
        holder.mTvContinuousCount.setText(frequencyBean.getContinuousCount().toString());
        holder.mTvOmitCount.setText(frequencyBean.getOmitCount().toString());
        holder.mTvOmitPeriods.setText(frequencyBean.getOmitPeriods().toString());
        holder.mTvTotalOmit.setText(String.valueOf(frequencyBean.getTotalOmitCount()));
        holder.mTvAveOmit.setText(String.valueOf(frequencyBean.getAveOmitCount()));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_number)
        TextView mTvNumber;
        @BindView(R.id.text_total_count)
        TextView mTextTotalCount;
        @BindView(R.id.tv_total_count)
        TextView mTvTotalCount;
        @BindView(R.id.text_max_omit)
        TextView mTextMaxOmit;
        @BindView(R.id.tv_max_omit)
        TextView mTvMaxOmit;
        @BindView(R.id.text_current_omit)
        TextView mTextCurrentOmit;
        @BindView(R.id.tv_current_omit)
        TextView mTvCurrentOmit;
        @BindView(R.id.text_continuous_count)
        TextView mTextContinuousCount;
        @BindView(R.id.tv_continuous_count)
        TextView mTvContinuousCount;
        @BindView(R.id.text_omit_count)
        TextView mTextOmitCount;
        @BindView(R.id.tv_omit_count)
        TextView mTvOmitCount;
        @BindView(R.id.text_omit_periods)
        TextView mTextOmitPeriods;
        @BindView(R.id.tv_omit_periods)
        TextView mTvOmitPeriods;
        @BindView(R.id.tv_total_omit)
        TextView mTvTotalOmit;
        @BindView(R.id.tv_ave_omit)
        TextView mTvAveOmit;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
