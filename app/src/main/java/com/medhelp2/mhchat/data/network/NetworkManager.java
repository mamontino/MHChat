package com.medhelp2.mhchat.data.network;

import com.medhelp2.mhchat.data.model.CategoryList;
import com.medhelp2.mhchat.data.model.CenterList;
import com.medhelp2.mhchat.data.model.DateList;
import com.medhelp2.mhchat.data.model.ScheduleList;
import com.medhelp2.mhchat.data.model.DoctorInfoList;
import com.medhelp2.mhchat.data.model.DoctorList;
import com.medhelp2.mhchat.data.model.MessageList;
import com.medhelp2.mhchat.data.model.RequestResponse;
import com.medhelp2.mhchat.data.model.RoomList;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.data.model.SaleList;
import com.medhelp2.mhchat.data.model.ServiceList;
import com.medhelp2.mhchat.data.model.UserList;
import com.medhelp2.mhchat.data.model.VisitList;
import com.medhelp2.mhchat.data.pref.PreferencesManager;
import com.medhelp2.mhchat.di.scope.PerApplication;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@PerApplication
public class NetworkManager implements NetworkHelper
{
    private PreferencesManager prefManager;

    private static final String ID_CENTER = "id_center";
    private static final String ID_CLIENT = "id_client";
    private static final String ID_DOCTOR = "id_doctor";
    private static final String ID_SERVICE = "id_service";
    private static final String ID_USER = "id_user";
    private static final String ID_ROOM = "id_room";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FB_TOKEN = "fb_token";
    private static final String MESSAGE = "message";
    private static final String ADM_DATE = "date";
    private static final String ADM_TIME = "adm";
    private static final String AUTH = "Authorization";


    @Inject
    public NetworkManager(@PerApplication PreferencesManager preferencesManager)
    {
        this.prefManager = preferencesManager;
    }


    @Override
    public Observable<UserList> doLoginApiCall(String username, String password)
    {
        return Rx2AndroidNetworking.post(ApiEndPoint.LOGIN)
                .addHeaders(AUTH, AppConstants.API_KEY)
                .addBodyParameter(USERNAME, username)
                .addBodyParameter(PASSWORD, password)
                .build()
                .getObjectObservable(UserList.class);
    }

