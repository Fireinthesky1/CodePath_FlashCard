package com.example.codepath_flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AddCardActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);



        //getting the strings from the edit button
        String question_to_edit = getIntent().getStringExtra("question_to_edit");
        String answer_to_edit = getIntent().getStringExtra("answer_to_edit");
        String choice_1_to_edit = getIntent().getStringExtra("wrong_choice_1");
        String choice_2_to_edit = getIntent().getStringExtra("wrong_choice_2");
        ((TextView) findViewById(R.id.question)).setText(question_to_edit);
        ((TextView) findViewById(R.id.answer)).setText(answer_to_edit);
        ((TextView) findViewById(R.id.wrong_answer_option_1)).setText(choice_1_to_edit);
        ((TextView) findViewById(R.id.wrong_answer_option_2)).setText(choice_2_to_edit);




        //onclicklistener for save button
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //storing the edit text into a string
                String question = ((EditText) findViewById(R.id.question)).getText().toString();
                String answer = ((EditText) findViewById(R.id.answer)).getText().toString();
                String choice_1 = ((EditText) findViewById(R.id.wrong_answer_option_1)).getText().toString();
                String choice_2 = ((EditText) findViewById(R.id.wrong_answer_option_2)).getText().toString();

                //if the answer is empty
                if(question.isEmpty() || answer.isEmpty() || choice_1.isEmpty() || choice_2.isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(), "required fields left empty", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }else {
                    //putting string back into the Intent to be passed to main
                    Intent data = new Intent(); //create a new intent
                    data.putExtra("question", question);
                    data.putExtra("answer", answer);
                    data.putExtra("choice_1", choice_1);
                    data.putExtra("choice_2", choice_2);
                    setResult(RESULT_OK, data); //set result code and bundle data for response
                    finish();
                }
            }
        });



        //dismiss this activity
        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}