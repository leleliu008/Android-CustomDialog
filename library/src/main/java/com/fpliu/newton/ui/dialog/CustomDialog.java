package com.fpliu.newton.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自定义弹出框，可以设置背景的模糊效果
 *
 * @author 792793182@qq.com 2014-12-19
 */
public class CustomDialog extends Dialog {

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private enum State {
        ANIM_TO_SHOW,   //正在显示进入动画过程
        SHOW_ING,       //正在显示
        ANIM_TO_DISMISS,//正在显示退出动画过程
        DISMISS_ING     //不显示状态
    }

    private AtomicReference<State> currentState = new AtomicReference(State.DISMISS_ING);

    private int width;

    private int height;

    private int gravity = Gravity.CENTER;

    private int xOff;

    private int yOff;

    private float dimAmount = 0.6f;

    private long duration;

    private Animation inAnimation;

    private Animation outAnimation;

    private List<OnDismissListener> onDismissListeners = new CopyOnWriteArrayList<>();

    public CustomDialog(Activity activity) {
        this(activity, android.R.style.Theme_Dialog);
    }

    public CustomDialog(Activity activity, int theme) {
        super(activity, theme);

        //默认背景是透明的
        setWindowBackgroundColor(Color.TRANSPARENT);

        //去掉标题栏，标题栏自定义
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.setOnDismissListener(dialog -> {
            currentState.set(State.DISMISS_ING);
            for (OnDismissListener onDismissListener : onDismissListeners) {
                if (onDismissListener != null) {
                    onDismissListener.onDismiss(dialog);
                }
            }
            onDismissed();
        });
    }

    /**
     * 显示此Dialog
     *
     * @param anchorView 参照系，是一个View
     */
    public void show(View anchorView) {
        show(anchorView, 0, 0);
    }

    /**
     * 显示此Dialog
     *
     * @param anchorView 参照系，是一个View
     * @param xOff       相对于anchorView在X方向上的偏移量
     * @param yOff       相对于anchorView在Y方向上的偏移量
     */
    public void show(View anchorView, int xOff, int yOff) {
        show(anchorView, xOff, yOff, 0);
    }

    /**
     * 显示此Dialog
     *
     * @param anchorView 参照系，是一个View
     * @param duration   显示的时间（单位：ms）
     */
    public void show(View anchorView, int duration) {
        show(anchorView, 0, 0, duration);
    }

    /**
     * 显示此Dialog
     *
     * @param anchorView 参照系，是一个View
     * @param xOff       相对于anchorView在X方向上的偏移量
     * @param yOff       相对于anchorView在Y方向上的偏移量
     * @param duration   显示的时间（单位：ms）
     */
    public void show(View anchorView, int xOff, int yOff, int duration) {
        currentState.set(State.ANIM_TO_SHOW);

        int[] locationOfViewOnScreen = new int[2];
        // 获取此view在屏幕上的位置
        anchorView.getLocationOnScreen(locationOfViewOnScreen);

        //以屏幕左上角为参照点
        this.gravity = Gravity.LEFT | Gravity.TOP;
        this.xOff = locationOfViewOnScreen[0] + xOff;
        this.yOff = -locationOfViewOnScreen[1] + yOff;
        this.duration = duration;
        show();
    }

    /**
     * 显示此Dialog
     *
     * @param gravity 参照系，相对于屏幕的位置，参看{@link Gravity}
     * @param xOff    相对于gravity在X方向上的偏移量
     * @param yOff    相对于gravity在Y方向上的偏移量
     */
    public void show(int gravity, int xOff, int yOff) {
        currentState.set(State.ANIM_TO_SHOW);
        this.gravity = gravity;
        this.xOff = xOff;
        this.yOff = yOff;
        show();
    }

    /**
     * 显示此Dialog
     *
     * @param gravity  参照系，相对于屏幕的位置，参看{@link Gravity}
     * @param xOff     相对于gravity在X方向上的偏移量
     * @param yOff     相对于gravity在Y方向上的偏移量
     * @param duration 显示的时间（单位：ms）
     */
    public void show(int gravity, int xOff, int yOff, long duration) {
        currentState.set(State.ANIM_TO_SHOW);
        this.gravity = gravity;
        this.xOff = xOff;
        this.yOff = yOff;
        this.duration = duration;
        show();
    }

