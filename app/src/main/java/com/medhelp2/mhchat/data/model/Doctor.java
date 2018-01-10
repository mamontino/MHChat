package com.medhelp2.mhchat.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@SuppressWarnings("unused")
public class Doctor extends RealmObject implements Parcelable
{
    @PrimaryKey
    @SerializedName("id_doctor")
    private int idDoctor;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("id_spec")
    private int idSpec;

    @SerializedName("specialty")
    private String specialty;

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

    public int getIdSpec()
    {
        return idSpec;
    }

    public void setIdSpec(int idSpec)
    {
        this.idSpec = idSpec;
    }

    public String getSpecialty()
    {
        return specialty;
    }

    public void setSpecialty(String specialty)
    {
        this.specialty = specialty;
    }

    public Doctor(String specialty)
    {
        this.specialty = specialty;
    }

    public Doctor()
    {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.idDoctor);
        dest.writeString(this.fullName);
        dest.writeInt(this.idSpec);
        dest.writeString(this.specialty);
    }

    protected Doctor(Parcel in)
    {
        this.idDoctor = in.readInt();
        this.fullName = in.readString();
        this.idSpec = in.readInt();
        this.specialty = in.readString();
    }

    public static final Parcelable.Creator<Doctor> CREATOR = new Parcelable.Creator<Doctor>()
    {
        @Override
        public Doctor createFromParcel(Parcel source)
        {
            return new Doctor(source);
        }

        @Override
        public Doctor[] newArray(int size)
        {
            return new Doctor[size];
        }
    };
}
