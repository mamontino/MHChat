package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ScheduleResponse
{
    @SerializedName("id_doctor")
    private int idDoctor;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("is_work")
    private boolean isWork;

    @SerializedName("adm_day")
    private String admDay;

    @SerializedName("adm_time")
    private List<String> admTime = new ArrayList<>();

    public ScheduleResponse()
    {
    }

    public ScheduleResponse(int idDoctor, String fullName, boolean isWork, String admDay, List<String> admTime)
    {
        this.idDoctor = idDoctor;
        this.fullName = fullName;
        this.isWork = isWork;
        this.admDay = admDay;
        this.admTime = admTime;
    }

    public boolean isWork()
    {
        return isWork;
    }

    public void setWork(boolean isWork)
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

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public List<String> getAdmTime()
    {
        return admTime;
    }

    public void setAdmTime(List<String> admTime)
    {
        this.admTime = admTime;
    }

    public int getIdDoctor()
    {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor)
    {
        this.idDoctor = idDoctor;
    }
}
