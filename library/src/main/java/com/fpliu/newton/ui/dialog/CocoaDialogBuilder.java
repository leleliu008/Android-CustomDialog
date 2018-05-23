package com.fpliu.newton.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 仿iOS的Dialog
 *
 * @author 792793182@qq.com 2016-04-23.
 */
public class CocoaDialogBuilder<T> extends CustomDialogBuilder<CocoaDialogBuilder<T>, LinearLayout, T> {

    protected List<Action<T>> actionItems;

    private ActionListener<T> actionListener;

    private ActionAdapter<T> actionAdapter;

    private T selectedItem;

    private CustomDialog dialog;

    public CocoaDialogBuilder(Activity activity) {
        super(activity);
    }

    public CocoaDialogBuilder setActions(T... items) {
        setActions(Arrays.asList(items));
        return this;
    }

    public CocoaDialogBuilder setActions(List<T> items) {
        if (actionItems == null) {
            actionItems = new ArrayList<>();
        }
        for (int i = 0; i < items.size(); i++) {
            actionItems.add(new Action<T>(items.get(i), null));
        }
        return this;
    }

    public CocoaDialogBuilder setActionListener(ActionListener<T> actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    public CocoaDialogBuilder addAction(T item, ActionListener<T> actionListener) {
        if (actionItems == null) {
            actionItems = new ArrayList<>();
        }
        actionItems.add(new Action<>(item, actionListener));
        return this;
    }

    public CocoaDialogBuilder setActionAdapter(ActionAdapter<T> actionAdapter) {
        this.actionAdapter = actionAdapter;
        return this;
    }

    @Override
    public CustomDialog create() {
        Activity activity = getActivity();
        int padding = UIUtil.dip2px(activity, 15);
        LinearLayout linearLayout = getView();
        if (linearLayout == null) {
            linearLayout = new LinearLayout(activity);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(padding, 0, padding, padding);
            int size = actionItems.size();
            for (int i = 0; i < size; i++) {
                Action<T> action = actionItems.get(i);
                View convertView = actionAdapter.getView(i, action.actionData, linearLayout);
                convertView.setTag(i);
                if (action.actionListener == null) {
                    if (CocoaDialogBuilder.this.actionListener != null) {
                        convertView.setOnClickListener(view -> {
                            dialog.dismiss();
                            CocoaDialogBuilder.this.actionListener.onActionClicked((Integer) convertView.getTag(), action.actionData);
                        });
                    }
                } else {
                    convertView.setOnClickListener(view -> {
                        dialog.dismiss();
                        action.actionListener.onActionClicked((Integer) convertView.getTag(), action.actionData);
                    });
                }
                linearLayout.addView(convertView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                if (i == 0) {
                    convertView.setBackgroundResource(R.drawable.cd_shape_rounded_rectangle_solid_white_conner_top);
                    View view = new View(activity);
                    view.setBackgroundResource(R.color.cd_line);
                    linearLayout.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
                } else if (i == size - 1) {
                    convertView.setBackgroundResource(R.drawable.cd_shape_rounded_rectangle_solid_white_conner_bottom);
                } else {
                    convertView.setBackgroundResource(R.drawable.cd_shape_rounded_rectangle_solid_white_conner_none);
                    View view = new View(activity);
                    view.setBackgroundResource(R.color.cd_line);
                    linearLayout.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
                }
            }

            TextView cancelActionTv = new TextView(activity);
            cancelActionTv.setText("取消");
            cancelActionTv.setTextSize(15);
            cancelActionTv.setGravity(Gravity.CENTER);
            cancelActionTv.setTextColor(Color.BLACK);
            cancelActionTv.setPadding(0, padding, 0, padding);
            cancelActionTv.setBackgroundResource(R.drawable.cd_shape_rounded_rectangle_solid_white_conner_all);
            cancelActionTv.setOnClickListener(view -> dialog.dismiss());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.topMargin = padding;
            linearLayout.addView(cancelActionTv, lp);

            setView(linearLayout);
        }

        dialog = super.create();
        dialog.setWindowWidth(UIUtil.getScreenWidth(activity));
        ((ViewGroup) linearLayout.getParent()).setBackgroundColor(Color.TRANSPARENT);
        return dialog;
    }


    @Override
    protected T getResult() {
        return selectedItem;
    }

    public static class Action<T> {
        private T actionData;
        private ActionListener<T> actionListener;

        public Action(T actionData, ActionListener<T> actionListener) {
            this.actionData = actionData;
            this.actionListener = actionListener;
        }

        public T getActionData() {
            return actionData;
        }

        public ActionListener<T> getActionListener() {
            return actionListener;
        }
    }

    public interface ActionListener<T> {
        void onActionClicked(int position, T actionData);
    }

    public interface ActionAdapter<T> {
        View getView(int position, T actionData, ViewGroup parent);
    }
}
