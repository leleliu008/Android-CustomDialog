package com.fpliu.newton.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.widget.EditText;

/**
 * 提示用户输入框
 *
 * @author 792793182@qq.com 2016-04-23.
 */
public class PromptDialogBuilder extends CustomDialogBuilder<PromptDialogBuilder, EditText, String> {

    public PromptDialogBuilder(Activity activity) {
        super(activity);
    }

    @Override
    public CustomDialog create() {
        EditText editText = getView();
        if (editText == null) {
            editText = new EditText(getActivity());
            editText.setTextSize(14);
            editText.setTextColor(Color.parseColor("#575757"));
            editText.setBackgroundResource(R.drawable.cd_shape_rounded_rectangle_stoke_gray);

            setView(editText);
        }

        return super.create();
    }


    @Override
    protected String getResult() {
        return getView().getText().toString();
    }
}
