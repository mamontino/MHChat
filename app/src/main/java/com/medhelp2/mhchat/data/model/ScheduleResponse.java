package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ScheduleResponse
{
    @SerializedName("is_work")
    @Expose
    private boolean isWork;

    @SerializedName("adm_day")
    @Expose
    private String admDay;

    @SerializedName("adm_time")
    @Expose
    private List<String> admTime = new ArrayList<String>();

    public boolean isWork()
    {
        return isWork;
    }

    public void setIsWork(boolean isWork)
    {
        this.isWork = isWork;
    }

    public String getAdmDay()
    {
        return admDay;
    }

    public void setAdmDay(String admDay)
    {
        this.admDay = admDay;
    }

    public List<String> getAdmTime()
    {
        return admTime;
    }

    public void setAdmTime(List<String> admTime)
    {
        this.admTime = admTime;
    }
}
