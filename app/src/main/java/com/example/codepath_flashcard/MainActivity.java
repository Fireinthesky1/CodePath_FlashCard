package com.example.codepath_flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    //instance fields
    boolean isShowingAnswers = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //storing my textviews as variables
        TextView Flashcard_Question = findViewById(R.id.Flashcard_Question_textview);
        TextView Flashcard_answer = findViewById(R.id.Flashcard_answer_textview);
        TextView Flashcard_choice_1 = findViewById(R.id.Flashcard_choice_1_textview);
        TextView Flashcard_choice_2 = findViewById(R.id.Flashcard_choice_2_textview);
        TextView Flashcard_choice_3 = findViewById(R.id.Flashcard_choice_3_textview);
        ImageView open_eye = findViewById(R.id.open_eye_imageview);
        ImageView closed_eye = findViewById(R.id.closed_eye_imageview);



        //onclick listener for question
        Flashcard_Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch the visibility of each text
                Flashcard_answer.setVisibility(View.VISIBLE);
                Flashcard_Question.setVisibility(View.INVISIBLE);
            }
        });



        //onclick listener for answer
        Flashcard_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch the visibility of each text
                Flashcard_answer.setVisibility(View.INVISIBLE);
                Flashcard_Question.setVisibility(View.VISIBLE);
            }
        });



        //onclick listener for choice 1
        Flashcard_choice_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch the color of the text
                Flashcard_choice_1.setBackgroundColor(getResources().getColor(R.color.Red, null));
            }
        });



        //onclick listener for choice 2
        Flashcard_choice_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch the color of the text
                Flashcard_choice_2.setBackgroundColor(getResources().getColor(R.color.Red, null));
            }
        });



        //onclick listener for choice 3
        Flashcard_choice_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch the color of the text
                Flashcard_choice_3.setBackgroundColor(getResources().getColor(R.color.Green, null));
            }
        });



        //onclick listener for hide icon
        closed_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide all the answers, change the visibility of icon, set isShowingAnswersToFalse
                Flashcard_choice_1.setVisibility(View.INVISIBLE);
                Flashcard_choice_2.setVisibility(View.INVISIBLE);
                Flashcard_choice_3.setVisibility(View.INVISIBLE);

                closed_eye.setVisibility(View.INVISIBLE);
                open_eye.setVisibility(View.VISIBLE);

                isShowingAnswers = false;
            }
        });



        //onclick listener for show icon
        open_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide all the answers, change the visibility of icon, set isShowingAnswersToFalse
                Flashcard_choice_1.setVisibility(View.VISIBLE);
                Flashcard_choice_2.setVisibility(View.VISIBLE);
                Flashcard_choice_3.setVisibility(View.VISIBLE);

                closed_eye.setVisibility(View.VISIBLE);
                open_eye.setVisibility(View.INVISIBLE);

                isShowingAnswers = true;
            }
        });



        //go to the add card activity and request data back from it
        findViewById(R.id.plus).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });



        //onclick listener for the edit button
        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_for_edit = new Intent(MainActivity.this, AddCardActivity.class);
                String current_question = ((TextView) findViewById(R.id.Flashcard_Question_textview)).getText().toString();
                String current_answer = ((TextView) findViewById(R.id.Flashcard_answer_textview)).getText().toString();
                String current_wrong_answer_option_1 = ((TextView) findViewById(R.id.Flashcard_choice_1_textview)).getText().toString();
                String current_wrong_answer_option_2 = ((TextView) findViewById(R.id.Flashcard_choice_2_textview)).getText().toString();
                intent_for_edit.putExtra("question_to_edit", current_question);
                intent_for_edit.putExtra("answer_to_edit", current_answer);
                intent_for_edit.putExtra("wrong_choice_1", current_wrong_answer_option_1);
                intent_for_edit.putExtra("wrong_choice_2", current_wrong_answer_option_2);
                MainActivity.this.startActivityForResult(intent_for_edit, 100);
            }
        });
    }



    //not sure why this method give an error
    //not sure why I can't use setText() method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if(data != null) {
                String question = data.getExtras().getString("question");
                String answer = data.getExtras().getString("answer");
                String choice_1 = data.getExtras().getString("choice_1");
                String choice_2 = data.getExtras().getString("choice_2");

                ((TextView) findViewById(R.id.Flashcard_Question_textview)).setText(question);
                ((TextView) findViewById(R.id.Flashcard_answer_textview)).setText(answer);
                ((TextView) findViewById(R.id.Flashcard_choice_1_textview)).setText(choice_1);
                ((TextView) findViewById(R.id.Flashcard_choice_2_textview)).setText(choice_2);
                ((TextView) findViewById(R.id.Flashcard_choice_3_textview)).setText(answer);
                Snackbar.make(findViewById(R.id.Flashcard_Question_textview), "card successfully created", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}