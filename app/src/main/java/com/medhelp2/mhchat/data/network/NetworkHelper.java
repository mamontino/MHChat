package com.medhelp2.mhchat.data.network;

import com.medhelp2.mhchat.data.model.CategoryList;
import com.medhelp2.mhchat.data.model.CenterList;
import com.medhelp2.mhchat.data.model.DateList;
import com.medhelp2.mhchat.data.model.DoctorInfoList;
import com.medhelp2.mhchat.data.model.DoctorList;
import com.medhelp2.mhchat.data.model.MessageList;
import com.medhelp2.mhchat.data.model.RequestResponse;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.data.model.SaleList;
import com.medhelp2.mhchat.data.model.ScheduleList;
import com.medhelp2.mhchat.data.model.ServiceList;
import com.medhelp2.mhchat.data.model.UserList;
import com.medhelp2.mhchat.data.model.VisitList;

import java.util.List;

import io.reactivex.Observable;

public interface NetworkHelper
{
    Observable<UserList> doLoginApiCall(String username, String password);

    Observable<RequestResponse> sendTokenToServerApiCall(String token);

    Observable<RequestResponse> sendReviewToServerApiCall(String message, int star);

    Observable<List<RoomResponse>> getRoomListApiCall();

    Observable<RoomResponse> getRoomByIdApiCall(int id);

    Observable<MessageList> getMessageListApiCall(int idChat);

    Observable<RequestResponse> sendMessageApiCall(int idRoom, String message);

    Observable<RequestResponse> readMessagesApiCall(int idRoom);

    Observable<List<RoomResponse>> getUnreadCountApiCall();

    Observable<ServiceList> getPriceApiCall(int idDoctor);

    Observable<ServiceList> getPriceApiCall();

    Observable<CategoryList> getCategoryApiCall();

    Observable<CategoryList> getCategoryApiCall(int idDoctor);

    Observable<CenterList> getCenterApiCall();

    Observable<DoctorList> getStaffApiCall();

    Observable<DoctorList> getStaffApiCall(int idService);

    Observable<SaleList> getSaleApiCall(String date);

    Observable<DoctorInfoList> getDoctorApiCall(int idDoctor);

    Observable<VisitList> getAllReceptionApiCall();

    Observable<VisitList> getActualReceptionApiCall();

    Observable<ScheduleList> getScheduleByDoctorApiCall(int idDoctor, String date,  int adm);

    Observable<ScheduleList> getScheduleByServiceApiCall(int idService, String date,  int adm);

    Observable<DateList> getCurrentDateApiCall();
}
