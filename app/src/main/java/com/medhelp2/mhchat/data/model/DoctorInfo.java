//package com.medhelp2.mhchat.data.model;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class DoctorInfo implements Parcelable {
//
//    @SerializedName("id")
//    @Expose
//    private int id;
//
//    @SerializedName("id_sotr")
//    @Expose
//    private int idDoctor;
//
//    @SerializedName("fio")
//    @Expose
//    private String fio;
//
//    @SerializedName("foto")
//    @Expose
//    private String foto;
//
//    @SerializedName("stag")
//    @Expose
//    private String stag;
//
//    @SerializedName("info")
//    @Expose
//    private String info;
//
//    @SerializedName("id_centr")
//    @Expose
//    private int idCenter;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getIdDoctor() {
//        return idDoctor;
//    }
//
//    public void setIdDoctor(int idDoctor) {
//        this.idDoctor = idDoctor;
//    }
//
//    public String getFio() {
//        return fio;
//    }
//
//    public void setFio(String fio) {
//        this.fio = fio;
//    }
//
//    public String getFoto() {
//        return foto;
//    }
//
//    public void setFoto(String foto) {
//        this.foto = foto;
//    }
//
//    public String getStag() {
//        return stag;
//    }
//
//    public void setStag(String stag) {
//        this.stag = stag;
//    }
//
//    public String getInfo() {
//        return info;
//    }
//
//    public void setInfo(String info) {
//        this.info = info;
//    }
//
//    public int getIdCenter() {
//        return idCenter;
//    }
//
//    public void setIdCenter(int idCenter) {
//        this.idCenter = idCenter;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(this.id);
//        dest.writeInt(this.idDoctor);
//        dest.writeString(this.fio);
//        dest.writeString(this.foto);
//        dest.writeString(this.stag);
//        dest.writeString(this.info);
//        dest.writeInt(this.idCenter);
//    }
//
//    public DoctorInfo() {
//    }
//
//    protected DoctorInfo(Parcel in) {
//        this.id = in.readInt();
//        this.idDoctor = in.readInt();
//        this.fio = in.readString();
//        this.foto = in.readString();
//        this.stag = in.readString();
//        this.info = in.readString();
//        this.idCenter = in.readInt();
//    }
//
//    public static final Creator<DoctorInfo> CREATOR = new Creator<DoctorInfo>() {
//        @Override
//        public DoctorInfo createFromParcel(Parcel source) {
//            return new DoctorInfo(source);
//        }
//
//        @Override
//        public DoctorInfo[] newArray(int size) {
//            return new DoctorInfo[size];
//        }
//    };
//}