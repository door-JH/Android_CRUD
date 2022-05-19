package com.example.ps4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ps4.R;

import java.util.List;



public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> items;

    public SpinnerAdapter(final Context context,
                          final int textViewResourceId, List<String> items) {

        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    /**
     * 스피너 클릭시 보여지는 View의 정의
     */
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

        }

        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        //android.view.ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        //layoutParams.width = ActionBar.LayoutParams.WRAP_CONTENT;
        //layoutParams.height = ActionBar.LayoutParams.WRAP_CONTENT;
        //convertView.setLayoutParams(layoutParams);
        tv.setText(items.get(position));
        tv.setPadding(20, 10, 10, 10);
        tv.setTextColor(context.getResources().getColor(R.color.gray_600));
        tv.setTextSize(16);
        return convertView;
    }

    /**
     * 기본 스피너 View 정의
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    android.R.layout.simple_spinner_item, parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(items.get(position));
        tv.setTextColor(context.getResources().getColor(R.color.gray_600));
        tv.setTextSize(16);

        return convertView;
    }
}

