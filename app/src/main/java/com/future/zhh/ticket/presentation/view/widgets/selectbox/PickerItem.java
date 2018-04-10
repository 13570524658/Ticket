package com.future.zhh.ticket.presentation.view.widgets.selectbox;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/22.
 */

public class PickerItem implements IContent, Parcelable {

    private String content;

    public PickerItem(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getDesc() {
        return content;
    }

    public static class PickerItems {
        ArrayList<PickerItem> mList;

        public ArrayList<PickerItem> getList() {
            mList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                mList.add(new PickerItem(" "+i));
            }
            return mList;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
    }

    protected PickerItem(Parcel in) {
        this.content = in.readString();
    }

    public static final Parcelable.Creator<PickerItem> CREATOR = new Parcelable.Creator<PickerItem>() {
        @Override
        public PickerItem createFromParcel(Parcel source) {
            return new PickerItem(source);
        }

        @Override
        public PickerItem[] newArray(int size) {
            return new PickerItem[size];
        }
    };
}
