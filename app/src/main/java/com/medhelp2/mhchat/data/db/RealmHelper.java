package com.medhelp2.mhchat.data.db;

import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.data.model.MessageResponse;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.data.model.UserResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RealmHelper
{
    Single<List<UserResponse>> getAllRealmUser();

    Single<List<MessageResponse>> getAllRealmMessage();

    Single<List<MessageResponse>> getAllRealmReceivedMessage();

    Single<List<MessageResponse>> getRealmUnReadMessage(int idChatRoom);

    Single<List<MessageResponse>> getRealmMessageList(String date, int idChatRoom);

    Single<List<RoomResponse>> getAllRealmRoom();

    Single<UserResponse> getRealmUser(int id);

    Single<CenterResponse> getRealmCenter();

    Completable saveRealmCenter(CenterResponse response);

    Single<UserResponse> getRealmUser(String username);

    Single<MessageResponse> getRealmMessage(int id);

    Single<List<MessageResponse>> getRealmMessageList(int idRoom);

    Single<RoomResponse> getRealmChatRoom(int id);

    Single<UserResponse> getRealmUserResponse(String username, String password);

    Single<RoomResponse> getRealmChatRoom(String title);

    Completable saveRealmUser(UserResponse response);

    Completable saveRealmMessage(MessageResponse response, int idChat);

    Completable saveRealmMessages(List<MessageResponse> response, int idChat);

    Completable saveRealmRoom(List<RoomResponse> response);

    Completable saveRealmLoginUser(UserResponse response);

    Completable saveRealmStaff(List<Doctor> response);

    Single<List<Doctor>> getRealmStaff();

//    Completable setRealmReadMessages(int idRoom);

    Single<List<CategoryResponse>> getRealmCategory();

    Completable saveRealmCategory(List<CategoryResponse> response);

    Single<List<ServiceResponse>> getRealmPrice();

    Single<List<ServiceResponse>> getRealmPrice(int idCategory);

    Completable saveRealmPrice(List<ServiceResponse> response);
}
