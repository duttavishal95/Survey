package com.survey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.survey.survey_response.Data;
import com.survey.survey_response.SurveyRequestResponse;
import com.survey.survey_response.SurveyResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener, Callback<SurveyResponse> {

    private Button submit;
    private RecyclerView surveyList;
    private ProgressDialog dialog;
    private List<Data> dataList;
    private SurveyAdapter surveyAdapter;
    private List<SurveyRequest> surveyRequests = new ArrayList<>();
    private int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        surveyList = findViewById(R.id.surveyList);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        if (getIntent().getExtras() != null) {
            userId = getIntent().getExtras().getInt("user_id");
        }
        dialog = new ProgressDialog(SurveyActivity.this);
        dialog.setMessage("Getting questions..");
        dialog.show();
        getSurvey();
    }

    private void getSurvey() {
        Call<SurveyResponse> surveyResponseCall = new Services().getRetrofit("http://3.16.11.132/survey_system/").getSurvey("application/json", "application/json");
        surveyResponseCall.enqueue(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (dataList != null && dataList.size() > 0) {
                    boolean isEmpty = false;
                    surveyRequests.clear();
                    for (int i = 0; i < dataList.size(); i++) {
                        if (TextUtils.isEmpty(dataList.get(i).getAnswer())) {
                            isEmpty = true;
                            int questionCount = i + 1;
                            Toast.makeText(getApplicationContext(), "question " + questionCount + " is not answered", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            SurveyRequest surveyRequest = new SurveyRequest();
                            surveyRequest.setUser_id(userId);
                            surveyRequest.setQuestion_id(dataList.get(i).getId());
                            surveyRequest.setAnswer(dataList.get(i).getAnswer());
                            surveyRequests.add(surveyRequest);
                        }
                    }
                    if (isEmpty) {
                        Toast.makeText(getApplicationContext(), "You have not answered all the questions.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    submitSurvey();
                } else {

                }
                break;
        }
    }

    private void submitSurvey() {
        dialog.setMessage("Submitting survey...");
        dialog.show();
        Call<SurveyRequestResponse> submitCall = new Services().getRetrofit("http://3.16.11.132/survey_system/").submitSurvey("application/json", "application/json", surveyRequests);
        submitCall.enqueue(new Callback<SurveyRequestResponse>() {
            @Override
            public void onResponse(Call<SurveyRequestResponse> call, Response<SurveyRequestResponse> response) {
                if (response.body() != null && response.body().getResponse().getStatus().equals("success")) {
                    Intent intent = new Intent(getApplicationContext(), FinalScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<SurveyRequestResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void showMessageToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
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
        surveyList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        surveyAdapter = new SurveyAdapter(dataList, getApplicationContext());
        surveyList.setAdapter(surveyAdapter);
    }

    @Override
    public void onFailure(Call<SurveyResponse> call, Throwable t) {
        dialog.dismiss();
    }
}