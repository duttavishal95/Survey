package com.survey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup firstGroup;
    private Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        firstGroup = findViewById(R.id.firstSurvey);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (firstGroup.getCheckedRadioButtonId() == -1) {
                    showMessageToast("select any option");
                    return;
                }
                showMessageToast("Survey submitted");
                break;
        }
    }

    private void showMessageToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

}
