package com.survey;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private RadioGroup firstGroup;
    private Button submit;

    private RadioButton excellent;
    private RadioButton veryGood;
    private RadioButton good;
    private RadioButton bad;

    private EditText feedback;

    private String service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        firstGroup = findViewById(R.id.firstSurvey);
        excellent = findViewById(R.id.excellent);
        veryGood = findViewById(R.id.very_good);
        good = findViewById(R.id.good);
        bad = findViewById(R.id.bad);
        feedback = findViewById(R.id.answer);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        excellent.setOnCheckedChangeListener(this);
        veryGood.setOnCheckedChangeListener(this);
        good.setOnCheckedChangeListener(this);
        bad.setOnCheckedChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (firstGroup.getCheckedRadioButtonId() == -1) {
                    showMessageToast("Select any option");
                    return;
                } else if (TextUtils.isEmpty(feedback.getText().toString())) {
                    showMessageToast("Enter feedback");
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);
                intent.putExtra("service", service);
                intent.putExtra("answer", feedback.getText().toString());
                startActivity(intent);
                break;
        }
    }

    private void showMessageToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.excellent:
                if (isChecked) {
                    service = "Excellent";
                }
                break;

            case R.id.very_good:
                if (isChecked) {
                    service = "Very Good";
                }
                break;

            case R.id.good:
                if (isChecked) {
                    service = "Good";
                }
                break;

            case R.id.bad:
                if (isChecked) {
                    service = "Bad";
                }
                break;
        }
    }
}