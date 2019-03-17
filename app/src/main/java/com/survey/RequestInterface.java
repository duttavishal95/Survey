package com.survey;


import com.survey.login_model.LoginRequest;
import com.survey.login_model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by debut_android on 12/1/2015.
 */
public interface RequestInterface {

    @POST("api/login.json")
    Call<LoginResponse> hitLogin(@Header("Accept") String accept, @Header("Content-Type") String content_type, @Body LoginRequest loginRequest);
}