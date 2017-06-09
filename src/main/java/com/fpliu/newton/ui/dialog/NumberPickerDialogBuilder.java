package com.fpliu.newton.ui.dialog;

import android.app.Activity;
import android.widget.NumberPicker;

import java.util.List;

/**
 * 使用NumberPicker展示的单选列表
 *
 * @author 792793182@qq.com 2016-04-23.
 */
public class NumberPickerDialogBuilder<T> extends CustomDialogBuilder<NumberPickerDialogBuilder<T>, NumberPicker, T> {

    private List<T> items;

    private Converter<T> converter;

    private int selectedIndex;


    public NumberPickerDialogBuilder(Activity activity) {
        super(activity);
    }

    public NumberPickerDialogBuilder setItems(List<T> items) {
        this.items = items;
        return this;
    }

    public NumberPickerDialogBuilder setInitIndex(int initIndex) {
        this.selectedIndex = initIndex;
        return this;
    }

    public NumberPickerDialogBuilder setConverter(Converter<T> converter) {
        this.converter = converter;
        return this;
    }

    @Override
    public CustomDialog create() {
        int size;
        if (items == null) {
            size = 0;
        } else {
            size = items.size();
        }

        NumberPicker numberPicker = getView();
        if (numberPicker == null) {
            numberPicker = new NumberPicker(getActivity());
            String[] displayedValues = new String[size];
            for (int i = 0; i < size; i++) {
                if (converter == null) {
                    displayedValues[i] = items.get(i).toString();
                } else {
                    displayedValues[i] = converter.convert(items, i);
                }
            }
            numberPicker.setDisplayedValues(displayedValues);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(size - 1);
            if (selectedIndex == -1) {
                selectedIndex = size / 2;
            }
            numberPicker.setValue(selectedIndex);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            setView(numberPicker);
        }

        return super.create();
    }


    @Override
    protected T getResult() {
        return items.get(getView().getValue());
    }

    public interface Converter<T> {
        String convert(List<T> items, int index);
    }
}
