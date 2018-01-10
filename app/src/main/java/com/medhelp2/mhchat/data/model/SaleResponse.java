package com.medhelp2.mhchat.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

@SuppressWarnings("unused")
public class SaleResponse implements Parcelable
{
    @PrimaryKey
    @SerializedName("id_sale")
    private int idSale;

    @SerializedName("id_center")
    private int idCenter;

    @SerializedName("sale_image")
    private String saleImage;

    @SerializedName("sale_description")
    private String saleDescription;

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public String getSaleImage() {
        return saleImage;
    }

    public void setSaleImage(String saleImage) {
        this.saleImage = saleImage;
    }

    public String getSaleDescription() {
        return saleDescription;
    }

    public void setSaleDescription(String saleDescription) {
        this.saleDescription = saleDescription;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.idSale);
        dest.writeInt(this.idCenter);
        dest.writeString(this.saleImage);
        dest.writeString(this.saleDescription);
    }

    public SaleResponse()
    {
    }

    protected SaleResponse(Parcel in)
    {
        this.idSale = in.readInt();
        this.idCenter = in.readInt();
        this.saleImage = in.readString();
        this.saleDescription = in.readString();
    }

    public static final Parcelable.Creator<SaleResponse> CREATOR = new Parcelable.Creator<SaleResponse>()
    {
        @Override
        public SaleResponse createFromParcel(Parcel source)
        {
            return new SaleResponse(source);
        }

        @Override
        public SaleResponse[] newArray(int size)
        {
            return new SaleResponse[size];
        }
    };
}
