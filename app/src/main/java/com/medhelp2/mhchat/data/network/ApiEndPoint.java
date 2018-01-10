package com.medhelp2.mhchat.data.network;

interface ApiEndPoint
{
    String BASE_URL = "http://89.189.183.200:8648/api/v1/";

    //    POST AUTH

    String LOGIN = BASE_URL + "login";

    String SEND_TOKEN = BASE_URL + "send/token";

    String SEND_MESSAGE = BASE_URL + "send/message";

    //    GET ROOM

    String ROOM_BY_ID = BASE_URL + "rooms/{id_user}/{id_room}";

    String ROOM_LIST = BASE_URL + "rooms/{id_user}";

    //    GET MESSAGE

    String MESSAGE_LIST = BASE_URL + "message/{id_room}/{id_message}";

    String UNREAD_API = BASE_URL + "unread/{id_room}/{id_user}";

    String READ_MESSAGE = BASE_URL + "read/{id_room}/{id_user}";

    //    GET DOCTORS FROM CENTER

    String DOCTORS = BASE_URL + "doctors/{id_center}";

    String DOCTOR_BY_ID = BASE_URL + "doctors/{id_center}/{id_doctor}";

    String DOCTORS_BY_SPEC = BASE_URL + "staff/{id_center}/{id_spec}";

    //    GET CATEGORY FROM CENTER

    String CATEGORY = BASE_URL + "category/{id_center}";

    String CATEGORY_BY_ID_DOCTOR = BASE_URL + "category/doctor/{id_center}/{id_doctor}";

    //    GET PRICE FROM CENTER

    String PRICE = BASE_URL + "services/{id_center}";

    String PRICE_BY_DOCTOR = BASE_URL + "services/doctor/{id_center}/{id_doctor}";

    //    GET CENTER INFO

    String CENTER = BASE_URL + "centres/{id_center}";

    //    GET VISITS TO CENTER

    String VISITS = BASE_URL + "visits/{id_center}/{id_client}";

    //    GET SALE BY CENTER

    String SALE = BASE_URL + "sale/{id_center}/{date}";

    //    GET SALE BY CENTER

    String SCHEDULE_DOCTOR = BASE_URL + "schedule/doctor/{id_center}/{id_doctor}/{date}/{adm}";

    String SCHEDULE_SERVICE = BASE_URL + "schedule/service/{id_center}/{id_service}/{date}/{adm}";

    String CURRENT_DATE = BASE_URL + "date";

    //     SEND REVIEW

    String SEND_REVIEW = BASE_URL + "send/review";
}
