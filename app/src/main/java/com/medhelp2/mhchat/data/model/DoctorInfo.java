package com.medhelp2.mhchat.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class DoctorInfo implements Parcelable
{
    @PrimaryKey
    @SerializedName("id_doctor")
    @Expose
    private int idDoctor;

    @SerializedName("id_doctor_center")
    @Expose
    private int idDoctorCenter;

    @SerializedName("id_center")
    @Expose
    private int idCenter;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("expr")
    @Expose
    private int expr;

    @SerializedName("info")
    @Expose
    private String info;

    @SerializedName("specialty")
    @Expose
    private String specialty;

    @SerializedName("fb_key")
    @Expose
    private String fbKey;

    public int getIdDoctor()
    {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor)
    {
        this.idDoctor = idDoctor;
    }

    public int getIdDoctorCenter()
    {
        return idDoctorCenter;
    }

    public void setIdDoctorCenter(int idDoctorCenter)
    {
        this.idDoctorCenter = idDoctorCenter;
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

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public int getExpr()
    {
        return expr;
    }

    public void setExpr(int expr)
    {
        this.expr = expr;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public String getSpecialty()
    {
        return specialty;
    }

    public void setSpecialty(String specialty)
    {
        this.specialty = specialty;
    }

    public String getFbKey()
    {
        return fbKey;
    }

    public void setFbKey(String fbKey)
    {
        this.fbKey = fbKey;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.idDoctorCenter);
        dest.writeInt(this.idCenter);
        dest.writeString(this.fullName);
        dest.writeString(this.photo);
        dest.writeInt(this.expr);
        dest.writeString(this.info);
        dest.writeString(this.specialty);
        dest.writeString(this.fbKey);
        dest.writeInt(this.idDoctor);
    }

    public DoctorInfo()
    {
    }

    protected DoctorInfo(Parcel in)
    {
        this.idDoctorCenter = in.readInt();
        this.idCenter = in.readInt();
        this.fullName = in.readString();
        this.photo = in.readString();
        this.expr = in.readInt();
        this.info = in.readString();
        this.specialty = in.readString();
        this.fbKey = in.readString();
        this.idDoctor = in.readInt();
    }

    public static final Creator<DoctorInfo> CREATOR = new Creator<DoctorInfo>()
    {
        @Override
        public DoctorInfo createFromParcel(Parcel source)
        {
            return new DoctorInfo(source);
        }

        @Override
        public DoctorInfo[] newArray(int size)
        {
            return new DoctorInfo[size];
        }
    };
}