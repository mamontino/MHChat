package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class VisitList
{
    @SerializedName("error")
    private boolean error;

    @SerializedName("response")
    private List<VisitResponse> response = new ArrayList<>();

    @SerializedName("message")
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

    public List<VisitResponse> getResponse()
    {
        return response;
    }

    public void setResponse(List<VisitResponse> response)
    {
        this.response = response;
    }
}