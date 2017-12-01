package com.medhelp2.mhchat.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CenterResponse extends RealmObject implements Parcelable
{
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_center")
    @Expose
    private int idCenter;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("info")
    @Expose
    private String info;

    @SerializedName("logo")
    @Expose
    private String logo;

    @SerializedName("site")
    @Expose
    private String site;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address")
    @Expose
    private String address;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getIdCenter()
    {
        return idCenter;
    }

    public void setIdCenter(int idCenter)
    {
        this.idCenter = idCenter;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getSite()
    {
        return site;
    }

    public void setSite(String site)
    {
        this.site = site;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeInt(this.idCenter);
        dest.writeString(this.title);
        dest.writeString(this.info);
        dest.writeString(this.logo);
        dest.writeString(this.site);
        dest.writeString(this.phone);
        dest.writeString(this.address);
    }

    public CenterResponse()
    {
    }

    protected CenterResponse(Parcel in)
    {
        this.id = in.readInt();
        this.idCenter = in.readInt();
        this.title = in.readString();
        this.info = in.readString();
        this.logo = in.readString();
        this.site = in.readString();
        this.phone = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<CenterResponse> CREATOR = new Parcelable.Creator<CenterResponse>()
    {
        @Override
        public CenterResponse createFromParcel(Parcel source)
        {
            return new CenterResponse(source);
        }

        @Override
        public CenterResponse[] newArray(int size)
        {
            return new CenterResponse[size];
        }
    };
}