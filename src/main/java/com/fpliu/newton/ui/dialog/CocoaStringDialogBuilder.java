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

    public CocoaStringDialogBuilder(Activity activity) {
        super(activity);
    }


    @Override
    public CustomDialog create() {
        Activity activity = getActivity();
        setActionAdapter((actionData, position, parent) -> {
            TextView actionTv = new TextView(activity);
            actionTv.setText(actionData);
            actionTv.setGravity(Gravity.CENTER);
            actionTv.setTextColor(Color.BLACK);
            actionTv.setBackgroundResource(R.drawable.cd_shape_rounded_rectangle_solid_white);

            ActionListener<String> actionListener = actionItems.get(position).getActionListener();
            if (actionListener != null) {
                actionTv.setOnClickListener(view -> actionListener.onActionClicked(actionData));
            }
            return actionTv;
        });
        return super.create();
    }
}
