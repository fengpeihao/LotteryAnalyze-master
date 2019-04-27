package com.fph.lotteryanalyze.widget;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.webkit.WebView;

public class CustomerWebView extends WebView {

    private StringBuilder mResult = new StringBuilder();

    public CustomerWebView(Context context) {
        super(context);
    }

    public CustomerWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new MyInputConnection(new InputView(this, false), true);
    }

    class MyInputConnection extends InputConnectionWrapper {

        /**
         * Initializes a wrapper.
         *
         * <p><b>Caveat:</b> Although the system can accept {@code (InputConnection) null} in some
         * places, you cannot emulate such a behavior by non-null {@link InputConnectionWrapper} that
         * has {@code null} in {@code target}.</p>
         *
         * @param target  the {@link InputConnection} to be proxied.
         * @param mutable set {@code true} to protect this object from being reconfigured to target
         *                another {@link InputConnection}.  Note that this is ignored while the target is {@code null}.
         */
        public MyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public Handler getHandler() {
            return new Handler();
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (KeyEvent.ACTION_DOWN == event.getAction()) {
//                if (event.isShiftPressed()) return false;

                if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    if (!TextUtils.isEmpty(mResult)) {
                        mResult.deleteCharAt(mResult.length() - 1);
                    }
                }
            }
            return super.sendKeyEvent(event);
        }
    }

    class InputView extends BaseInputConnection {
        public InputView(View targetView, boolean fullEditor) {
            super(targetView, fullEditor);
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            Log.e("截获的文字", text.toString());
            mResult.append(text);
            return super.commitText(text, newCursorPosition);
        }
    }

    public String getInputText() {
        if (mResult.length() >= 11)
            return mResult.substring(0, 11);
        else return mResult.toString();
    }
}
