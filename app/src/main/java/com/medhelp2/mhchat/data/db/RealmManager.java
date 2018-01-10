package com.medhelp2.mhchat.data.db;

import android.content.Context;

import com.medhelp2.mhchat.data.model.AppNames;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.data.model.MessageResponse;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.data.model.UserResponse;
import com.medhelp2.mhchat.di.scope.PerApplication;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;
import timber.log.Timber;

import static com.medhelp2.mhchat.data.model.AppNames.ID_ROOM;
import static com.medhelp2.mhchat.data.model.AppNames.TIMESTAMP;

@PerApplication
public class RealmManager implements RealmHelper
{
    private Context context;

    private RealmConfiguration config = new RealmConfig().getConfig();

    @Inject
    public RealmManager(@PerApplication Context context)
    {
        this.context = context;
    }

    @Override
    public Single<List<UserResponse>> getAllRealmUser()
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<UserResponse> responses = realm.copyFromRealm(realm
                .where(UserResponse.class)
                .findAllSorted(AppNames.USERNAME, Sort.ASCENDING));
        realm.close();
        Timber.d("getAllRealmUser");
        return Single.just(responses);
    }

    @Override
    public Single<List<MessageResponse>> getAllRealmMessage()
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<MessageResponse> responses = realm.copyFromRealm(realm
                .where(MessageResponse.class)
                .findAllSorted(AppNames.ID_MESSAGE, Sort.ASCENDING));
        realm.close();
        Timber.d("getAllRealmMessage");
        return Single.just(responses);
    }

    @Override
    public Single<List<MessageResponse>> getAllRealmReceivedMessage()
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<MessageResponse> responses = realm.copyFromRealm(realm
                .where(MessageResponse.class)
                .findAllSorted(AppNames.ID_MESSAGE, Sort.ASCENDING));
        realm.close();
        Timber.d("getAllRealmReceivedMessage");
        return Single.just(responses);
    }

    @Override
    public Single<List<RoomResponse>> getAllRealmRoom()
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<RoomResponse> responses = realm.copyFromRealm(realm
                .where(RoomResponse.class)
                .findAllSorted(AppNames.ID_ROOM, Sort.ASCENDING));
        realm.close();
        Timber.d("getAllRealmRoom");
        return Single.just(responses);
    }

    @Override
    public Single<UserResponse> getRealmUser(int id)
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        UserResponse response = null;
        UserResponse realmResponse = realm
                .where(UserResponse.class)
                .equalTo(AppNames.ID_USER, id)
                .findFirst();
        if (realmResponse != null)
        {
            response = realm.copyFromRealm(realmResponse);
        }
        realm.close();
        Timber.d("getRealmUser");
        assert response != null;
        return Single.just(response);
    }

    @Override
    public Single<CenterResponse> getRealmCenter()
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        CenterResponse response = null;
        CenterResponse realmResponse = realm
                .where(CenterResponse.class)
                .findFirst();
        if (realmResponse != null)
        {
            response = realm.copyFromRealm(realmResponse);
        }
        realm.close();
        Timber.d("getCenterResponse");
        assert response != null;
        return Single.just(response);
    }

    @Override
    public Completable saveRealmCenter(CenterResponse response)
    {
        return Completable.fromAction(() ->
        {
            Realm.init(context);
            Realm realm = Realm.getInstance(config);
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();
            realm.close();
            Timber.d("saveRealmReceivedMessages");
            Realm.compactRealm(config);
        });
    }

    @Override
    public Single<MessageResponse> getRealmMessage(int id)
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        MessageResponse response = null;
        MessageResponse realmResponse = realm
                .where(MessageResponse.class)
                .equalTo(AppNames.ID_MESSAGE, id)
                .findFirst();
        if (realmResponse != null)
        {
            response = realm.copyFromRealm(realmResponse);
        }
        realm.close();
        Timber.d("getRealmMessage");
        assert response != null;
        return Single.just(response);
    }

    @Override
    public Single<List<MessageResponse>> getRealmMessageList(int idRoom)
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<MessageResponse> responses = realm.copyFromRealm(realm
                .where(MessageResponse.class)
                .equalTo(AppNames.ID_ROOM, idRoom)
                .findAllSorted(AppNames.ID_ROOM, Sort.ASCENDING));
        realm.close();
        Timber.d("getRealmMessageList");
        return Single.just(responses);
    }

    @SuppressWarnings("all")
    @Override
    public Single<MessageResponse> getLastMessage(int idRoom)
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);

        try
        {
            MessageResponse responses = realm.copyFromRealm(realm
                    .where(MessageResponse.class)
                    .equalTo(AppNames.ID_ROOM, idRoom)
                    .findAllSorted(AppNames.ID_MESSAGE, Sort.DESCENDING)
                    .first());
            realm.close();
            Timber.e("id_message: " + responses.getIdMessage());
            return Single.just(responses);
        } catch (Exception e)
        {
            realm.close();
            Timber.e("id_message: 0" + e.getMessage());
            return Single.just(new MessageResponse(0));
        }
    }

    @Override
    public Single<RoomResponse> getRealmChatRoom(int idRoom)
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        RoomResponse response = null;
        RoomResponse realmResponse = realm
                .where(RoomResponse.class)
                .equalTo(AppNames.ID_ROOM, idRoom)
                .findFirst();
        if (realmResponse != null)
        {
            response = realm.copyFromRealm(realmResponse);
        }
        realm.close();
        Timber.d("getRealmChatRoom");
        assert response != null;
        return Single.just(response);
    }

    @Override
    public Single<UserResponse> getRealmUser(String username)
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        UserResponse response = null;
        UserResponse realmResponse = realm
                .where(UserResponse.class)
                .equalTo(AppNames.USERNAME, username)
                .findFirst();
        if (realmResponse != null)
        {
            response = realm.copyFromRealm(realmResponse);
        }
        realm.close();
        Timber.d("getRealmUser");
        assert response != null;
        return Single.just(response);
    }

    @Override
    public Single<UserResponse> getRealmUserResponse(String username, String password)
    {
        try
        {
            Realm.init(context);
            Realm realm = Realm.getInstance(config);
            UserResponse response = null;
            UserResponse realmResponse = realm
                    .where(UserResponse.class)
                    .equalTo(AppNames.USERNAME, username)
                    .findFirst();
            if (realmResponse != null)
            {
                response = realm.copyFromRealm(realmResponse);
            }
            Timber.d("getRealmUserResponse");
            assert response != null;
            return Single.just(response);
        } catch (Exception e)
        {
            Timber.e("Ошибка получения данных пользователя из локального хранилища: " + e.getMessage());
            return Single.error(e);
        }
    }

    @Override
    public Single<RoomResponse> getRealmChatRoom(String title)
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        RoomResponse response = null;
        RoomResponse realmResponse = realm
                .where(RoomResponse.class)
                .equalTo(AppNames.TITLE, title)
                .findFirst();
        if (realmResponse != null)
        {
            response = realm.copyFromRealm(realmResponse);
        }
        realm.close();
        Timber.d("getRealmChatRoom");
        assert response != null;
        return Single.just(response);
    }

    @Override
    public Completable saveRealmUser(UserResponse response)
    {
        Realm.init(context);
        return Completable.fromAction(() ->
        {
            Realm realm = Realm.getInstance(config);
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();
            realm.close();
            Timber.d("saveRealmUser");
            Realm.compactRealm(config);
        });
    }

    @Override
    public Completable saveRealmMessage(MessageResponse response, int idChat)
    {
        return Completable.fromAction(() ->
        {
            Realm.init(context);
            Realm realm = Realm.getInstance(config);
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();
            realm.close();
            Timber.d("saveRealmMessage");
            Realm.compactRealm(config);
        });
    }

    @Override
    public Completable saveRealmMessages(List<MessageResponse> response, int idChat)
    {
        return Completable.fromAction(() ->
        {
            Realm.init(context);
            Realm realm = Realm.getInstance(config);
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();
            realm.close();
            Timber.d("saveRealmReceivedMessages");
            Realm.compactRealm(config);
        });
    }

    @Override
    public Completable saveRealmRoom(List<RoomResponse> response)
    {
        return Completable.fromAction(() ->
        {
            Realm.init(context);
            Realm realm = Realm.getInstance(config);
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();
            realm.close();
            Timber.d("saveRealmRoom");
            Realm.compactRealm(config);
        });
    }

    @Override
    public Completable saveRealmLoginUser(UserResponse response)
    {
        try
        {
            return Completable.fromAction(() ->
            {
                Realm.init(context);
                Realm realm = Realm.getInstance(config);
                realm.beginTransaction();
                realm.insertOrUpdate(response);
                realm.commitTransaction();
                realm.close();
                Timber.d("Сохранение пользователя прошло успешно");
                Realm.compactRealm(config);
            });
        } catch (Exception e)
        {
            Timber.e("Сохранение пользователя произошло с ошибкой: " + e.getMessage());
        }
        return Completable.complete();
    }

    @Override
    public Completable saveRealmStaff(List<Doctor> response)
    {
        try
        {
            return Completable.fromAction(() ->
            {
                Realm.init(context);
                Realm realm = Realm.getInstance(config);
                realm.beginTransaction();
                realm.insertOrUpdate(response);
                realm.commitTransaction();
                realm.close();
                Timber.d("Сохранение списка докторов прошло успешно");
                Realm.compactRealm(config);
            });
        } catch (Exception e)
        {
            Timber.e("Сохранение списка докторов произошло с ошибкой: " + e.getMessage());
        }
        return Completable.complete();
    }

    @Override
    public Single<List<Doctor>> getRealmStaff()
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<Doctor> responses = realm.copyFromRealm(realm
                .where(Doctor.class)
                .findAll());
        realm.close();
        Timber.d("getRealmUnReadMessage");
        return Single.just(responses);
    }

    @Override
    public Single<List<MessageResponse>> getRealmUnReadMessage(int idChatRoom)
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<MessageResponse> responses = realm.copyFromRealm(realm
                .where(MessageResponse.class)
                .equalTo(AppNames.ID_ROOM, idChatRoom)
                .equalTo(AppNames.IS_READ, false)
                .findAllSorted(AppNames.ID_ROOM, Sort.ASCENDING));
        realm.close();
        Timber.d("getRealmUnReadMessage");
        return Single.just(responses);
    }

    @Override
    public Single<List<MessageResponse>> getRealmMessageList(String date, int idChatRoom)
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<MessageResponse> responses = realm.copyFromRealm(realm
                .where(MessageResponse.class)
                .equalTo(ID_ROOM, idChatRoom)
                .equalTo(TIMESTAMP, date)
                .findAllSorted(AppNames.TIMESTAMP, Sort.ASCENDING));
        realm.close();
        Timber.d("getRealmMessageList");
        return Single.just(responses);
    }

    //    @Override
    //    public Completable setRealmReadMessages(int idRoom)
    //    {
    //        try
    //        {
    //            return Completable.fromAction(() ->
    //            {
    //                Realm.init(context);
    //                Realm realm = Realm.getInstance(config);
    //
    //                List<RoomResponse> response = realm.copyFromRealm(realm
    //                        .where(RoomResponse.class)
    //                        .equalTo(ID_ROOM, idRoom)
    //                        .findAll());
    //
    //                List<RoomResponse> response1 = new ArrayList<>();
    //
    //                for (int i = 0; i < response.size() + 1; i++)
    //                {
    //                    try
    //                    {
    //                        RoomResponse room = new RoomResponse(idRoom, response.get(i).getIdCenter(), response.get(i).getSaleDescription());
    //                        response1.add(room);
    //                    } catch (Exception e)
    //                    {
    //                        e.printStackTrace();
    //                    }
    //                }
    //
    //                realm.beginTransaction();
    //                realm.insertOrUpdate(response1);
    //                realm.commitTransaction();
    //                realm.close();
    //                Timber.d("Сохранение пользователя прошло успешно");
    //                Realm.compactRealm(config);
    //            });
    //        } catch (Exception e)
    //        {
    //            Timber.e("Сохранение пользователя произошло с ошибкой: " + e.getMessage());
    //            return Completable.complete();
    //        }
    //    }

    @Override
    public Single<List<CategoryResponse>> getRealmCategory()
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<CategoryResponse> responses = realm.copyFromRealm(realm
                .where(CategoryResponse.class)
                .findAll());
        realm.close();
        Timber.d("getRealmPrice");
        return Single.just(responses);
    }

    @Override
    public Completable saveRealmCategory(List<CategoryResponse> response)
    {
        return Completable.fromAction(() ->
        {
            Realm.init(context);
            Realm realm = Realm.getInstance(config);
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();
            realm.close();
            Timber.d("saveRealmCategory");
            Realm.compactRealm(config);
        });
    }

    @Override
    public Single<List<ServiceResponse>> getRealmPrice()
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<ServiceResponse> responses = realm.copyFromRealm(realm
                .where(ServiceResponse.class)
                .findAll());
        realm.close();
        Timber.d("getRealmPrice");
        return Single.just(responses);
    }

    @Override
    public Single<List<ServiceResponse>> getRealmPrice(int idCategory)
    {
        Realm.init(context);
        Realm realm = Realm.getInstance(config);
        List<ServiceResponse> responses = realm.copyFromRealm(realm
                .where(ServiceResponse.class)
                .equalTo("id", idCategory)
                .findAll());
        realm.close();
        Timber.d("getRealmPrice (int idCategory)");
        return Single.just(responses);
    }

    @Override
    public Completable saveRealmPrice(List<ServiceResponse> response)
    {
        return Completable.fromAction(() ->
        {
            Realm.init(context);
            Realm realm = Realm.getInstance(config);
            realm.beginTransaction();
            realm.insertOrUpdate(response);
            realm.commitTransaction();
            realm.close();
            Timber.d("saveRealmPrice");
            Realm.compactRealm(config);
        });
    }
}
