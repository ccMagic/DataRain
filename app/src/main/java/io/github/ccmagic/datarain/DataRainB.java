package io.github.ccmagic.datarain;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ccMagic on 2018/6/4.
 * Copyright ：
 * Version ：
 * Reference ：
 * Description ：
 */
public class DataRainB extends LinearLayout {

    private Context mContext;
    private ArrayList<RainSingle> rainSingleArrayList = new ArrayList<>();


    public DataRainB(Context context) {
        this(context, null);
    }


    public DataRainB(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataRainB(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setBackgroundColor(context.getResources().getColor(R.color.black));
        setOrientation(HORIZONTAL);
        setPadding(10, 0, 10, 0);
        initRain();
    }

    private void initRain() {
        RainSingle.color = mContext.getResources().getColor(R.color.white);
        RainSingle.color2 = mContext.getResources().getColor(R.color.white);
        //
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //
        for (int k = 0, size = 25; k < size; k++) {
            RainSingle rainSingle = new RainSingle(mContext);
            //
            rainSingle.duration = RainSingle.random.nextInt(5000) + 5000;
            //
            rainSingleArrayList.add(rainSingle);
            Random random = new Random(k);
            //
            for (int j = 0; j < 100; j++) {
                rainSingle.s.append(random.nextInt(2));
                rainSingle.s.append("\n");
                j++;
            }
            SpannableString s = new SpannableString(rainSingle.s.toString());
            int position = RainSingle.random.nextInt(rainSingle.s.toString().length() - 1);
            s.setSpan(new ForegroundColorSpan(Color.GREEN), position, position + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            rainSingle.textView.setText(s);
            //
            for (int j = 0; j < 100; j++) {
                rainSingle.snext.append(random.nextInt(2));
                rainSingle.snext.append("\n");
                j++;
            }
            SpannableString snext = new SpannableString(rainSingle.snext.toString());
            int positionnext = RainSingle.random.nextInt(rainSingle.snext.toString().length() - 1);
            snext.setSpan(new ForegroundColorSpan(Color.GREEN), positionnext, positionnext + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            rainSingle.textViewNext.setText(snext);

            //
            RelativeLayout relativeLayout = new RelativeLayout(mContext);
            relativeLayout.setLayoutParams(layoutParams);
            relativeLayout.addView(rainSingle.textView);
            relativeLayout.addView(rainSingle.textViewNext);
            addView(relativeLayout);
            rainSingle.init();
        }
    }

    /**
     * 开始下雨啦
     */
    public void startRain() {
        post(new Runnable() {
            @Override
            public void run() {
                for (final RainSingle rainSingle : rainSingleArrayList) {
                    rainSingle.start();
                }
            }
        });
    }


    private static class RainSingle {

        private static final String TAG = "RainSingle";
        static Random random = new Random();
        static RelativeLayout.LayoutParams layoutParams;
        static int color;
        static int color2;

        static {
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10, 0, 10, 0);
        }

        ObjectAnimator objectAnimator1;
        ObjectAnimator objectAnimator2;
        TextView textView;
        TextView textViewNext;
        StringBuilder s = new StringBuilder();
        StringBuilder snext = new StringBuilder();
        long duration;
        private Handler handler = new Handler();

        RainSingle(Context context) {
            textView = new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setTextColor(color);
            textView.setTextSize(10);
            textView.setVisibility(INVISIBLE);
            //
            textViewNext = new TextView(context);
            textViewNext.setLayoutParams(layoutParams);
            textViewNext.setTextColor(color2);
            textViewNext.setTextSize(10);
            textViewNext.setVisibility(INVISIBLE);

        }

        void init() {
            textView.post(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "run textView.getMeasuredHeight(): " + textView.getMeasuredHeight());
                    Log.i(TAG, "run textViewNext.getMeasuredHeight(): " + textViewNext.getMeasuredHeight());
                    Log.i(TAG, "run duration: " + duration);
                    objectAnimator1 = ObjectAnimator.ofFloat(textView, "y", -textView.getMeasuredHeight(), textView.getMeasuredHeight());
                    objectAnimator1.setDuration(duration);
                    objectAnimator1.setInterpolator(new LinearInterpolator());
                    objectAnimator1.setRepeatMode(ValueAnimator.RESTART);
                    objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
                    //
                    objectAnimator2 = ObjectAnimator.ofFloat(textViewNext, "y", -textViewNext.getMeasuredHeight(), textViewNext.getMeasuredHeight());
                    objectAnimator2.setDuration(duration);
                    objectAnimator2.setInterpolator(new LinearInterpolator());
                    objectAnimator2.setRepeatMode(ValueAnimator.RESTART);
                    objectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
                }
            });
        }

        private void animator1() {
            textView.setVisibility(VISIBLE);
            objectAnimator1.start();
        }

        private void animator2() {
            textViewNext.setVisibility(VISIBLE);
            objectAnimator2.start();
        }

        private void start() {
            long delay = random.nextInt(1000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animator1();
                }
            }, delay);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animator2();
                }
            }, delay + duration / 2);
        }
    }
}
