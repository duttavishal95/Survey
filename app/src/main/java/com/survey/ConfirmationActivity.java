package com.survey;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ConfirmationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView firstAnswer;
    private TextView answer;
    private Button back;
    private Button confirmation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        firstAnswer = findViewById(R.id.first_answer);
        answer = findViewById(R.id.answer);
        back = findViewById(R.id.back);
        confirmation = findViewById(R.id.confirmation);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            firstAnswer.setText(intent.getExtras().getString("service"));
            answer.setText(intent.getExtras().getString("answer"));
        }

        back.setOnClickListener(this);
        confirmation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.confirmation:
                Toast.makeText(getApplicationContext(), "Survey Submitted", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
