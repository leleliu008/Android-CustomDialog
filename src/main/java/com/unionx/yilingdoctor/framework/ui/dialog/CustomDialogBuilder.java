package com.unionx.yilingdoctor.framework.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fpliu.newton.framework.ui.dialog.R;

/**
 * CustomDialog的构造器
 *
 * @author 792793182@qq.com 2016-04-22.
 */
public abstract class CustomDialogBuilder<T extends CustomDialogBuilder<T, V, Result>, V extends View, Result> {

    private Activity activity;

    private CharSequence title;
    private CharSequence message;
    private V view;

    private CharSequence positiveButtonText;
    private CharSequence neutralButtonText;
    private CharSequence negativeButtonText;

    private OnClickListener<Result> positiveButtonClickListener;
    private OnClickListener<Result> neutralButtonClickListener;
    private OnClickListener<Result> negativeButtonClickListener;
    private OnClickListener<Result> closeButtonClickListener;

    private Animation inAnimation;
    private Animation outAnimation;

    private Drawable background;

    private boolean cancelable = true;
    private boolean needCloseButton = false;


    public CustomDialogBuilder(Activity activity) {
        this.activity = activity;
    }

    public T setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return (T) this;
    }

    public T setTitle(CharSequence title) {
        this.title = title;
        return (T) this;
    }

    public T setTitle(int title) {
        return setTitle(activity.getText(title));
    }

    public T setMessage(CharSequence message) {
        this.message = message;
        return (T) this;
    }

    public T setMessage(int message) {
        return setMessage(activity.getText(message));
    }

    public T setView(V view) {
        this.view = view;
        return (T) this;
    }

    public T setBackground(Drawable background) {
        this.background = background;
        return (T) this;
    }

    public T setPositiveButton(String positiveButtonText, OnClickListener<Result> listener) {
        this.positiveButtonText = positiveButtonText;
        this.positiveButtonClickListener = listener;
        return (T) this;
    }

    public T setPositiveButton(int positiveButtonText, OnClickListener<Result> listener) {
        return setPositiveButton((String) activity.getText(positiveButtonText), listener);
    }

    public T setNeutralButton(String neutralButtonText, OnClickListener<Result> listener) {
        this.neutralButtonText = neutralButtonText;
        this.neutralButtonClickListener = listener;
        return (T) this;
    }

    public T setNeutralButton(int neutralButtonText, OnClickListener<Result> listener) {
        return setNeutralButton((String) activity.getText(neutralButtonText), listener);
    }

    public T setNegativeButton(String negativeButtonText, OnClickListener<Result> listener) {
        this.negativeButtonText = negativeButtonText;
        this.negativeButtonClickListener = listener;
        return (T) this;
    }

    public T setNegativeButton(int negativeButtonText, OnClickListener<Result> listener) {
        return setNegativeButton((String) activity.getText(negativeButtonText), listener);
    }

    public T setInAnimation(Animation inAnimation) {
        this.inAnimation = inAnimation;
        return (T) this;
    }

    public T setOutAnimation(Animation outAnimation) {
        this.outAnimation = outAnimation;
        return (T) this;
    }

    public T setNeedCloseButton(boolean needCloseButton) {
        this.needCloseButton = needCloseButton;
        return (T) this;
    }

    public T setNeedCloseButton(boolean needCloseButton, OnClickListener listener) {
        this.needCloseButton = needCloseButton;
        if (needCloseButton) {
            this.closeButtonClickListener = listener;
        }
        return (T) this;
    }

    /**
     * 创建对话框中的内容
     */
    public CustomDialog create() {
        final CustomDialog dialog = new CustomDialog(activity);

        RelativeLayout parentLayout = new RelativeLayout(activity);
        parentLayout.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundDrawable(UIUtil.getRoundRectShapeDrawable());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        parentLayout.addView(layout, lp);

        if (needCloseButton) {
            lp.topMargin = UIUtil.dip2px(activity, 20);
            lp.rightMargin = UIUtil.dip2px(activity, 20);

            //关闭按钮
            ImageButton closeBtn = new ImageButton(activity);
            closeBtn.setBackgroundResource(R.drawable.btn_close_normal);
            closeBtn.setOnClickListener(v -> onCloseButtonClick(dialog));

            RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            parentLayout.addView(closeBtn, lp1);
        }

        int a = UIUtil.dip2px(activity, 10);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (!TextUtils.isEmpty(title)) {
            TextView titleTv = new TextView(activity);
            titleTv.setText(title);
            titleTv.setTextSize(16);
            titleTv.setTextColor(Color.parseColor("#575757"));
            titleTv.setGravity(Gravity.CENTER);
            titleTv.setPadding(0, a, 0, a);

            layout.addView(titleTv, lp2);
        }

        TextView messageTv = null;
        if (!TextUtils.isEmpty(message)) {
            messageTv = new TextView(activity);
            messageTv.setText(message);
            messageTv.setTextSize(18);
            messageTv.setTextColor(Color.parseColor("#797979"));
            messageTv.setGravity(Gravity.CENTER);

            if (TextUtils.isEmpty(title)) {
                messageTv.setPadding(0, 2 * a, 0, a);
            } else {
                messageTv.setPadding(0, a, 0, a);
            }

            layout.addView(messageTv, lp2);
        }

        if (view != null) {
            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(message)) {
                lp3.topMargin = 2 * a;
                lp3.bottomMargin = a;
            }
            lp3.leftMargin = a;
            lp3.rightMargin = a;
            layout.addView(view, lp3);
        }

        LinearLayout footer = new LinearLayout(activity);
        footer.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(footer, lp2);

        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        lp3.topMargin = a;
        lp3.leftMargin = a;
        lp3.rightMargin = a;
        lp3.bottomMargin = a;

        if (!TextUtils.isEmpty(positiveButtonText)) {
            Button okBtn = new Button(activity);
            okBtn.setText(positiveButtonText);
            okBtn.setTextSize(18);
            okBtn.setTextColor(Color.WHITE);
            okBtn.setBackgroundResource(R.drawable.state_list_rounded_rectangle_solid_yellow);
            okBtn.setOnClickListener(v -> onPositiveButtonClick(dialog));
            footer.addView(okBtn, lp3);
        }

        if (!TextUtils.isEmpty(negativeButtonText)) {
            Button cancelBtn = new Button(activity);
            cancelBtn.setText(negativeButtonText);
            cancelBtn.setTextSize(18);
            cancelBtn.setTextColor(Color.WHITE);
            cancelBtn.setBackgroundResource(R.drawable.state_list_rounded_rectangle_solid_blue);
            cancelBtn.setOnClickListener(v -> onNegativeButtonClick(dialog));

            footer.addView(cancelBtn, lp3);
        }

        if (!TextUtils.isEmpty(positiveButtonText) || !TextUtils.isEmpty(negativeButtonText)) {
            if (TextUtils.isEmpty(title) && messageTv != null) {
                messageTv.setPadding(0, 2 * a, 0, a);
            }
        }

        if (TextUtils.isEmpty(positiveButtonText)
                && TextUtils.isEmpty(negativeButtonText)
                && view == null
                && TextUtils.isEmpty(title)
                && messageTv != null) {
            messageTv.setPadding(0, 2 * a, 0, 2 * a);
        }

        dialog.setContentView(parentLayout);
        dialog.setCancelable(cancelable);

        if (inAnimation == null) {
            inAnimation = UIUtil.getInAlphaAnimation();
        }
        dialog.setInAnimation(inAnimation);

        if (outAnimation == null) {
            outAnimation = UIUtil.getOutAlphaAnimation();
        }
        dialog.setOutAnimation(outAnimation);

        if (background != null) {
            dialog.setWindowBackground(background);
        }

        // 设置为屏幕宽度的9/10
        dialog.setWindowWidth(UIUtil.getScreenWidth(activity) * 9 / 10);

        return dialog;
    }

    protected void onCloseButtonClick(CustomDialog dialog) {
        dialog.dismiss();
        if (closeButtonClickListener != null) {
            closeButtonClickListener.onClick(dialog, getResult());
        }
    }

    protected void onPositiveButtonClick(CustomDialog dialog) {
        dialog.dismiss();
        if (positiveButtonClickListener != null) {
            positiveButtonClickListener.onClick(dialog, getResult());
        }
    }

    protected void onNegativeButtonClick(CustomDialog dialog) {
        dialog.dismiss();
        if (negativeButtonClickListener != null) {
            negativeButtonClickListener.onClick(dialog, getResult());
        }
    }

    protected abstract Result getResult();

    protected final Activity getActivity() {
        return activity;
    }

    protected final V getView() {
        return view;
    }

    public interface OnClickListener<R> {
        void onClick(CustomDialog dialog, R result);
    }
}
