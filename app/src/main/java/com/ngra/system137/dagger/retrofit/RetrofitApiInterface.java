package com.ngra.system137.dagger.retrofit;


import com.ngra.system137.models.ModelGetAddress;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RetrofitApiInterface {

    @FormUrlEncoded
    @POST("/token")
    Call<ModelToken> getToken
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("grant_type") String grant_type
            );


    @GET()
    Call<ModelGetAddress> getAddress(
            @Url String url
    );

}
