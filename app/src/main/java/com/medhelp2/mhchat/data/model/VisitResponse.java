package com.medhelp2.mhchat.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class VisitResponse implements Parcelable
{
    @PrimaryKey
    @SerializedName("id_schedule")
    @Expose
    private int idSchedule;

    @SerializedName("adm_date")
    @Expose
    private String admDate;

    @SerializedName("adm_time")
    @Expose
    private String admTime;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("photo")
    @Expose
    private String photo;

    public int getIdSchedule()
    {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule)
    {
        this.idSchedule = idSchedule;
    }

    public String getAdmDate()
    {
        return admDate;
    }

    public void setAdmDate(String admDate)
    {
        this.admDate = admDate;
    }

    public String getAdmTime()
    {
        return admTime;
    }

    public void setAdmTime(String admTime)
    {
        this.admTime = admTime;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.idSchedule);
        dest.writeString(this.admDate);
        dest.writeString(this.admTime);
        dest.writeString(this.fullName);
        dest.writeString(this.state);
        dest.writeString(this.title);
        dest.writeString(this.photo);
    }

    public VisitResponse()
    {
    }

    protected VisitResponse(Parcel in)
    {
        this.idSchedule = in.readInt();
        this.admDate = in.readString();
        this.admTime = in.readString();
        this.fullName = in.readString();
        this.state = in.readString();
        this.title = in.readString();
        this.photo = in.readString();
    }

    public static final Creator<VisitResponse> CREATOR = new Creator<VisitResponse>()
    {
        @Override
        public VisitResponse createFromParcel(Parcel source)
        {
            return new VisitResponse(source);
        }

        @Override
        public VisitResponse[] newArray(int size)
        {
            return new VisitResponse[size];
        }
    };
}