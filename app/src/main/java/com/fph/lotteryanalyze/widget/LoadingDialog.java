package com.fph.lotteryanalyze.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fph.lotteryanalyze.R;


/**
 * Created by fengpeihao on 2017/6/10.
 */

public class LoadingDialog {
    private Builder mBuilder;

    public LoadingDialog(Builder builder) {
        mBuilder = builder;
    }

    public static class Builder {
        private final Dialog mDialog;
        private TextView mContentText;
        private final AnimationDrawable mAnimationDrawable;

        public Builder(Context context) {
            mDialog = new Dialog(context, R.style.loading_dialog);
            View view = View.inflate(context, R.layout.loading_dialog, null);
            ImageView spaceshipImage = view.findViewById(R.id.img);
            mContentText = view.findViewById(R.id.tipTextView);
            mAnimationDrawable = (AnimationDrawable) spaceshipImage.getDrawable();
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(true);
            mDialog.setContentView(view);
        }

        public Builder setContent(String str) {
            if(TextUtils.isEmpty(str)) {
                str = "数据加载中...";
            }
            mContentText.setText(str);
            mContentText.setVisibility(View.VISIBLE);
            return this;
        }

        public LoadingDialog build() {
            return new LoadingDialog(this);
        }
    }

    public boolean isShowing(){
        return mBuilder.mDialog.isShowing();
    }

    public void showDialog() {
        mBuilder.mDialog.show();
        mBuilder.mAnimationDrawable.start();
    }

    public void cancelDialog() {
        mBuilder.mAnimationDrawable.stop();
        mBuilder.mDialog.cancel();
    }
}
