package com.medhelp2.mhchat.data.network;


interface ApiEndPoint
{
    String BASE_URL = "http://192.168.0.110/api/v1/";

    //    POST AUTH

    String LOGIN = BASE_URL + "login";

    String SEND_TOKEN = BASE_URL + "send/token";

    String SEND_MESSAGE = BASE_URL + "send/message";

    //    GET ROOM

    String ROOM_BY_ID = BASE_URL + "rooms/{id_user}/{id_room}";

    String ROOM_LIST = BASE_URL + "rooms/{id_user}";

    //    GET MESSAGE

    String MESSAGE_LIST = BASE_URL + "message/{id_room}";

    String UNREAD_API = BASE_URL + "unread/{id_room}/{id_user}";

    String READ_MESSAGE = BASE_URL + "read/{id_room}/{id_user}";

    //    GET DOCTORS FROM CENTER

    String DOCTORS = BASE_URL + "doctors/{id_center}";

    String DOCTOR_BY_ID = BASE_URL + "doctors/{id_center}/{id_doctor}";

    //    GET CATEGORY FROM CENTER

    String CATEGORY = BASE_URL + "category/{id_center}";

    //    GET PRICE FROM CENTER

    String PRICE = BASE_URL + "services/{id_center}";

    //    GET CENTER INFO

    String CENTER = BASE_URL + "centres/{id_center}";

    //    GET VISITS TO CENTER

    String VISITS = BASE_URL + "visits/{id_center}/{id_client}";
}
