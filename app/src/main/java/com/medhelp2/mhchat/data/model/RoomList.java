package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class RoomList
{
    @SerializedName("error")
    private boolean error;

    @SerializedName("response")
    private List<RoomResponse> mResponses = new ArrayList<>();

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

    public List<RoomResponse> getResponses()
    {
        return mResponses;
    }

    public void setResponses(List<RoomResponse> responses)
    {
        this.mResponses = responses;
    }
}
