package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ScheduleList
{
    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("response")
    @Expose
    private List<ScheduleResponse> response = new ArrayList<ScheduleResponse>();

    public boolean isError()
    {
        return error;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<ScheduleResponse> getResponse()
    {
        return response;
    }

    public void setResponse(List<ScheduleResponse> response)
    {
        this.response = response;
    }
}
