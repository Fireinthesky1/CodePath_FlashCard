package com.example.codepath_flashcard;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //instance fields
    boolean isShowingAnswers = true;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    Flashcard cardToEdit;
    int Add_Card_Request_Code = 100;
    int Edit_Card_Request_Code = 200;


    //utility methods
    public int generate_random_number(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1);
    }



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

        //initializing flashcardDatabase
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());

        //getting cards from flashcardDatabase
        allFlashcards = flashcardDatabase.getAllCards();

        //if there are any flashcards in the database display the first one to the screen
        if(allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.Flashcard_Question_textview)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.Flashcard_answer_textview)).setText(allFlashcards.get(0).getAnswer());
            ((TextView) findViewById(R.id.Flashcard_choice_1_textview)).setText(allFlashcards.get(0).getWrongAnswer1());
            ((TextView) findViewById(R.id.Flashcard_choice_2_textview)).setText(allFlashcards.get(0).getWrongAnswer2());
            ((TextView) findViewById(R.id.Flashcard_choice_3_textview)).setText(allFlashcards.get(0).getAnswer());
        }



        //onclick listener for question
        Flashcard_Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the center for the clipping circle
                int cx = Flashcard_answer.getWidth() / 2;
                int cy = Flashcard_answer.getHeight() / 2;

                //get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                //create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(Flashcard_answer, cx, cy, 0f, finalRadius);

                //switch the visibility of each text
                Flashcard_answer.setVisibility(View.VISIBLE);
                Flashcard_Question.setVisibility(View.INVISIBLE);

                anim.setDuration(3000);
                anim.start();
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
                Flashcard_choice_1.setBackground(getDrawable(R.drawable.card_background_false));
            }
        });



        //onclick listener for choice 2
        Flashcard_choice_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch the color of the text
                Flashcard_choice_2.setBackground(getDrawable(R.drawable.card_background_false));
            }
        });



        //onclick listener for choice 3
        Flashcard_choice_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch the color of the text
                Flashcard_choice_3.setBackground(getDrawable(R.drawable.card_background_correct));
                //Flashcard_choice_3.setBackgroundColor(getResources().getColor(R.color.Green, null));
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
                MainActivity.this.startActivityForResult(intent, Add_Card_Request_Code);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });



        //onclick listener for the edit button
        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_for_edit = new Intent(MainActivity.this, AddCardActivity.class);
                String current_question = ((TextView) findViewById(R.id.Flashcard_Question_textview)).getText().toString();

                if(allFlashcards.size() == 0) {
                    Snackbar.make(findViewById(R.id.Flashcard_Question_textview), "Cannot edit example card!", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //find the current flashcard in allFlashCards and point cardToEdit at that flashcard
                for(int i = 0; i < allFlashcards.size(); i++){
                    if(allFlashcards.get(i).getQuestion().equals(current_question)) {
                        cardToEdit = allFlashcards.get(i);
                    }
                }

                String current_answer = ((TextView) findViewById(R.id.Flashcard_answer_textview)).getText().toString();
                String current_wrong_answer_option_1 = ((TextView) findViewById(R.id.Flashcard_choice_1_textview)).getText().toString();
                String current_wrong_answer_option_2 = ((TextView) findViewById(R.id.Flashcard_choice_2_textview)).getText().toString();
                intent_for_edit.putExtra("question_to_edit", current_question);
                intent_for_edit.putExtra("answer_to_edit", current_answer);
                intent_for_edit.putExtra("wrong_choice_1", current_wrong_answer_option_1);
                intent_for_edit.putExtra("wrong_choice_2", current_wrong_answer_option_2);
                MainActivity.this.startActivityForResult(intent_for_edit, Edit_Card_Request_Code);
            }
        });



        //onclick listener for the next button
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //load the animation resource files
                final Animation LeftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                final Animation RightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);

                LeftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        //start the right in animation
                        findViewById(R.id.Flashcard_Question_textview).startAnimation(RightInAnim);
                        findViewById(R.id.Flashcard_choice_1_textview).startAnimation(RightInAnim);
                        findViewById(R.id.Flashcard_choice_2_textview).startAnimation(RightInAnim);
                        findViewById(R.id.Flashcard_choice_3_textview).startAnimation(RightInAnim);

                        //after the right in animation set the question and answer textviews with data from the database
                        allFlashcards = flashcardDatabase.getAllCards();
                        Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);
                        ((TextView) findViewById(R.id.Flashcard_Question_textview)).setText(flashcard.getQuestion());
                        ((TextView) findViewById(R.id.Flashcard_answer_textview)).setText(flashcard.getAnswer());
                        ((TextView) findViewById(R.id.Flashcard_choice_1_textview)).setText(flashcard.getWrongAnswer1());
                        ((TextView) findViewById(R.id.Flashcard_choice_2_textview)).setText(flashcard.getWrongAnswer2());
                        ((TextView) findViewById(R.id.Flashcard_choice_3_textview)).setText(flashcard.getAnswer());
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                //reset the color of the choices
                findViewById(R.id.Flashcard_choice_1_textview).setBackground(getDrawable(R.drawable.card_background_2));
                findViewById(R.id.Flashcard_choice_2_textview).setBackground(getDrawable(R.drawable.card_background_2));
                findViewById(R.id.Flashcard_choice_3_textview).setBackground(getDrawable(R.drawable.card_background_2));

                //if there are no cards return
                if(allFlashcards.size() == 0) {
                    Snackbar.make(findViewById(R.id.Flashcard_Question_textview), "No more cards in study set!", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //advance the pointer index so we can show the next card
                currentCardDisplayedIndex++;

                //make sure we don't get IndexOutOfBoundsError if we are viewing the last card in our list
                if(currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(findViewById(R.id.Flashcard_Question_textview), "study set complete!", Snackbar.LENGTH_SHORT).show();
                    currentCardDisplayedIndex = 0;
                }

                //start the animation
                findViewById(R.id.Flashcard_Question_textview).startAnimation(LeftOutAnim);
                findViewById(R.id.Flashcard_choice_1_textview).startAnimation(LeftOutAnim);
                findViewById(R.id.Flashcard_choice_2_textview).startAnimation(LeftOutAnim);
                findViewById(R.id.Flashcard_choice_3_textview).startAnimation(LeftOutAnim);
                }
        });



        //onclick listener for trash button
        findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete the current flashcard using the delete method from FlashCardDatabase class
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.Flashcard_Question_textview)).getText().toString());
                //update the list of cards
                allFlashcards = flashcardDatabase.getAllCards();
                //check to see if we deleted all the cards
                if(allFlashcards.size() == 0){
                    //go to the add card activity and request data back from it
                    Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                    MainActivity.this.startActivityForResult(intent, Add_Card_Request_Code);
                }else { //otherwise get the next flashcard and display it
                    currentCardDisplayedIndex++;

                    //make sure we don't get IndexOutOfBoundsError if we are viewing the last card in our list
                    if(currentCardDisplayedIndex >= allFlashcards.size()) {
                        Snackbar.make(findViewById(R.id.Flashcard_Question_textview), "study set complete!", Snackbar.LENGTH_SHORT).show();
                        currentCardDisplayedIndex = 0;
                    }

                    Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);
                    ((TextView) findViewById(R.id.Flashcard_Question_textview)).setText(flashcard.getQuestion());
                    ((TextView) findViewById(R.id.Flashcard_answer_textview)).setText(flashcard.getAnswer());
                    ((TextView) findViewById(R.id.Flashcard_choice_1_textview)).setText(flashcard.getWrongAnswer1());
                    ((TextView) findViewById(R.id.Flashcard_choice_2_textview)).setText(flashcard.getWrongAnswer2());
                    ((TextView) findViewById(R.id.Flashcard_choice_3_textview)).setText(flashcard.getAnswer());
                }

            }
        });
    }



    //when we return from an activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        findViewById(R.id.Flashcard_choice_1_textview).setBackground(getDrawable(R.drawable.card_background_2));
        findViewById(R.id.Flashcard_choice_2_textview).setBackground(getDrawable(R.drawable.card_background_2));
        findViewById(R.id.Flashcard_choice_3_textview).setBackground(getDrawable(R.drawable.card_background_2));
        if (requestCode == Add_Card_Request_Code && resultCode == RESULT_OK) {
            if(data != null) {
                String question = data.getExtras().getString("question");
                String answer = data.getExtras().getString("answer");
                String choice_1 = data.getExtras().getString("choice_1");
                String choice_2 = data.getExtras().getString("choice_2");
                //inserting the flashcard into the database
                flashcardDatabase.insertCard(new Flashcard(question, answer, choice_1, choice_2));
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex = allFlashcards.size()-1;

                ((TextView) findViewById(R.id.Flashcard_Question_textview)).setText(question);
                ((TextView) findViewById(R.id.Flashcard_answer_textview)).setText(answer);
                ((TextView) findViewById(R.id.Flashcard_choice_1_textview)).setText(choice_1);
                ((TextView) findViewById(R.id.Flashcard_choice_2_textview)).setText(choice_2);
                ((TextView) findViewById(R.id.Flashcard_choice_3_textview)).setText(answer);
                Snackbar.make(findViewById(R.id.Flashcard_Question_textview), "card successfully created", Snackbar.LENGTH_SHORT).show();
            }
        }else if (requestCode == Edit_Card_Request_Code && resultCode == RESULT_OK) {
            String question = data.getExtras().getString("question");
            String answer = data.getExtras().getString("answer");
            String choice_1 = data.getExtras().getString("choice_1");
            String choice_2 = data.getExtras().getString("choice_2");

            ((TextView) findViewById(R.id.Flashcard_Question_textview)).setText(question);
            ((TextView) findViewById(R.id.Flashcard_answer_textview)).setText(answer);
            ((TextView) findViewById(R.id.Flashcard_choice_1_textview)).setText(choice_1);
            ((TextView) findViewById(R.id.Flashcard_choice_2_textview)).setText(choice_2);
            ((TextView) findViewById(R.id.Flashcard_choice_3_textview)).setText(answer);

            cardToEdit.setQuestion(question);
            cardToEdit.setAnswer(answer);
            cardToEdit.setWrongAnswer1(choice_1);
            cardToEdit.setWrongAnswer2(choice_2);

            flashcardDatabase.updateCard(cardToEdit);
            Snackbar.make(findViewById(R.id.Flashcard_Question_textview), "card successfully edited", Snackbar.LENGTH_SHORT).show();
        }
    }
}