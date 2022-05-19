package com.example.ps6.App;

import java.io.Serializable;

public class AppConfig implements Serializable {

    int listViewItemPositionStudentAdapter;

    public AppConfig() {
        listViewItemPositionStudentAdapter = 0;
    }

    public int getListViewItemPositionStudentAdapter() {
        return listViewItemPositionStudentAdapter;
    }

    public void setListViewItemPositionStudentAdapter(int listViewItemPositionStudentAdapter) {
        this.listViewItemPositionStudentAdapter = listViewItemPositionStudentAdapter;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "listViewItemPositionStudentAdapter=" + listViewItemPositionStudentAdapter +
                '}';
    }
}
