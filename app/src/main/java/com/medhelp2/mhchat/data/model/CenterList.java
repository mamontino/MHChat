package com.medhelp2.mhchat.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CenterList
{
    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("response")
    @Expose
    private List<CenterResponse> response = new ArrayList<CenterResponse>();

    @SerializedName("message")
    @Expose
    private String message;


    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public boolean isError()
    {
        return error;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }

    public List<CenterResponse> getResponse()
    {
        return response;
    }

    public void setResponse(List<CenterResponse> response)
    {
        this.response = response;
    }
}