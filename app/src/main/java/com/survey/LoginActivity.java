package com.survey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.survey.login_model.LoginRequest;
import com.survey.login_model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<LoginResponse> {

    private EditText username;
    private EditText password;

    private Button login;
    private ProgressDialog dialog;
    public static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(LoginActivity.this);

        findViews();
    }

    private void findViews() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:

                if (username.getText().toString().isEmpty()) {
                    showMessageToast("Enter username");
                    return;
                } else if (password.getText().toString().isEmpty()) {
                    showMessageToast("Enter password");
                    return;
                }
                dialog.setMessage("Signing In");
                dialog.show();
                LoginRequest loginRequest = new LoginRequest(username.getText().toString(), password.getText().toString());
                Call<LoginResponse> responseCall = new Services().getRetrofit("http://3.16.11.132/survey_system/").hitLogin("application/json", "application/json", loginRequest);
                responseCall.enqueue(this);
                break;
        }
    }

    private void showMessageToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        dialog.dismiss();
        if (response.body() instanceof LoginResponse) {
            userId = response.body().getResponse().getUser_id();
            Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
            startActivity(intent);
            finish();
        } else {
            showMessageToast("Invalid Username and password");
        }
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        dialog.dismiss();
    }
}
