package com.unionx.yilingdoctor.framework.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fpliu.newton.framework.ui.dialog.R;

import java.util.List;

/**
 * 使用RadioButton展示的单选列表
 *
 * @author 792793182@qq.com 2016-04-23.
 */
public class RadioButtonDialogBuilder<T> extends CustomDialogBuilder<RadioButtonDialogBuilder<T>, GridView, T> {

    private List<T> items;

    private Converter<T> converter;

    private int selectedIndex;


    public RadioButtonDialogBuilder(Activity activity) {
        super(activity);
    }

    public RadioButtonDialogBuilder setItems(List<T> items) {
        this.items = items;
        return this;
    }

    public RadioButtonDialogBuilder setInitIndex(int initIndex) {
        this.selectedIndex = initIndex;
        return this;
    }

    public RadioButtonDialogBuilder setConverter(Converter<T> converter) {
        this.converter = converter;
        return this;
    }

    @Override
    public CustomDialog create() {
        GridView gridView = getView();
        if (gridView == null) {
            Activity activity = getActivity();
            gridView = new GridView(activity);
            gridView.setNumColumns(2);
            gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            gridView.setCacheColorHint(Color.TRANSPARENT);
            gridView.setAdapter(new BaseAdapter() {

                private View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedIndex = (int) v.getTag();
                        notifyDataSetChanged();
                    }
                };

                @Override
                public int getCount() {
                    return items.size();
                }

                @Override
                public T getItem(int position) {
                    return items.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView;
                    if (convertView == null) {
                        LinearLayout linearLayout = new LinearLayout(parent.getContext());
                        linearLayout.setGravity(Gravity.CENTER);

                        textView = new TextView(parent.getContext());
                        linearLayout.addView(textView);

                        convertView = linearLayout;
                        convertView.setTag(textView);
                    } else {
                        textView = (TextView) convertView.getTag();
                    }

                    String displayText;
                    if (converter == null) {
                        displayText = getItem(position).toString();
                    } else {
                        displayText = converter.convert(items, position);
                    }
                    textView.setText(displayText);
                    textView.setPadding(5, 10, 5, 10);
                    textView.setCompoundDrawablePadding(5);
                    if (selectedIndex == position) {
                        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_radio_checked, 0, 0, 0);
                    } else {
                        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_radio_unchecked, 0, 0, 0);
                    }
                    textView.setTag(position);
                    textView.setOnClickListener(onClickListener);

                    return convertView;
                }
            });

            setView(gridView);
        }

        return super.create();
    }


    @Override
    protected T getResult() {
        return items.get(selectedIndex);
    }

    public interface Converter<T> {
        String convert(List<T> items, int index);
    }
}
