package com.fpliu.newton.ui.dialog;

import android.app.Activity;
import android.view.View;

/**
 * @author 792793182@qq.com 2016-04-23.
 */
public class AlertDialogBuilder extends CustomDialogBuilder<AlertDialogBuilder, View, Object> {

    public AlertDialogBuilder(Activity activity) {
        super(activity);
    }

    @Override
    protected Object getResult() {
        return null;
    }
}
