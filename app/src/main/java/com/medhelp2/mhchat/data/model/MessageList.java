package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageList
{
    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("response")
    @Expose
    private List<MessageResponse> response;

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

    public List<MessageResponse> getResponse()
    {
        return response;
    }

    public void setResponse(List<MessageResponse> response)
    {
        this.response = response;
    }

}
