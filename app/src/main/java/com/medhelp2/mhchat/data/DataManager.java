package com.medhelp2.mhchat.data;

import android.content.Context;

import com.medhelp2.mhchat.data.db.RealmHelper;
import com.medhelp2.mhchat.data.model.CategoryList;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.CenterList;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.DateList;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.data.model.DoctorInfoList;
import com.medhelp2.mhchat.data.model.DoctorList;
import com.medhelp2.mhchat.data.model.MessageList;
import com.medhelp2.mhchat.data.model.MessageResponse;
import com.medhelp2.mhchat.data.model.RequestResponse;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.data.model.SaleList;
import com.medhelp2.mhchat.data.model.ScheduleList;
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
    private final PreferencesHelper prefManager;
    private final NetworkHelper networkManager;
    private final RealmHelper realmManager;

    @Inject
    DataManager(@PerApplication Context context,
            @PerApplication PreferencesHelper prefManager,
            @PerApplication NetworkHelper networkManager, @PerApplication RealmHelper realmManager)
    {
        this.context = context;
        this.prefManager = prefManager;
        this.networkManager = networkManager;
        this.realmManager = realmManager;
    }

    @Override
    public void setNetworkMode(int mode)
    {
        prefManager.setNetworkMode(mode);
    }

    @Override
    public int getNetworkMode()
    {
        return prefManager.getNetworkMode();
    }

    @Override
    public Observable<UserList> doLoginApiCall(String username, String password)
    {
        return networkManager.doLoginApiCall(username, password);
    }

    @Override
    public Observable<List<RoomResponse>> getRoomListApiCall()
    {
        return networkManager.getRoomListApiCall();
    }

    @Override
    public Observable<RoomResponse> getRoomByIdApiCall(int id)
    {
        return networkManager.getRoomByIdApiCall(id);
    }

    @Override
    public Observable<MessageList> getMessageListApiCall(int idChat, int idMessage)
    {
        return networkManager.getMessageListApiCall(idChat, idMessage);
    }

    @Override
    public Observable<RequestResponse> sendMessageApiCall(int idRoom, String message)
    {
        return networkManager.sendMessageApiCall(idRoom, message);
    }

    @Override
    public Observable<RequestResponse> sendTokenToServerApiCall(String token)
    {
        return networkManager.sendTokenToServerApiCall(token);
    }

    @Override
    public Observable<RequestResponse> sendReviewToServerApiCall(String message, int star)
    {
        return networkManager.sendReviewToServerApiCall(message, star);
    }

    @Override
    public Observable<List<RoomResponse>> getUnreadCountApiCall()
    {
        return networkManager.getUnreadCountApiCall();
    }

    @Override
    public Observable<ServiceList> getPriceApiCall(int idDoctor)
    {
        return networkManager.getPriceApiCall(idDoctor);
    }

    @Override
    public void loadFile(String image)
    {
        networkManager.loadFile(image);
    }

    @Override
    public Observable<ServiceList> getPriceApiCall()
    {
        return networkManager.getPriceApiCall();
    }

    @Override
    public Observable<CategoryList> getCategoryApiCall()
    {
        return networkManager.getCategoryApiCall();
    }

    @Override
    public Observable<CategoryList> getCategoryApiCall(int idDoctor)
    {
        return networkManager.getCategoryApiCall(idDoctor);
    }

    @Override
    public Observable<CenterList> getCenterApiCall()
    {
        return networkManager.getCenterApiCall();
    }

    @Override
    public Observable<DoctorList> getStaffApiCall()
    {
        return networkManager.getStaffApiCall();
    }

    @Override
    public Observable<DoctorInfoList> getStaffApiCall(int idSpec)
    {
        return networkManager.getStaffApiCall(idSpec);
    }

    @Override
    public Observable<SaleList> getSaleApiCall(String date)
    {
        return networkManager.getSaleApiCall(date);
    }

    @Override
    public Observable<DoctorInfoList> getDoctorApiCall(int idDoctor)
    {
        return networkManager.getDoctorApiCall(idDoctor);
    }

    @Override
    public Observable<VisitList> getAllReceptionApiCall()
    {
        return networkManager.getAllReceptionApiCall();
    }

    @Override
    public Observable<VisitList> getActualReceptionApiCall()
    {
        return networkManager.getActualReceptionApiCall();
    }

    @Override
    public Observable<ScheduleList> getScheduleByDoctorApiCall(int idDoctor, String date, int adm)
    {
        return networkManager.getScheduleByDoctorApiCall(idDoctor, date, adm);
    }

    @Override
    public Observable<ScheduleList> getScheduleByServiceApiCall(int idService, String date, int adm)
    {
        return networkManager.getScheduleByServiceApiCall(idService, date, adm);
    }

    @Override
    public Observable<DateList> getCurrentDateApiCall()
    {
        return networkManager.getCurrentDateApiCall();
    }

    @Override
    public Observable<RequestResponse> readMessagesApiCall(int idRoom)
    {
        return networkManager.readMessagesApiCall(idRoom);
    }

    @Override
    public int getCurrentUserId()
    {
        return prefManager.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(int userId)
    {
        prefManager.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName()
    {
        return prefManager.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName)
    {
        prefManager.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentName()
    {
        return prefManager.getCurrentName();
    }

    @Override
    public void setCurrentName(String name)
    {
        prefManager.setCurrentName(name);
    }

    @Override
    public String getFireBaseToken()
    {
        return prefManager.getFireBaseToken();
    }

    @Override
    public void setFireBaseToken(String token)
    {
        prefManager.setFireBaseToken(token);
    }

    @Override
    public String getCurrentPassword()
    {
        return prefManager.getCurrentPassword();
    }

    @Override
    public void setCurrentPassword(String password)
    {
        prefManager.setCurrentPassword(password);
    }

    @Override
    public void deleteCurrentPassword()
    {
        prefManager.deleteCurrentPassword();
    }

    @Override
    public void saveCurrentUser(int idUser, String name, String username)
    {
        prefManager.saveCurrentUser(idUser, name, username);
    }

    @Override
    public boolean hasNetwork()
    {
        return NetworkUtils.isNetworkConnected(context);
    }

    @Override
    public void addNotification(String notification)
    {
        prefManager.addNotification(notification);
    }

    @Override
    public String getNotifications()
    {
        return prefManager.getNotifications();
    }

    @Override
    public int getStartMode()
    {
        return prefManager.getStartMode();
    }

    @Override
    public void setStartMode(int mode)
    {
        prefManager.setStartMode(mode);
    }

    @Override
    public int getCurrentCenterId()
    {
        return prefManager.getCurrentCenterId();
    }

    @Override
    public void setCurrentCenterId(int id_center)
    {
        prefManager.setCurrentCenterId(id_center);
    }

    @Override
    public int getCurrentDoctorId()
    {
        return prefManager.getCurrentDoctorId();
    }

    @Override
    public void setCurrentDoctorId(int id_doctor)
    {
        prefManager.setCurrentDoctorId(id_doctor);
    }

    @Override
    public String getAccessToken()
    {
        return prefManager.getAccessToken();
    }

    @Override
    public void setAccessToken(String token)
    {
        prefManager.setAccessToken(token);
    }

    @Override
    public Single<List<UserResponse>> getAllRealmUser()
    {
        return realmManager.getAllRealmUser();
    }

    @Override
    public Single<List<MessageResponse>> getAllRealmMessage()
    {
        return realmManager.getAllRealmMessage();
    }

    @Override
    public Single<List<MessageResponse>> getAllRealmReceivedMessage()
    {
        return realmManager.getAllRealmReceivedMessage();
    }

    @Override
    public Single<List<RoomResponse>> getAllRealmRoom()
    {
        return realmManager.getAllRealmRoom();
    }

    @Override
    public Single<UserResponse> getRealmUser(int id)
    {
        return realmManager.getRealmUser(id);
    }

    @Override
    public Single<CenterResponse> getRealmCenter()
    {
        return realmManager.getRealmCenter();
    }

    @Override
    public Completable saveRealmCenter(CenterResponse response)
    {
        return realmManager.saveRealmCenter(response);
    }

    @Override
    public Single<MessageResponse> getRealmMessage(int id)
    {
        return realmManager.getRealmMessage(id);
    }

    @Override
    public Single<List<MessageResponse>> getRealmMessageList(int id)
    {
        return realmManager.getRealmMessageList(id);
    }

    @Override
    public Single<MessageResponse> getLastMessage(int idRoom)
    {
        return realmManager.getLastMessage(idRoom);
    }

    @Override
    public Single<RoomResponse> getRealmChatRoom(int id)
    {
        return realmManager.getRealmChatRoom(id);
    }

    @Override
    public Single<UserResponse> getRealmUser(String username)
    {
        return realmManager.getRealmUser(username);
    }

    @Override
    public Single<UserResponse> getRealmUserResponse(String username, String password)
    {
        return realmManager.getRealmUserResponse(username, password);
    }

    @Override
    public Single<RoomResponse> getRealmChatRoom(String title)
    {
        return realmManager.getRealmChatRoom(title);
    }

    @Override
    public Completable saveRealmUser(UserResponse response)
    {
        return realmManager.saveRealmUser(response);
    }

    @Override
    public Completable saveRealmMessage(MessageResponse response, int idChat)
    {
        return realmManager.saveRealmMessage(response, idChat);
    }

    @Override
    public Completable saveRealmMessages(List<MessageResponse> response, int idChat)
    {
        return realmManager.saveRealmMessages(response, idChat);
    }

    @Override
    public Completable saveRealmRoom(List<RoomResponse> response)
    {
        return realmManager.saveRealmRoom(response);
    }

    @Override
    public Completable saveRealmLoginUser(UserResponse response)
    {
        return realmManager.saveRealmLoginUser(response);
    }

    @Override
    public Completable saveRealmStaff(List<Doctor> response)
    {
        return realmManager.saveRealmStaff(response);
    }

    @Override
    public Single<List<Doctor>> getRealmStaff()
    {
        return realmManager.getRealmStaff();
    }

    @Override
    public Single<List<CategoryResponse>> getRealmCategory()
    {
        return realmManager.getRealmCategory();
    }

    @Override
    public Completable saveRealmCategory(List<CategoryResponse> response)
    {
        return realmManager.saveRealmCategory(response);
    }

    @Override
    public Single<List<ServiceResponse>> getRealmPrice()
    {
        return realmManager.getRealmPrice();
    }

    @Override
    public Single<List<ServiceResponse>> getRealmPrice(int idCategory)
    {
        return realmManager.getRealmPrice(idCategory);
    }

    @Override
    public Completable saveRealmPrice(List<ServiceResponse> response)
    {
        return realmManager.saveRealmPrice(response);
    }

    @Override
    public Single<List<MessageResponse>> getRealmUnReadMessage(int idChatRoom)
    {
        return realmManager.getRealmUnReadMessage(idChatRoom);
    }

    @Override
    public Single<List<MessageResponse>> getRealmMessageList(String date, int idChatRoom)
    {
        return realmManager.getRealmMessageList(date, idChatRoom);
    }
}
