package com.medhelp2.mhchat.data.network;

import com.medhelp2.mhchat.data.model.CategoryList;
import com.medhelp2.mhchat.data.model.CenterList;
import com.medhelp2.mhchat.data.model.DoctorInfo;
import com.medhelp2.mhchat.data.model.DoctorList;
import com.medhelp2.mhchat.data.model.MessageList;
import com.medhelp2.mhchat.data.model.RequestResponse;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.data.model.ServiceList;
import com.medhelp2.mhchat.data.model.UserList;
import com.medhelp2.mhchat.data.model.VisitList;

import java.util.List;

import io.reactivex.Observable;

public interface NetworkHelper
{
    Observable<UserList> doLogin(String username, String password);

    Observable<RequestResponse> sendTokenToServer(String token);

    Observable<List<RoomResponse>> getRoomList();

    Observable<RoomResponse> getRoomById(int id);

    Observable<MessageList> getMessageList(int idChat);

    Observable<RequestResponse> sendMessage(int idRoom, String message);

    Observable<RequestResponse> readMessages(int idRoom);

    Observable<List<RoomResponse>> getUnreadCount();

    Observable<ServiceList> getPriceApiCall();

    Observable<CategoryList> getCategoryApiCall();

    Observable<CenterList> getCenterApiCall();

    Observable<DoctorList> getStaffApiCall();

    Observable<DoctorInfo> getDoctorApiCall(int idDoctor);

    Observable<VisitList> getAllReceptionApiCall();

    Observable<VisitList> getActualReceptionApiCall();
}
