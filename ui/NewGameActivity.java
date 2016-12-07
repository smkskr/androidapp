package com.example.android.thenextbigthing.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.thenextbigthing.R;
import com.example.android.thenextbigthing.helper.DataBaseHelper;
import com.example.android.thenextbigthing.utils.Riddles;

import java.util.ArrayList;
import java.util.List;

public class NewGameActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatTextView question;
    private List<AppCompatEditText> answerList;
    private AppCompatEditText answer;
    private LinearLayout.LayoutParams layoutParams;
    private LinearLayout linearLayout;
    private DataBaseHelper dataBaseHelper;
    private int randomNum,countOfRows;
    private AppCompatButton buttonNext;
    private Riddles riddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);


        initGui();
        initDatabase();
        updateQuestion();
    }

    private void initGui(){

        question = (AppCompatTextView)findViewById(R.id.textView_question);

        linearLayout = (LinearLayout)findViewById(R.id.linear_layout);
        layoutParams = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT);

        buttonNext = (AppCompatButton)findViewById(R.id.btn_next);
        buttonNext.setOnClickListener(this);
    }

    private void initDatabase(){
        dataBaseHelper = new DataBaseHelper(this);
    }

    private void updateQuestion(){

        answerList = new ArrayList<AppCompatEditText>();

        riddle = dataBaseHelper.getRandomRiddle();
        if(riddle.get_count() > 0) {
            question.setText(riddle.getQuestion());
            for(int i = 0;i < riddle.getAnswer().length();i++){
                editText(i);
            }
        }else{

            Toast.makeText(getApplicationContext(),"Game Over",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:submitAnswer(riddle);break;
        }

    }

    private void submitAnswer(Riddles riddle) {

        StringBuilder answer = new StringBuilder();
        for (AppCompatEditText editText : answerList) {
            answer.append(editText.getText().toString());
        }

        if( answer.toString().toLowerCase().equals(riddle.getAnswer())){
            for (AppCompatEditText editText : answerList) {
                editText.setVisibility(View.GONE);
            }
            updateQuestion();

        }else{
            Toast.makeText(getApplicationContext(),"Wrong Answer",Toast.LENGTH_LONG).show();
        }
    }

    private void editText(int id) {
        AppCompatEditText editText = new AppCompatEditText(this);
        editText.setLayoutParams(layoutParams);
        editText.setId(id);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(1);
        editText.setFilters(FilterArray);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        editText.setGravity(Gravity.CENTER);
        editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editText.setText("");
        editText.setSingleLine(true);
        editText.setTextColor(getResources().getColor(R.color.colorText));
        linearLayout.addView(editText);
        answerList.add(editText);
    }

}
