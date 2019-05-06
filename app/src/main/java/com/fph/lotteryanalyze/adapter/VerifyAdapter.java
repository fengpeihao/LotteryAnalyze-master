package com.fph.lotteryanalyze.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.bean.VerifyBean;
import com.fph.lotteryanalyze.widget.LotteryView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerifyAdapter extends RecyclerView.Adapter<VerifyAdapter.ViewHolder> {

    private List<VerifyBean> mList;

    public void setList(List<VerifyBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_verify, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        VerifyBean verifyBean = mList.get(i);
        viewHolder.mTvDate.setText(verifyBean.getExpect());
        viewHolder.mLotteryView.setData(verifyBean.getOpencode());
        viewHolder.mTvBeforehand.setText(verifyBean.getBeforehandCode());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.text_beforehand)
        TextView mTextBeforehand;
        @BindView(R.id.tv_beforehand)
        TextView mTvBeforehand;
        @BindView(R.id.text_opencode)
        TextView mTextOpencode;
        @BindView(R.id.lottery_view)
        LotteryView mLotteryView;
        @BindView(R.id.text_hit_rate)
        TextView mTextHitRate;
        @BindView(R.id.tv_hit_rate)
        TextView mTvHitRate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
