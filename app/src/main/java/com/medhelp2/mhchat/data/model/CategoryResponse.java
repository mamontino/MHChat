package com.medhelp2.mhchat.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CategoryResponse extends RealmObject implements Parcelable
{
    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("id_spec")
    private int idSpec;

    @SerializedName("title")
    private String title;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getIdSpec()
    {
        return idSpec;
    }

    public void setIdSpec(int idSpec)
    {
        this.idSpec = idSpec;
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
        dest.writeInt(this.idSpec);
        dest.writeString(this.title);
    }

    public CategoryResponse()
    {
    }

    public CategoryResponse(String title)
    {
        this.title = title;
    }

    protected CategoryResponse(Parcel in)
    {
        this.id = in.readInt();
        this.idSpec = in.readInt();
        this.title = in.readString();
    }

    public static final Creator<CategoryResponse> CREATOR = new Creator<CategoryResponse>()
    {
        @Override
        public CategoryResponse createFromParcel(Parcel source)
        {
            return new CategoryResponse(source);
        }

        @Override
        public CategoryResponse[] newArray(int size)
        {
            return new CategoryResponse[size];
        }
    };
}