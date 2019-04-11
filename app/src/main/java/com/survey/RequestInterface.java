package com.survey;


import com.survey.login_model.LoginRequest;
import com.survey.login_model.LoginResponse;
import com.survey.question_response.QuestionRequestResponse;
import com.survey.question_response.QuestionResponse;
import com.survey.survey_response.SurveyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by debut_android on 12/1/2015.
 */
public interface RequestInterface {

    @POST("api/login.json")
    Call<LoginResponse> hitLogin(@Header("Accept") String accept, @Header("Content-Type") String content_type, @Body LoginRequest loginRequest);

    @GET("api/surveys.json")
    Call<SurveyResponse> getSurvey(@Header("Accept") String accept, @Header("Content-Type") String content_type);

    @GET("api/questions.json")
    Call<QuestionResponse> getQuestions(@Header("Accept") String accept, @Header("Content-Type") String content_type, @Query("survey_id") int survey_id);

    @POST("api/users_questions.json")
    Call<QuestionRequestResponse> submitQuestions(@Header("Accept") String accept, @Header("Content-Type") String content_type, @Body List<QuestionRequest> surveyRequest);
}