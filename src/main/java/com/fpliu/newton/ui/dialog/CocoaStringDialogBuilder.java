package com.fpliu.newton.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import java.util.List;

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

    @Override
    public CocoaStringDialogBuilder setActions(String... items) {
        super.setActions(items);
        return this;
    }

    @Override
    public CocoaStringDialogBuilder setActions(List<String> items) {
        super.setActions(items);
        return this;
    }

    @Override
    public CocoaStringDialogBuilder addAction(String item, ActionListener<String> actionListener) {
        super.addAction(item, actionListener);
        return this;
    }

    @Override
    public CocoaStringDialogBuilder setActionListener(ActionListener<String> actionListener) {
        super.setActionListener(actionListener);
        return this;
    }

    public CocoaStringDialogBuilder setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public CocoaStringDialogBuilder setTextSizeSp(int textSize) {
        this.textSize = textSize;
        return this;
    }

    @Override
    public CustomDialog create() {
        Activity activity = getActivity();
        int padding = UIUtil.dip2px(activity, 15);
        setActionAdapter((position, actionData, parent) -> {
            TextView actionTv = new TextView(activity);
            actionTv.setText(actionData);
            actionTv.setGravity(Gravity.CENTER);
            actionTv.setTextColor(Color.BLACK);
            actionTv.setTextSize(textSize);
            actionTv.setTextColor(textColor);
            actionTv.setPadding(0, padding, 0, padding);
            return actionTv;
        });
        return super.create();
    }
}
