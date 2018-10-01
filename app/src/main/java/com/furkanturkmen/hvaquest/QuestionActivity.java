package com.furkanturkmen.hvaquest;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    private Button checkButton;
    private TextView questionText;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private int questionNumber;
    private String[] items;
    private String correctAnswer;
    private int selectedId;
    private Snackbar snackbar;
    private String snackbarString;
    private String snackbarString2;
    public static final String questionKey = "questionKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Locale variables
        checkButton = findViewById(R.id.btnNext);
        questionText = findViewById(R.id.textQuestion);
        radioGroup = findViewById(R.id.grpRadio);
        radioButton1 = findViewById(R.id.btnRadio1);
        radioButton2 = findViewById(R.id.btnRadio2);
        radioButton3 = findViewById(R.id.btnRadio3);
        questionNumber = 1;

        // Populate question with answers to choose from!
        populateQuestion(questionNumber);


        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId == -1){
                    snackbarString = getApplicationContext().getString(R.string.choose_answer);
                    snackbar = Snackbar.make(v, snackbarString, Snackbar.LENGTH_LONG).setAction("Action", null);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else {
                    RadioButton answer = findViewById(selectedId);
                    if(correctAnswer.contentEquals(answer.getText())){
                        Intent intent = new Intent(QuestionActivity.this, LocationActivity.class);
                        intent.putExtra(questionKey, questionNumber);
                        startActivityForResult(intent,1234);
                    } else{
                        snackbarString2 = getApplicationContext().getString(R.string.try_again);
                        snackbar = Snackbar.make(v, snackbarString2, Snackbar.LENGTH_LONG).setAction("Action", null);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();
                    }
                }
            }
        });
    }


    public void populateQuestion(int questionNumber){
        //   populate question
        String question = "question" + questionNumber;
        int holderint1 = getResources().getIdentifier(question, "string",
                this.getPackageName()); // You had used "name"
        String questionScreen = questionNumber + ". " + getResources().getString(holderint1);
        questionText.setText(questionScreen);


        // populate answer
        String answer = "answer" + questionNumber;
        int holderint = getResources().getIdentifier(answer, "array",
                this.getPackageName()); // You had used "name"
        items = getResources().getStringArray(holderint);

        radioButton1.setText(items[1]);
        radioButton2.setText(items[2]);
        radioButton3.setText(items[3]);
        correctAnswer = items[0];
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check if the result code is the right one
        if (resultCode == Activity.RESULT_OK) {
            //Check if the request code is correct
            if (requestCode == 1234) {
                questionNumber = data.getIntExtra(LocationActivity.clueKey, -1);
                populateQuestion(questionNumber);
                radioGroup.clearCheck();
            }
        }
    }
}
