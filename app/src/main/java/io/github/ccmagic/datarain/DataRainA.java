package io.github.ccmagic.datarain;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by ccMagic on 2018/6/4.
 * Copyright ：
 * Version ：
 * Reference ：
 * Description ：
 */
public class DataRainA extends RelativeLayout {

    private static final String TAG = "DataRainA";
    /**
     * 最大字符长度：maxLength+3
     */
    private static int maxLength = 40;
    /**
     * 字体颜色
     */
    private int textColor;
    private Context mContext;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final Random random = new Random();
            final TextView textView = new TextView(mContext);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            //随机控制X轴上 的偏移量
            layoutParams.setMargins(random.nextInt(getMeasuredWidth()), 0, 0, 0);
            textView.setLayoutParams(layoutParams);
            textView.setTextColor(textColor);
            //
            int size = random.nextInt(10) + 4;
            //随机控制字体大小
            textView.setTextSize(size);
            if (Build.VERSION.SDK_INT >= 21) {
                textView.setZ(size);
            }
            //
            String str = getRandomString();
            //保证获取到最后一位字符位置
            int position0 = str.lastIndexOf("0");
            int position1 = str.lastIndexOf("1");
            int position = position0 > position1 ? position0 : position1;
            if (position > 0) {
                //最后一个字符高亮
                SpannableString s = new SpannableString(str);
                s.setSpan(new ForegroundColorSpan(Color.WHITE), position, position + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(s);
            } else {
                textView.setText(str);
            }

            //

            //
            addView(textView);

            textView.post(new Runnable() {
                @Override
                public void run() {
                    int lastValue = textView.getMeasuredHeight() > getMeasuredHeight() ? textView.getMeasuredHeight() : getMeasuredHeight();
                    final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "y", -textView.getMeasuredHeight(), lastValue);
                    objectAnimator.setDuration(random.nextInt(5000) + 5000);
                    objectAnimator.setInterpolator(new LinearInterpolator());
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        /**
                         * {@inheritDoc}
                         *
                         * @param animation
                         */
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //动画结束后移除textView
                            removeView(textView);
                            objectAnimator.cancel();
                        }
                    });
                    objectAnimator.start();
                    //无限循环
                    handler.postDelayed(runnable, 100);
                }
            });
        }
    };

    public DataRainA(Context context) {
        this(context, null);
    }

    public DataRainA(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataRainA(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setBackgroundColor(context.getResources().getColor(R.color.black));
        setPadding(10, 0, 10, 0);
        textColor = mContext.getResources().getColor(R.color.mediumAquamarine);
    }

    public void startRain() {
        //
        handler.postDelayed(runnable, 100);

    }

    /**
     * 获取随机字符
     */
    private String getRandomString() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        //每个字符长度不一样,3-33;
        for (int i = 0, size = random.nextInt(maxLength) + 3; i < size; i++) {
            stringBuilder.append(random.nextInt(2));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
