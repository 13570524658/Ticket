package com.future.zhh.ticket.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.future.zhh.ticket.presentation.view.widgets.selectbox.IContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CarNoListInfo implements IContent, Parcelable {

    private  String carNo;//车牌号码


    public CarNoListInfo(String carNo) {
        this.carNo = carNo;
    }

    public String getContent() {
        return carNo;
    }

    public void setContent(String carNo) {
        this.carNo = carNo;
    }

    @Override
    public String getDesc() {
        return carNo;
    }

    public static class CarNoInfos {
        ArrayList<CarNoListInfo> mList;

        public ArrayList<CarNoListInfo> getList() {
            mList = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                mList.add(new CarNoListInfo(" "));
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
        dest.writeString(this.carNo);
    }

    protected CarNoListInfo(Parcel in) {
        this.carNo = in.readString();
    }

    public static final Parcelable.Creator<CarNoListInfo> CREATOR = new Parcelable.Creator<CarNoListInfo>() {
        @Override
        public CarNoListInfo createFromParcel(Parcel source) {
            return new CarNoListInfo(source);
        }

        @Override
        public CarNoListInfo[] newArray(int size) {
            return new CarNoListInfo[size];
        }
    };
}
