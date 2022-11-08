package com.damasingo.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAYkVNt5s:APA91bHqZsDurJdZ3rLF8HJWLcWkX9M3n5tEGYhyB_l03HMShRNcF722ahXoF42Y8hCmpthz1Mkv9B8k37HKAs26mRQ5uy_IwcR03zccAmmWzA06po54rKfz5pwV7ILaHvdFFa_gdxdy"
                    // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

