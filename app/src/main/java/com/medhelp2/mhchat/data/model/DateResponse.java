package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DateResponse
{
    @SerializedName("today")
    private String today;

    @SerializedName("week_day")
    private String weekDay;

    @SerializedName("last_monday")
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
