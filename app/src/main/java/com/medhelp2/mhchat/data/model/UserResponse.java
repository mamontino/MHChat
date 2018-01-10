package com.medhelp2.mhchat.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@SuppressWarnings("unused")
public class UserResponse extends RealmObject implements Parcelable
{
    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("id_user")
    private int idUser;

    @SerializedName("id_center")
    private int idCenter;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("username")
    private String username;

    @SerializedName("phone")
    private String phone;

    @SerializedName("phone_key")
    private String phoneKey;

    @SerializedName("fb_key")
    private String fbKey;

    @SerializedName("token")
    private String apiKey;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getIdUser()
    {
        return idUser;
    }

    public void setIdUser(int idUser)
    {
        this.idUser = idUser;
    }

    public int getIdCenter()
    {
        return idCenter;
    }

    public void setIdCenter(int idCenter)
    {
        this.idCenter = idCenter;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhoneKey()
    {
        return phoneKey;
    }

    public void setPhoneKey(String phoneKey)
    {
        this.phoneKey = phoneKey;
    }

    public String getFbKey()
    {
        return fbKey;
    }

    public void setFbKey(String fbKey)
    {
        this.fbKey = fbKey;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
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
        dest.writeInt(this.idUser);
        dest.writeInt(this.idCenter);
        dest.writeString(this.fullName);
        dest.writeString(this.username);
        dest.writeString(this.phone);
        dest.writeString(this.phoneKey);
        dest.writeString(this.fbKey);
        dest.writeString(this.apiKey);
    }

    public UserResponse()
    {
    }

    @SuppressWarnings("all")
    protected UserResponse(Parcel in)
    {
        this.id = in.readInt();
        this.idUser = in.readInt();
        this.idCenter = in.readInt();
        this.fullName = in.readString();
        this.username = in.readString();
        this.phone = in.readString();
        this.phoneKey = in.readString();
        this.fbKey = in.readString();
        this.apiKey = in.readString();
    }

    public static final Parcelable.Creator<UserResponse> CREATOR = new Parcelable.Creator<UserResponse>()
    {
        @Override
        public UserResponse createFromParcel(Parcel source)
        {
            return new UserResponse(source);
        }

        @Override
        public UserResponse[] newArray(int size)
        {
            return new UserResponse[size];
        }
    };
}