    @Override
    public Observable<List<RoomResponse>> getRoomListApiCall()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.ROOM_LIST)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_USER, String.valueOf(prefManager.getCurrentUserId()))
                .build()
                .getObjectObservable(RoomList.class)
                .map(roomList -> (List<RoomResponse>) new ArrayList<>(roomList.getResponses()));
    }

    @Override
    public Observable<RoomResponse> getRoomByIdApiCall(int id)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.ROOM_BY_ID)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_ROOM, String.valueOf(id))
                .build()
                .getObjectObservable(RoomResponse.class);
    }

    @Override
    public Observable<MessageList> getMessageListApiCall(int idRoom)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.MESSAGE_LIST)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_ROOM, String.valueOf(idRoom))
                .build()
                .getObjectObservable(MessageList.class);
    }

    @Override
    public Observable<RequestResponse> sendMessageApiCall(int idRoom, String message)
    {
        return Rx2AndroidNetworking.post(ApiEndPoint.SEND_MESSAGE)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addBodyParameter(ID_USER, String.valueOf(prefManager.getCurrentUserId()))
                .addBodyParameter(ID_ROOM, String.valueOf(idRoom))
                .addBodyParameter(MESSAGE, message)
                .build()
                .getObjectObservable(RequestResponse.class);
    }

    @Override
    public Observable<RequestResponse> readMessagesApiCall(int idRoom)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.READ_MESSAGE)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_ROOM, String.valueOf(idRoom))
                .addPathParameter(ID_USER, String.valueOf(prefManager.getCurrentUserId()))
                .build()
                .getObjectObservable(RequestResponse.class);
    }

    @Override
    public Observable<RequestResponse> sendTokenToServerApiCall(String token)
    {
        return Rx2AndroidNetworking.post(ApiEndPoint.SEND_TOKEN)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addBodyParameter(ID_USER, String.valueOf(prefManager.getCurrentUserId()))
                .addBodyParameter(FB_TOKEN, token)
                .build()
                .getObjectObservable(RequestResponse.class);
    }

    @Override
    public Observable<List<RoomResponse>> getUnreadCountApiCall()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.UNREAD_API)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_USER, String.valueOf(prefManager.getCurrentUserId()))
                .build()
                .getObjectObservable(RoomList.class)
                .map(roomList -> (List<RoomResponse>) new ArrayList<>(roomList.getResponses()));
    }

    @Override
    public Observable<ServiceList> getPriceApiCall(int idDoctor)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.PRICE_BY_DOCTOR)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .addPathParameter(ID_DOCTOR, String.valueOf(idDoctor))
                .build()
                .getObjectObservable(ServiceList.class);
    }

    @Override
    public Observable<ServiceList> getPriceApiCall()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.PRICE)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .build()
                .getObjectObservable(ServiceList.class);
    }

    @Override
    public Observable<CategoryList> getCategoryApiCall()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.CATEGORY)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .build()
                .getObjectObservable(CategoryList.class);
    }

    @Override
    public Observable<CategoryList> getCategoryApiCall(int idDoctor)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.CATEGORY_BY_ID_DOCTOR)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .addPathParameter(ID_DOCTOR, String.valueOf(idDoctor))
                .build()
                .getObjectObservable(CategoryList.class);
    }

    @Override
    public Observable<CenterList> getCenterApiCall()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.CENTER)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .build()
                .getObjectObservable(CenterList.class);
    }

    @Override
    public Observable<DoctorList> getStaffApiCall()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.DOCTORS)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .build()
                .getObjectObservable(DoctorList.class);
    }

    @Override
    public Observable<DoctorList> getStaffApiCall(int idService)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.DOCTORS_BY_SERVICE)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .addPathParameter(ID_SERVICE, String.valueOf(idService))
                .build()
                .getObjectObservable(DoctorList.class);
    }

    @Override
    public Observable<SaleList> getSaleApiCall()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.SALE)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .build()
                .getObjectObservable(SaleList.class);
    }

    @Override
    public Observable<DoctorInfoList> getDoctorApiCall(int idDoctor)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.DOCTOR_BY_ID)
                .addHeaders(AUTH, AppConstants.API_KEY)
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .addPathParameter(ID_DOCTOR, String.valueOf(idDoctor))
                .build()
                .getObjectObservable(DoctorInfoList.class);
    }

    @Override
    public Observable<VisitList> getAllReceptionApiCall()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.VISITS)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .addPathParameter(ID_CLIENT, String.valueOf(prefManager.getCurrentUserId()))
                .build()
                .getObjectObservable(VisitList.class);
    }

    @Override
    public Observable<VisitList> getActualReceptionApiCall()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.VISITS)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .addPathParameter(ID_CLIENT, String.valueOf(prefManager.getCurrentUserId()))
                .build()
                .getObjectObservable(VisitList.class);
    }

    @Override
    public Observable<ScheduleList> getScheduleByDoctorApiCall(int idDoctor, String date,  int adm)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.SCHEDULE_DOCTOR)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .addPathParameter(ID_DOCTOR, String.valueOf(idDoctor))
                .addPathParameter(ADM_DATE, date)
                .addPathParameter(ADM_TIME, String.valueOf(adm))
                .build()
                .getObjectObservable(ScheduleList.class);
    }

    @Override
    public Observable<ScheduleList> getScheduleByServiceApiCall(int idService, String date, int adm)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.SCHEDULE_SERVICE)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_CENTER, String.valueOf(prefManager.getCurrentCenterId()))
                .addPathParameter(ID_SERVICE, String.valueOf(idService))
                .addPathParameter(ADM_DATE, date)
                .addPathParameter(ADM_TIME, String.valueOf(adm))
                .build()
                .getObjectObservable(ScheduleList.class);
    }

    @Override
    public Observable<DateList> getCurrentDateApiCall()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.CURRENT_DATE)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .build()
                .getObjectObservable(DateList.class);
    }
}

