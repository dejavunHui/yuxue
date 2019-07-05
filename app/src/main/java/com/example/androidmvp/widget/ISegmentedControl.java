package com.example.androidmvp.widget;

public interface ISegmentedControl {

    /**
     * item 数量
     * @return *
     */
    int getCount();

    /**
     * 选中item
     * @param position *
     * @return *
     */
    SegmentedControlItem getItem(int position);

    /**
     * 选中item的名字
     * @param position *
     * @return *
     */
    String getName(int position);

}

