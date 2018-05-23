package com.fpliu.newton.ui.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * 与UI相关的帮助类
 *
 * @author 792793182@qq.com 2015-06-12
 */
public final class UIUtil {

    private UIUtil() {
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5);
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度（单位：px）
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static ShapeDrawable getRoundRectShapeDrawable() {
        float r = 10;
        float[] outerR = new float[]{r, r, r, r, r, r, r, r};
        RoundRectShape rr = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rr);
        drawable.getPaint().setColor(Color.WHITE);
        return drawable;
    }

    public static RotateAnimation getRotateAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1200);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        return rotateAnimation;
    }

    public static AlphaAnimation getInAlphaAnimation() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setInterpolator(new AccelerateInterpolator());
        inAnimation.setFillAfter(true);
        inAnimation.setDuration(300);
        return inAnimation;
    }

    public static AlphaAnimation getOutAlphaAnimation() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setInterpolator(new DecelerateInterpolator());
        outAnimation.setDuration(300);
        return outAnimation;
    }
}
