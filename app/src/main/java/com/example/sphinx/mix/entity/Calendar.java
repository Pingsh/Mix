package com.example.sphinx.mix.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.sphinx.mix.BR;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 万年历实体类
 * Created by Sphinx on 2017/3/24.
 */

public class Calendar extends BaseObservable implements Serializable {

    /**
     * reason : Success
     * result : {"data":{"avoid":"塑绘.开光.造桥.除服.成服.","animalsYear":"鸡","weekday":"星期五","suit":"嫁娶.冠笄.祭祀.出行.会亲友.修造.动土.入殓.破土.","lunarYear":"丁酉年","lunar":"二月廿七","year-month":"2017-3","date":"2017-3-24"}}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    //数据驱动，刷新布局。需要注意的是，xml中绑定的Entity比这里高级一层，也就是Calendar。因为需要用Calendar.setResult()方法。
    @Bindable
    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * data : {"avoid":"塑绘.开光.造桥.除服.成服.","animalsYear":"鸡","weekday":"星期五","suit":"嫁娶.冠笄.祭祀.出行.会亲友.修造.动土.入殓.破土.","lunarYear":"丁酉年","lunar":"二月廿七","year-month":"2017-3","date":"2017-3-24"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * avoid : 塑绘.开光.造桥.除服.成服.
             * animalsYear : 鸡
             * weekday : 星期五
             * suit : 嫁娶.冠笄.祭祀.出行.会亲友.修造.动土.入殓.破土.
             * lunarYear : 丁酉年
             * lunar : 二月廿七
             * year-month : 2017-3
             * date : 2017-3-24
             */

            private String avoid;
            private String animalsYear;
            private String weekday;
            private String suit;
            private String lunarYear;
            private String lunar;
            @SerializedName("year-month")
            private String yearmonth;
            private String date;

            public String getAvoid() {
                return avoid;
            }

            public void setAvoid(String avoid) {
                this.avoid = avoid;
            }

            public String getAnimalsYear() {
                return animalsYear;
            }

            public void setAnimalsYear(String animalsYear) {
                this.animalsYear = animalsYear;
            }

            public String getWeekday() {
                return weekday;
            }

            public void setWeekday(String weekday) {
                this.weekday = weekday;
            }

            public String getSuit() {
                return suit;
            }

            public void setSuit(String suit) {
                this.suit = suit;
            }

            public String getLunarYear() {
                return lunarYear;
            }

            public void setLunarYear(String lunarYear) {
                this.lunarYear = lunarYear;
            }

            public String getLunar() {
                return lunar;
            }

            public void setLunar(String lunar) {
                this.lunar = lunar;
            }

            public String getYearmonth() {
                return yearmonth;
            }

            public void setYearmonth(String yearmonth) {
                this.yearmonth = yearmonth;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
