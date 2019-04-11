package com.survey;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.survey.question_response.Data;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private List<Data> dataList;
    private Context mContext;

    QuestionAdapter(List<Data> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_question, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.question.setText(dataList.get(i).getQuestion());


        switch (dataList.get(i).getAnswer_type()) {
            case 1:
                myViewHolder.answer.setVisibility(View.VISIBLE);
                break;
            case 2:
                myViewHolder.radioGroup.setVisibility(View.VISIBLE);
                myViewHolder.rb1.setText(dataList.get(i).getAnswer_A());
                myViewHolder.rb2.setText(dataList.get(i).getAnswer_B());
                myViewHolder.rb3.setText(dataList.get(i).getAnswer_C());
                myViewHolder.rb4.setText(dataList.get(i).getAnswer_D());
                break;
            case 3:
                myViewHolder.ratingBar.setVisibility(View.VISIBLE);
                break;
            case 4:
                myViewHolder.chkGroup.setVisibility(View.VISIBLE);
                myViewHolder.chk1.setText(dataList.get(i).getAnswer_A());
                myViewHolder.chk2.setText(dataList.get(i).getAnswer_B());
                myViewHolder.chk3.setText(dataList.get(i).getAnswer_C());
                myViewHolder.chk4.setText(dataList.get(i).getAnswer_D());
                break;
        }

        myViewHolder.answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(myViewHolder.answer.getText().toString())) {
                    dataList.get(i).setAnswer(myViewHolder.answer.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        myViewHolder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                dataList.get(i).setAnswer(String.valueOf(rating));
            }
        });

        myViewHolder.rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataList.get(i).setAnswer(myViewHolder.rb1.getText().toString());
                }
            }
        });
        myViewHolder.rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataList.get(i).setAnswer(myViewHolder.rb2.getText().toString());
                }
            }
        });
        myViewHolder.rb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataList.get(i).setAnswer(myViewHolder.rb3.getText().toString());
                }
            }
        });
        myViewHolder.rb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataList.get(i).setAnswer(myViewHolder.rb4.getText().toString());
                }
            }
        });


        myViewHolder.chk1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String answer;
                    if (TextUtils.isEmpty(dataList.get(i).getAnswer())) {
                        answer = myViewHolder.chk1.getText().toString();
                    } else {
                        answer = dataList.get(i).getAnswer() + " " + myViewHolder.chk1.getText().toString();
                    }
                    dataList.get(i).setAnswer(answer);
                } else {
                    if (!TextUtils.isEmpty(dataList.get(i).getAnswer())) {
                        String answer = dataList.get(i).getAnswer().replaceAll(myViewHolder.chk1.getText().toString(), "");
                        dataList.get(i).setAnswer(answer.trim());
                    }
                }
            }
        });
        myViewHolder.chk2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String answer;
                    if (TextUtils.isEmpty(dataList.get(i).getAnswer())) {
                        answer = myViewHolder.chk2.getText().toString();
                    } else {
                        answer = dataList.get(i).getAnswer() + " " + myViewHolder.chk2.getText().toString();
                    }
                    dataList.get(i).setAnswer(answer);
                } else {
                    if (!TextUtils.isEmpty(dataList.get(i).getAnswer())) {
                        String answer = dataList.get(i).getAnswer().replaceAll(myViewHolder.chk2.getText().toString(), "");
                        dataList.get(i).setAnswer(answer.trim());
                    }
                }
            }
        });
        myViewHolder.chk3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String answer;
                    if (TextUtils.isEmpty(dataList.get(i).getAnswer())) {
                        answer = myViewHolder.chk3.getText().toString();
                    } else {
                        answer = dataList.get(i).getAnswer() + " " + myViewHolder.chk3.getText().toString();
                    }
                    dataList.get(i).setAnswer(answer);
                } else {
                    if (!TextUtils.isEmpty(dataList.get(i).getAnswer())) {
                        String answer = dataList.get(i).getAnswer().replaceAll(myViewHolder.chk3.getText().toString(), "");
                        dataList.get(i).setAnswer(answer.trim());
                    }
                }
            }
        });
        myViewHolder.chk4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String answer;
                    if (TextUtils.isEmpty(dataList.get(i).getAnswer())) {
                        answer = myViewHolder.chk4.getText().toString();
                    } else {
                        answer = dataList.get(i).getAnswer() + " " + myViewHolder.chk4.getText().toString();
                    }
                    dataList.get(i).setAnswer(answer);
                } else {
                    if (!TextUtils.isEmpty(dataList.get(i).getAnswer())) {
                        String answer = dataList.get(i).getAnswer().replaceAll(myViewHolder.chk4.getText().toString(), "");
                        dataList.get(i).setAnswer(answer.trim());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private EditText answer;
        private RadioGroup radioGroup;
        private RadioButton rb1;
        private RadioButton rb2;
        private RadioButton rb3;
        private RadioButton rb4;
        private ConstraintLayout chkGroup;
        private CheckBox chk1;
        private CheckBox chk2;
        private CheckBox chk3;
        private CheckBox chk4;
        private RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
            radioGroup = itemView.findViewById(R.id.radio_group);
            rb1 = itemView.findViewById(R.id.rb1);
            rb2 = itemView.findViewById(R.id.rb2);
            rb3 = itemView.findViewById(R.id.rb3);
            rb4 = itemView.findViewById(R.id.rb4);
            chkGroup = itemView.findViewById(R.id.chkGroup);
            chk1 = itemView.findViewById(R.id.chk1);
            chk2 = itemView.findViewById(R.id.chk2);
            chk3 = itemView.findViewById(R.id.chk3);
            chk4 = itemView.findViewById(R.id.chk4);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
