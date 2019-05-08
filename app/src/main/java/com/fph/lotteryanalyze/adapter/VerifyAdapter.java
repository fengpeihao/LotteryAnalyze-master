package com.fph.lotteryanalyze.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fph.lotteryanalyze.R;
import com.fph.lotteryanalyze.db.BallEntity;
import com.fph.lotteryanalyze.db.OmitEntity;
import com.fph.lotteryanalyze.widget.LotteryView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerifyAdapter extends RecyclerView.Adapter<VerifyAdapter.ViewHolder> {

    private List<OmitEntity> mList;

    public void setList(List<OmitEntity> list) {
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
        OmitEntity omitEntity = mList.get(i);
        viewHolder.mTvDate.setText(omitEntity.getExpect());
        if(!TextUtils.isEmpty(omitEntity.getOpencode())) {
            viewHolder.mLotteryView.setData(omitEntity.getOpencode());
        }
        List<BallEntity> ballEntities = omitEntity.getBallEntities();
        Collections.sort(ballEntities, new Comparator<BallEntity>() {
            @Override
            public int compare(BallEntity o1, BallEntity o2) {
                return (o2.getBeforehandFrequency() + o2.getAnaplerosisFrequency()).compareTo(o1.getBeforehandFrequency() + o1.getAnaplerosisFrequency());
            }
        });
        String colorType = ballEntities.get(0).getColorType();
        if ("red".equals(colorType)) {
            viewHolder.mTvBeforehand.setTextColor(ContextCompat.getColor(viewHolder.mTvBeforehand.getContext(),android.R.color.holo_red_light));
        }else{
            viewHolder.mTvBeforehand.setTextColor(ContextCompat.getColor(viewHolder.mTvBeforehand.getContext(),android.R.color.holo_blue_light));
        }
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < ballEntities.size(); j++) {
            if("red".equals(ballEntities.get(0).getColorType())){
                if(j>=6){
                    break;
                }
            }else{
                if(j>=1){
                    break;
                }
            }
            builder.append(ballEntities.get(j).getNumber()).append(",");
        }
        viewHolder.mTvBeforehand.setText(builder.substring(0, builder.length() - 1));
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
