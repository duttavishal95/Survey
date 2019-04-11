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
import android.widget.Toast;

import com.survey.question_response.Data;
import com.survey.question_response.QuestionRequestResponse;
import com.survey.question_response.QuestionResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener, Callback<QuestionResponse> {

    private Button submit;
    private RecyclerView questionList;
    private ProgressDialog dialog;
    private List<Data> dataList;
    private QuestionAdapter questionAdapter;
    private List<QuestionRequest> questionRequests = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        questionList = findViewById(R.id.questionList);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        getQuestions();
    }

    private void getQuestions() {
        if (getIntent().getExtras() != null) {
            dialog = new ProgressDialog(QuestionActivity.this);
            dialog.setMessage("Getting questions..");
            dialog.show();
            Call<QuestionResponse> questionsResponseCall = new Services().getRetrofit("http://3.16.11.132/survey_system/").getQuestions("application/json", "application/json", getIntent().getExtras().getInt("id"));
            questionsResponseCall.enqueue(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (dataList != null && dataList.size() > 0) {
                    boolean isEmpty = false;
                    questionRequests.clear();
                    for (int i = 0; i < dataList.size(); i++) {
                        if (TextUtils.isEmpty(dataList.get(i).getAnswer())) {
                            isEmpty = true;
                            int questionCount = i + 1;
                            Toast.makeText(getApplicationContext(), "question " + questionCount + " is not answered", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            QuestionRequest questionsRequest = new QuestionRequest();
                            questionsRequest.setUser_id(LoginActivity.userId);
                            questionsRequest.setQuestion_id(dataList.get(i).getId());
                            questionsRequest.setAnswer(dataList.get(i).getAnswer());
                            questionRequests.add(questionsRequest);
                        }
                    }
                    if (isEmpty) {
                        Toast.makeText(getApplicationContext(), "You have not answered all the questions.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    submitQuestions();
                } else {

                }
                break;
        }
    }

    private void submitQuestions() {
        dialog.setMessage("Submitting Questions...");
        dialog.show();
        Call<QuestionRequestResponse> submitCall = new Services().getRetrofit("http://3.16.11.132/survey_system/").submitQuestions("application/json", "application/json", questionRequests);
        submitCall.enqueue(new Callback<QuestionRequestResponse>() {
            @Override
            public void onResponse(Call<QuestionRequestResponse> call, Response<QuestionRequestResponse> response) {
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
            public void onFailure(Call<QuestionRequestResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void showMessageToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
        dialog.dismiss();
        if (response.body() != null && response.body().getResponse().getStatus().contains("success")) {
            dataList = response.body().getResponse().getData();
            setAdapter();
        }
    }

    private void setAdapter() {
        questionList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        questionAdapter = new QuestionAdapter(dataList, getApplicationContext());
        questionList.setAdapter(questionAdapter);
    }

    @Override
    public void onFailure(Call<QuestionResponse> call, Throwable t) {
        dialog.dismiss();
    }
}