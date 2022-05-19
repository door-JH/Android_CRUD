package com.example.ps6.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ps6.Model.Student;
import com.example.ps6.R;

import java.util.ArrayList;
import java.util.List;



public final class StudentAdapter extends ArrayAdapter<Student> {

    // Log tag
    private final String TAG = getClass().getSimpleName();
    private final boolean D = false;
    Activity activity;

    Student s;
    List<Student> list = new ArrayList<Student>();

    SparseBooleanArray selectedItemsIds;
    int position;
    boolean clickMode = false; // true for long click
    //수정
    public StudentAdapter(#, #, #) {

        super(activity, resId, list);
        this.activity = activity;
        selectedItemsIds = new SparseBooleanArray();
        this.list = list;

        if (D) Log.i(TAG, "StudentAdapter() is constructed!!");
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {

        final ViewHolder holder;

        if (view == null) {

            holder = new ViewHolder();
            final Context mContext = getContext();
            final LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.listitem_student, null);
            //수정
            holder.num = view.findViewById(R.id.#);
            holder.name = view.findViewById(R.id.#);
            holder.phone = view.findViewById(R.id.#);
            holder.dept = view.findViewById(R.id.#);
            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();
        }

        // getting attending data for the row
        s = getItem(position);
        if(D) Log.i(TAG, s.toString());

        holder.num.setText(#);

        holder.name.setText(#);

        if(s.getPhone() == null)
            holder.phone.setText("010-0000-0000");
        else
            holder.phone.setText(#);

        holder.dept.setText(#);

        if ((selectedItemsIds.get(position) && clickMode)) {
            view.setBackgroundColor(0x9934B5E4);
        } else if ((position == this.position) && !clickMode) {
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setBackgroundColor(0x9934B5E4);
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        return view;
    }

    public void setClickMode(boolean clickMode) {
        this.clickMode = clickMode;
    }

    public void setSellectedPosition(int position) {
        this.position = position;
    }

    @Override
    public void add(Student s) {

        list.add(s);
        notifyDataSetChanged();
    }

    @Override
    public void remove(Student s) {

        list.remove(s);
        notifyDataSetChanged();
    }

    public Student getStudent(int position) {

        return getItem(position);
    }

    public List<Student> getStudents() {

        return list;
    }

    public void toggleSelection(int position) {

        selectView(position, !selectedItemsIds.get(position));
    }

    public void removeSelection() {

        selectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {

        if (value)
            selectedItemsIds.put(position, value);
        else
            selectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {

        return selectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {

        return selectedItemsIds;
    }

    private static class ViewHolder {
        //수정
        public TextView #;
        public TextView #;
        public TextView #;
        public TextView #;
    }

}