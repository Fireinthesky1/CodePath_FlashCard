package com.example.codepath_flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    }
}