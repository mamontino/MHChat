package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateResponse
{
    @SerializedName("today")
    @Expose
    private String today;

    @SerializedName("week_day")
    @Expose
    private String weekDay;

    @SerializedName("last_monday")
    @Expose
    private String lastMonday;

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getLastMonday() {
        return lastMonday;
    }

    public void setLastMonday(String lastMonday) {
        this.lastMonday = lastMonday;
    }

    public DateResponse(String lastMonday)
    {
        this.lastMonday = lastMonday;
    }

    public DateResponse()
    {
    }
}
