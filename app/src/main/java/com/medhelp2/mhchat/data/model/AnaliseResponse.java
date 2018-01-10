package com.medhelp2.mhchat.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class AnaliseResponse implements Parcelable
{
    private String date;
    private String name;
    private String image;

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.date);
        dest.writeString(this.name);
        dest.writeString(this.image);
    }

    public AnaliseResponse() {}

    protected AnaliseResponse(Parcel in)
    {
        this.date = in.readString();
        this.name = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<AnaliseResponse> CREATOR = new Parcelable.Creator<AnaliseResponse>()
    {
        @Override
        public AnaliseResponse createFromParcel(Parcel source) {return new AnaliseResponse(source);}

        @Override
        public AnaliseResponse[] newArray(int size) {return new AnaliseResponse[size];}
    };
}
