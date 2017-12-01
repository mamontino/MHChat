package com.medhelp2.mhchat.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RoomList
{
    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("response")
    @Expose
    private List<RoomResponse> mResponses = new ArrayList<>();

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

    public List<RoomResponse> getResponses()
    {
        return mResponses;
    }

    public void setResponses(List<RoomResponse> responses)
    {
        this.mResponses = responses;
    }
}
