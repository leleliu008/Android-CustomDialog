package com.fpliu.newton.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 仿iOS的Dialog
 *
 * @author 792793182@qq.com 2016-04-23.
 */
public class CocoaStringDialogBuilder extends CocoaDialogBuilder<String> {

    private int textColor = Color.BLACK;

    private int textSize = 17;

    public CocoaStringDialogBuilder(Activity activity) {
        super(activity);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSizeSp(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public CustomDialog create() {
        Activity activity = getActivity();
        int padding = UIUtil.dip2px(activity, 15);
        setActionAdapter((actionData, position, parent) -> {
            TextView actionTv = new TextView(activity);
            actionTv.setText(actionData);
            actionTv.setGravity(Gravity.CENTER);
            actionTv.setTextColor(Color.BLACK);
            actionTv.setTextSize(textSize);
            actionTv.setTextColor(textColor);
            actionTv.setPadding(0, padding, 0, padding);
            actionTv.setBackgroundResource(R.drawable.cd_shape_rounded_rectangle_solid_white_conner_all);

            ActionListener<String> actionListener = actionItems.get(position).getActionListener();
            if (actionListener != null) {
                actionTv.setOnClickListener(view -> actionListener.onActionClicked(actionData));
            }
            return actionTv;
        });
        return super.create();
    }
}
