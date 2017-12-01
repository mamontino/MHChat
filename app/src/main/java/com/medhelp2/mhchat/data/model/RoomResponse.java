package com.medhelp2.mhchat.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RoomResponse extends RealmObject implements Serializable, Parcelable
{
    @PrimaryKey
    @SerializedName("id_room")
    @Expose
    private int idRoom;

    @SerializedName("id_doctor")
    @Expose
    private int idDoctor;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    public int getIdRoom()
    {
        return idRoom;
    }

    public void setIdRoom(int idRoom)
    {
        this.idRoom = idRoom;
    }

    public int getIdDoctor()
    {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor)
    {
        this.idDoctor = idDoctor;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.idRoom);
        dest.writeInt(this.idDoctor);
        dest.writeString(this.fullName);
    }

    public RoomResponse()
    {
    }

    protected RoomResponse(Parcel in)
    {
        this.idRoom = in.readInt();
        this.idDoctor = in.readInt();
        this.fullName = in.readString();
    }

    public RoomResponse(int idRoom, int idDoctor, String fullName)
    {
        this.idRoom = idRoom;
        this.idDoctor = idDoctor;
        this.fullName = fullName;
    }


    public static final Creator<RoomResponse> CREATOR = new Creator<RoomResponse>()
    {
        @Override
        public RoomResponse createFromParcel(Parcel source)
        {
            return new RoomResponse(source);
        }

        @Override
        public RoomResponse[] newArray(int size)
        {
            return new RoomResponse[size];
        }
    };
}
