package com.medhelp2.mhchat.data;


import android.content.Context;

import com.medhelp2.mhchat.data.db.RealmHelper;
import com.medhelp2.mhchat.data.model.CategoryList;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.CenterList;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.DateList;
import com.medhelp2.mhchat.data.model.ScheduleList;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.data.model.DoctorInfoList;
import com.medhelp2.mhchat.data.model.DoctorList;
import com.medhelp2.mhchat.data.model.MessageList;
import com.medhelp2.mhchat.data.model.MessageResponse;
import com.medhelp2.mhchat.data.model.RequestResponse;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.data.model.SaleList;
import com.medhelp2.mhchat.data.model.ServiceList;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.data.model.UserList;
import com.medhelp2.mhchat.data.model.UserResponse;
import com.medhelp2.mhchat.data.model.VisitList;
import com.medhelp2.mhchat.data.network.NetworkHelper;
import com.medhelp2.mhchat.data.pref.PreferencesHelper;
import com.medhelp2.mhchat.di.scope.PerApplication;
import com.medhelp2.mhchat.utils.main.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@PerApplication
public class DataManager implements DataHelper
{
    private final Context context;
    private final PreferencesHelper preferencesHelper;
    private final NetworkHelper networkHelper;
    private final RealmHelper realmHelper;

    @Inject
    DataManager(@PerApplication Context context,
            @PerApplication PreferencesHelper preferencesHelper,
            @PerApplication NetworkHelper networkHelper, @PerApplication RealmHelper realmHelper)
    {
        this.context = context;
        this.preferencesHelper = preferencesHelper;
        this.networkHelper = networkHelper;
        this.realmHelper = realmHelper;
    }

    @Override
    public Observable<UserList> doLogin(String username, String password)
    {
        return networkHelper.doLogin(username, password);
    }

    @Override
    public Observable<List<RoomResponse>> getRoomList()
    {
        return networkHelper.getRoomList();
    }

    @Override
    public Observable<RoomResponse> getRoomById(int id)
    {
        return networkHelper.getRoomById(id);
    }

    @Override
    public Observable<MessageList> getMessageList(int idChat)
    {
        return networkHelper.getMessageList(idChat);
    }

    @Override
    public Observable<RequestResponse> sendMessage(int idRoom, String message)
    {
        return networkHelper.sendMessage(idRoom, message);
    }

    @Override
    public Observable<RequestResponse> sendTokenToServer(String token)
    {
        return networkHelper.sendTokenToServer(token);
    }

    @Override
    public Observable<List<RoomResponse>> getUnreadCount()
    {
        return networkHelper.getUnreadCount();
    }

    @Override
    public Observable<ServiceList> getPriceApiCall()
    {
        return networkHelper.getPriceApiCall();
    }

    @Override
    public Observable<CategoryList> getCategoryApiCall()
    {
        return networkHelper.getCategoryApiCall();
    }

    @Override
    public Observable<CenterList> getCenterApiCall()
    {
        return networkHelper.getCenterApiCall();
    }

    @Override
    public Observable<DoctorList> getStaffApiCall()
    {
        return networkHelper.getStaffApiCall();
    }

    @Override
    public Observable<SaleList> getSaleApiCall()
    {
        return networkHelper.getSaleApiCall();
    }

    @Override
    public Observable<DoctorInfoList> getDoctorApiCall(int idDoctor)
    {
        return networkHelper.getDoctorApiCall(idDoctor);
    }

    @Override
    public Observable<VisitList> getAllReceptionApiCall()
    {
        return networkHelper.getAllReceptionApiCall();
    }

    @Override
    public Observable<VisitList> getActualReceptionApiCall()
    {
        return networkHelper.getActualReceptionApiCall();
    }

    @Override
    public Observable<ScheduleList> getScheduleByDoctor(int idDoctor, String date, int adm)
    {
        return networkHelper.getScheduleByDoctor(idDoctor, date, adm);
    }

    @Override
    public Observable<DateList> getCurrentDate()
    {
        return networkHelper.getCurrentDate();
    }

    @Override
    public Observable<RequestResponse> readMessages(int idRoom)
    {
        return networkHelper.readMessages(idRoom);
    }

