package com.medhelp2.mhchat.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ServiceResponse extends RealmObject implements Parcelable
{
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_service")
    @Expose
    private int idService;

    @SerializedName("id_spec")
    @Expose
    private int idSpec;

    @SerializedName("admission")
    @Expose
    private int admission;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("title")
    @Expose
    private String title;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getIdService()
    {
        return idService;
    }

    public void setIdService(int idService)
    {
        this.idService = idService;
    }

    public int getIdSpec()
    {
        return idSpec;
    }

    public void setIdSpec(int idSpec)
    {
        this.idSpec = idSpec;
    }

    public int getAdmission()
    {
        return admission;
    }

    public void setAdmission(int admission)
    {
        this.admission = admission;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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
        dest.writeInt(this.idService);
        dest.writeInt(this.idSpec);
        dest.writeInt(this.admission);
        dest.writeString(this.value);
        dest.writeString(this.title);
    }

    public ServiceResponse()
    {
    }

    protected ServiceResponse(Parcel in)
    {
        this.id = in.readInt();
        this.idService = in.readInt();
        this.idSpec = in.readInt();
        this.admission = in.readInt();
        this.value = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<ServiceResponse> CREATOR = new Parcelable.Creator<ServiceResponse>()
    {
        @Override
        public ServiceResponse createFromParcel(Parcel source)
        {
            return new ServiceResponse(source);
        }

        @Override
        public ServiceResponse[] newArray(int size)
        {
            return new ServiceResponse[size];
        }
    };
}