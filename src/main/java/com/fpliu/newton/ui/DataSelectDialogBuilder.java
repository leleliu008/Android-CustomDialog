package com.fpliu.newton.ui;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.Calendar;
import java.util.Locale;

/**
 * 日期选择弹出框
 *
 * @author 792793182@qq.com 2016-04-23.
 */
public class DataSelectDialogBuilder extends CustomDialogBuilder<DataSelectDialogBuilder, LinearLayout, Calendar> {

    private long initDate;

    private NumberPicker yearPicker;

    private NumberPicker monthPicker;

    private NumberPicker dayPicker;

    public DataSelectDialogBuilder(Activity activity) {
        super(activity);
    }

    public DataSelectDialogBuilder setInitDate(long initDate) {
        this.initDate = initDate;
        return this;
    }

    @Override
    public CustomDialog create() {
        LinearLayout layout = getView();
        if (layout == null) {
            Activity activity = getActivity();
            layout = new LinearLayout(activity);


            yearPicker = new NumberPicker(activity);
            monthPicker = new NumberPicker(activity);
            dayPicker = new NumberPicker(activity);

            if (initDate == 0) {
                initDate = System.currentTimeMillis();
            }

            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeInMillis(initDate);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            if (year < 1800 || year > 3000) {
                year = 1800;
            }

            yearPicker.setMinValue(1800);
            yearPicker.setMaxValue(3000);
            yearPicker.setValue(year);
            yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            yearPicker.setDisplayedValues(getDisplayedValues(1800, 3000, "年"));

            monthPicker.setMinValue(1);
            monthPicker.setMaxValue(12);
            monthPicker.setValue(month + 1);
            monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            monthPicker.setDisplayedValues(getDisplayedValues(1, 12, "月"));

            dayPicker.setMinValue(1);
            dayPicker.setMaxValue(31);
            dayPicker.setValue(day);
            dayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            dayPicker.setDisplayedValues(getDisplayedValues(1, 31, "日"));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            layout.addView(yearPicker, lp);
            layout.addView(monthPicker, lp);
            layout.addView(dayPicker, lp);

            setView(layout);
        }

        return super.create();
    }

    @Override
    protected Calendar getResult() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, yearPicker.getValue());
        calendar.set(Calendar.MONTH, monthPicker.getValue());
        calendar.set(Calendar.DAY_OF_MONTH, dayPicker.getValue());
        return calendar;
    }

    private String[] getDisplayedValues(int min, int max, String stuff) {
        String[] displayedValues = new String[max - min + 1];
        for (int i = min; i <= max; i++) {
            displayedValues[i - min] = i + " " + stuff;
        }
        return displayedValues;
    }
}
