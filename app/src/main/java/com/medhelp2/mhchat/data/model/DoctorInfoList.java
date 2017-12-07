package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DoctorInfoList
{
    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("response")
    @Expose
    private List<DoctorInfo> response = new ArrayList<DoctorInfo>();

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