    @Override
    public int getCurrentUserId()
    {
        return preferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(int userId)
    {
        preferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName()
    {
        return preferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName)
    {
        preferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentName()
    {
        return preferencesHelper.getCurrentName();
    }

    @Override
    public void setCurrentName(String name)
    {
        preferencesHelper.setCurrentName(name);
    }

    @Override
    public String getFireBaseToken()
    {
        return preferencesHelper.getFireBaseToken();
    }

    @Override
    public void setFireBaseToken(String token)
    {
        preferencesHelper.setFireBaseToken(token);
    }

    @Override
    public String getCurrentPassword()
    {
        return preferencesHelper.getCurrentPassword();
    }

    @Override
    public void setCurrentPassword(String password)
    {
        preferencesHelper.setCurrentPassword(password);
    }

    @Override
    public void deleteCurrentPassword()
    {
        preferencesHelper.deleteCurrentPassword();
    }

    @Override
    public void saveCurrentUser(int idUser, String name, String username)
    {
        preferencesHelper.saveCurrentUser(idUser, name, username);
    }

    @Override
    public boolean checkNetwork()
    {
        return NetworkUtils.isNetworkConnected(context);
    }

    @Override
    public void addNotification(String notification)
    {
        preferencesHelper.addNotification(notification);
    }

    @Override
    public String getNotifications()
    {
        return preferencesHelper.getNotifications();
    }

    @Override
    public int getCurrentCenterId()
    {
        return preferencesHelper.getCurrentCenterId();
    }

    @Override
    public void setCurrentCenterId(int id_center)
    {
        preferencesHelper.setCurrentCenterId(id_center);
    }

    @Override
    public int getCurrentDoctorId()
    {
        return preferencesHelper.getCurrentDoctorId();
    }

    @Override
    public void setCurrentDoctorId(int id_doctor)
    {
        preferencesHelper.setCurrentDoctorId(id_doctor);
    }

    @Override
    public String getAccessToken()
    {
        return preferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String token)
    {
        preferencesHelper.setAccessToken(token);
    }

    @Override
    public Single<List<UserResponse>> getAllRealmUser()
    {
        return realmHelper.getAllRealmUser();
    }

    @Override
    public Single<List<MessageResponse>> getAllRealmMessage()
    {
        return realmHelper.getAllRealmMessage();
    }

    @Override
    public Single<List<MessageResponse>> getAllRealmReceivedMessage()
    {
        return realmHelper.getAllRealmReceivedMessage();
    }

    @Override
    public Single<List<RoomResponse>> getAllRealmRoom()
    {
        return realmHelper.getAllRealmRoom();
    }

    @Override
    public Single<UserResponse> getRealmUser(int id)
    {
        return realmHelper.getRealmUser(id);
    }

    @Override
    public Single<CenterResponse> getRealmCenter()
    {
        return realmHelper.getRealmCenter();
    }

    @Override
    public Completable saveRealmCenter(CenterResponse response)
    {
        return realmHelper.saveRealmCenter(response);
    }

    @Override
    public Single<MessageResponse> getRealmMessage(int id)
    {
        return realmHelper.getRealmMessage(id);
    }

    @Override
    public Single<List<MessageResponse>> getRealmMessageList(int id)
    {
        return realmHelper.getRealmMessageList(id);
    }

    @Override
    public Single<RoomResponse> getRealmChatRoom(int id)
    {
        return realmHelper.getRealmChatRoom(id);
    }

    @Override
    public Single<UserResponse> getRealmUser(String username)
    {
        return realmHelper.getRealmUser(username);
    }

    @Override
    public Single<UserResponse> getRealmUserResponse(String username, String password)
    {
        return realmHelper.getRealmUserResponse(username, password);
    }

    @Override
    public Single<RoomResponse> getRealmChatRoom(String title)
    {
        return realmHelper.getRealmChatRoom(title);
    }

    @Override
    public Completable saveRealmUser(UserResponse response)
    {
        return realmHelper.saveRealmUser(response);
    }

    @Override
    public Completable saveRealmMessage(MessageResponse response, int idChat)
    {
        return realmHelper.saveRealmMessage(response, idChat);
    }

    @Override
    public Completable saveRealmMessages(List<MessageResponse> response, int idChat)
    {
        return realmHelper.saveRealmMessages(response, idChat);
    }

    @Override
    public Completable saveRealmRoom(List<RoomResponse> response)
    {
        return realmHelper.saveRealmRoom(response);
    }

    @Override
    public Completable saveRealmLoginUser(UserResponse response)
    {
        return realmHelper.saveRealmLoginUser(response);
    }

    @Override
    public Completable saveRealmStaff(List<Doctor> response)
    {
        return realmHelper.saveRealmStaff(response);
    }

    @Override
    public Single<List<Doctor>> getRealmStaff()
    {
        return realmHelper.getRealmStaff();
    }

    @Override
    public Single<List<CategoryResponse>> getRealmCategory()
    {
        return realmHelper.getRealmCategory();
    }

    @Override
    public Completable saveRealmCategory(List<CategoryResponse> response)
    {
        return realmHelper.saveRealmCategory(response);
    }

    @Override
    public Single<List<ServiceResponse>> getRealmPrice()
    {
        return realmHelper.getRealmPrice();
    }

    @Override
    public Single<List<ServiceResponse>> getRealmPrice(int idCategory)
    {
        return realmHelper.getRealmPrice(idCategory);
    }

    @Override
    public Completable saveRealmPrice(List<ServiceResponse> response)
    {
        return realmHelper.saveRealmPrice(response);
    }

    @Override
    public Single<List<MessageResponse>> getRealmUnReadMessage(int idChatRoom)
    {
        return realmHelper.getRealmUnReadMessage(idChatRoom);
    }

    @Override
    public Single<List<MessageResponse>> getRealmMessageList(String date, int idChatRoom)
    {
        return realmHelper.getRealmMessageList(date, idChatRoom);
    }
}
