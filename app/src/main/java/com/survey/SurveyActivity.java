package com.survey;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.survey.survey_response.Data;
import com.survey.survey_response.SurveyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends AppCompatActivity implements Callback<SurveyResponse> {

    private RecyclerView surveyList;
    private ProgressDialog dialog;
    private List<Data> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        surveyList = findViewById(R.id.surveyList);
        getSurveys();
    }

    private void getSurveys() {
        dialog = new ProgressDialog(SurveyActivity.this);
        dialog.setMessage("Getting Surveys..");
        dialog.show();

        Call<SurveyResponse> surveyResponseCall = new Services().getRetrofit("http://3.16.11.132/survey_system/").getSurvey("application/json", "application/json");
        surveyResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<SurveyResponse> call, Response<SurveyResponse> response) {
        dialog.dismiss();
        if (response.body() != null && response.body().getResponse().getStatus().contains("success")) {
            dataList = response.body().getResponse().getData();
            setAdapter();
        }
    }

    private void setAdapter() {
        surveyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SurveyAdapter surveyAdapter = new SurveyAdapter(dataList, this);
        surveyList.setAdapter(surveyAdapter);
    }

    @Override
    public void onFailure(Call<SurveyResponse> call, Throwable t) {
        dialog.dismiss();

    }
}