    @Override
    public void show() {
        currentState.set(State.ANIM_TO_SHOW);
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
            currentState.set(State.DISMISS_ING);
            return;
        }

        currentState.set(State.SHOW_ING);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        if (width != 0) {
            lp.width = width;
        }

        if (height != 0) {
            lp.height = height;
        }

        lp.dimAmount = dimAmount;

        if (gravity == Gravity.NO_GRAVITY) {
            gravity = Gravity.TOP | Gravity.LEFT;
        }

        lp.gravity = gravity;
        lp.x = xOff;
        lp.y = yOff;
        window.setAttributes(lp);

        if (duration <= 0) {
            return;
        }

        dismiss(duration);
    }

    /**
     * 关闭弹出框
     *
     * @param delayedTime  延迟关闭的时间,单位是ms
     * @param hasAnimation 是否有退出动画
     */
    public void dismiss(long delayedTime, final boolean hasAnimation) {
        mainHandler.postDelayed(() -> dismiss(hasAnimation), delayedTime);
    }

    /**
     * 关闭弹出框
     *
     * @param delayedTime 延迟关闭的时间,单位是ms
     */
    public void dismiss(long delayedTime) {
        mainHandler.postDelayed(() -> {
            try {
                dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, delayedTime);
    }

    /**
     * 关闭弹出框
     *
     * @param hasAnimation 是否有退出动画
     */
    public void dismiss(boolean hasAnimation) {
        if (currentState.compareAndSet(State.SHOW_ING, State.ANIM_TO_DISMISS)) {
            if (hasAnimation) {
                Animation outAnimation = getOutAnimation();
                if (outAnimation == null) {
                    CustomDialog.super.dismiss();
                } else {
                    outAnimation.setAnimationListener(new AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            CustomDialog.super.dismiss();
                        }
                    });
                    getWindow().getDecorView().findViewById(android.R.id.content).startAnimation(outAnimation);
                }
            } else {
                CustomDialog.super.dismiss();
            }
        }
    }

    @Override
    public void dismiss() {
        dismiss(false);
    }

    @Override
    public boolean isShowing() {
        State state = currentState.get();
        return state == State.ANIM_TO_SHOW || state == State.SHOW_ING;
    }

    public void setInAnimation(Animation inAnimation) {
        this.inAnimation = inAnimation;
    }

    public void setOutAnimation(Animation outAnimation) {
        this.outAnimation = outAnimation;
    }

    /**
     * show的平移动画
     */
    protected Animation getInAnimation() {
        if (inAnimation == null) {
            inAnimation = UIUtil.getInAlphaAnimation();
        }
        return inAnimation;
    }

    /**
     * dismiss的平移动画
     */
    protected Animation getOutAnimation() {
        if (outAnimation == null) {
            outAnimation = UIUtil.getOutAlphaAnimation();
        }
        return outAnimation;
    }

    /**
     * 设置Window的背景
     */
    public final void setWindowBackground(Drawable background) {
        getWindow().setBackgroundDrawable(background);
    }

    /**
     * 设置Window的背景颜色
     */
    public final void setWindowBackgroundColor(int bgColor) {
        getWindow().setBackgroundDrawable(new ColorDrawable(bgColor));
    }

    /**
     * 设置Window的宽度
     *
     * @param width 宽度
     */
    public final void setWindowWidth(int width) {
        this.width = width;
    }

    /**
     * 设置Window的高度
     *
     * @param height 高度
     */
    public final void setWindowHeight(int height) {
        this.height = height;
    }

    /**
     * 设置Window的模糊效果
     *
     * @param dimAmount 模糊程度
     */
    public final void setDim(float dimAmount) {
        this.dimAmount = dimAmount;
    }

    public final Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            } else {
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }

    public final void runOnUiThread(Runnable runnable) {
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            activity.runOnUiThread(runnable);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Animation inAnimation = getInAnimation();
        if (inAnimation != null) {
            getWindow().getDecorView().findViewById(android.R.id.content).startAnimation(inAnimation);
        }
    }

    protected void onDismissed() {

    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        if (listener != null) {
            onDismissListeners.add(listener);
        }
    }

    public void removeOnDismissListener(OnDismissListener listener) {
        if (listener != null) {
            onDismissListeners.remove(listener);
        }
    }

    public void clearAllOnDismissListener() {
        onDismissListeners.clear();
        super.setOnDismissListener(null);
    }
}
