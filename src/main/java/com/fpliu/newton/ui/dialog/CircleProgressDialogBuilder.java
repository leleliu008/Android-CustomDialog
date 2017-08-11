package com.fpliu.newton.ui.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 使用圆形进度条弹出框
 *
 * @author 792793182@qq.com 2016-04-23.
 */
public class CircleProgressDialogBuilder extends CustomDialogBuilder<CircleProgressDialogBuilder, LinearLayout, Object> {

    public CircleProgressDialogBuilder(Activity activity) {
        super(activity);
    }

    @Override
    public CustomDialog create() {
        LinearLayout layout = getView();
        if (layout == null) {
            Activity activity = getActivity();
            layout = new LinearLayout(activity);
            layout.setGravity(Gravity.CENTER);

            ImageView progressView = new ImageView(activity);
            progressView.setBackgroundResource(R.drawable.cd_progress_circle);
            progressView.startAnimation(UIUtil.getRotateAnimation());

            int width = UIUtil.dip2px(activity, 50);
            int margin = UIUtil.dip2px(activity, 15);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(width, width);
            lp1.gravity = Gravity.CENTER;
            lp1.topMargin = margin;
            lp1.leftMargin = margin;
            lp1.rightMargin = margin;
            lp1.bottomMargin = margin;

            layout.addView(progressView, lp1);

            setView(layout);
        }

        return super.create();
    }


    @Override
    protected Object getResult() {
        return null;
    }
}
