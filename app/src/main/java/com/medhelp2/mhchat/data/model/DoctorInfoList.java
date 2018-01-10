package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class DoctorInfoList
{
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("response")
    private List<DoctorInfo> response = new ArrayList<>();

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DoctorInfo> getResponse() {
        return response;
    }

    public void setResponse(List<DoctorInfo> response) {
        this.response = response;
    }
}