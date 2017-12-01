package com.medhelp2.mhchat.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Doctor extends RealmObject
{
    @PrimaryKey
    @SerializedName("id_doctor")
    @Expose
    private int idDoctor;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("id_spec")
    @Expose
    private int idSpec;

    @SerializedName("specialty")
    @Expose
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
}
