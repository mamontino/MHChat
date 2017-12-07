package com.medhelp2.mhchat.data.network;

import com.medhelp2.mhchat.data.model.CategoryList;
import com.medhelp2.mhchat.data.model.CenterList;
import com.medhelp2.mhchat.data.model.DoctorInfoList;
import com.medhelp2.mhchat.data.model.DoctorList;
import com.medhelp2.mhchat.data.model.MessageList;
import com.medhelp2.mhchat.data.model.RequestResponse;
import com.medhelp2.mhchat.data.model.RoomList;
import com.medhelp2.mhchat.data.model.RoomResponse;
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
    private static final String ID_USER = "id_user";
    private static final String ID_ROOM = "id_room";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FB_TOKEN = "fb_token";
    private static final String MESSAGE = "message";
    private static final String AUTH = "Authorization";


    @Inject
    public NetworkManager(@PerApplication PreferencesManager preferencesManager)
    {
        this.prefManager = preferencesManager;
    }

    @Override
    public Observable<UserList> doLogin(String username, String password)
    {
        return Rx2AndroidNetworking.post(ApiEndPoint.LOGIN)
                .addHeaders(AUTH, AppConstants.API_KEY)
                .addBodyParameter(USERNAME, username)
                .addBodyParameter(PASSWORD, password)
                .build()
                .getObjectObservable(UserList.class);
    }

    @Override
    public Observable<List<RoomResponse>> getRoomList()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.ROOM_LIST)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_USER, String.valueOf(prefManager.getCurrentUserId()))
                .build()
                .getObjectObservable(RoomList.class)
                .map(roomList -> (List<RoomResponse>) new ArrayList<>(roomList.getResponses()));
    }

    @Override
    public Observable<RoomResponse> getRoomById(int id)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.ROOM_BY_ID)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_ROOM, String.valueOf(id))
                .build()
                .getObjectObservable(RoomResponse.class);
    }

    @Override
    public Observable<MessageList> getMessageList(int idRoom)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.MESSAGE_LIST)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_ROOM, String.valueOf(idRoom))
                .build()
                .getObjectObservable(MessageList.class);
    }

    @Override
    public Observable<RequestResponse> sendMessage(int idRoom, String message)
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
    public Observable<RequestResponse> readMessages(int idRoom)
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.READ_MESSAGE)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_ROOM, String.valueOf(idRoom))
                .addPathParameter(ID_USER, String.valueOf(prefManager.getCurrentUserId()))
                .build()
                .getObjectObservable(RequestResponse.class);
    }

    @Override
    public Observable<RequestResponse> sendTokenToServer(String token)
    {
        return Rx2AndroidNetworking.post(ApiEndPoint.SEND_TOKEN)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addBodyParameter(ID_USER, String.valueOf(prefManager.getCurrentUserId()))
                .addBodyParameter(FB_TOKEN, token)
                .build()
                .getObjectObservable(RequestResponse.class);
    }

    @Override
    public Observable<List<RoomResponse>> getUnreadCount()
    {
        return Rx2AndroidNetworking.get(ApiEndPoint.UNREAD_API)
                .addHeaders(AUTH, prefManager.getAccessToken())
                .addPathParameter(ID_USER, String.valueOf(prefManager.getCurrentUserId()))
                .build()
                .getObjectObservable(RoomList.class)
                .map(roomList -> (List<RoomResponse>) new ArrayList<>(roomList.getResponses()));
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
}

