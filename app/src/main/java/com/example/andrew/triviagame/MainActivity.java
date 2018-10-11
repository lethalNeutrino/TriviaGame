package com.example.andrew.triviagame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements theLifeInterface { //Interface with all constant strings

    //Instance variables
    int[] fiveQ = new int[5]; //Which questions were chosen
    int[] chosen = new int[5]; //Saved answers
    int[] correct = {0,0,0,0,0}; //Results
    int screen; //Which question number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    public boolean onTouchEvent(MotionEvent event) {
        ImageButton next = (ImageButton)findViewById(R.id.next);
        ImageButton back = (ImageButton)findViewById(R.id.back);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() < 320 && screen > 0) lastQ(back);
            else if (event.getX() > 1000 && screen < 4) nextQ(next);
        }
        return true;
    }
    */

    public void play(View view) {
        //Change to trivia page, choose questions, hide back button, reset all variables to 0

        setContentView(R.layout.trivia);

        //Hide back button to avoid error
        ImageButton back = (ImageButton)findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);

        //Choose questions
        banishRepeats();

        //Show first question
        switch (fiveQ[0]) {
            case 0: questionSetOne(); break;
            case 1: questionSetTwo(); break;
            case 2: questionSetThree(); break;
            case 3: questionSetFour(); break;
            case 4: questionSetFive(); break;
            case 5: questionSetSix(); break;
            case 6: questionSetSeven(); break;
            case 7: questionSetEight(); break;
            case 8: questionSetNine(); break;
            case 9: questionSetTen(); break;
        }

        //Reset variables if played again
        for (int i = 0; i < chosen.length; i++){
            chosen[i] = 0;
            correct[i] = 0;
        }
        screen = 0;
    } //Play button
    public void nextQ(View view) {
        //Find buttons
        ImageButton next = (ImageButton)findViewById(R.id.next);
        ImageButton back = (ImageButton)findViewById(R.id.back);

        //Find RadioGroup and Buttons
        RadioGroup potOfGreed = (RadioGroup)findViewById(R.id.questions); //RadioGroup

        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        //Save selected answer
        switch(potOfGreed.getCheckedRadioButtonId()){
            case R.id.ans_1: chosen[screen] = 1; break;
            case R.id.ans_2: chosen[screen] = 2; break;
            case R.id.ans_3: chosen[screen] = 3; break;
            case R.id.ans_4: chosen[screen] = 4; break;
            default: break;
        }

        //Deselect answer
        potOfGreed.clearCheck();

        //Change screen
        screen++;

        //Dynamically hide/show buttons
        if (screen > 0) {
            back.setVisibility(View.VISIBLE);
        }
        if (screen == 4) {
            next.setVisibility(View.INVISIBLE);
        }

        //Set next question
        switch(fiveQ[screen]) {
            case 0: questionSetOne(); break;
            case 1: questionSetTwo(); break;
            case 2: questionSetThree(); break;
            case 3: questionSetFour(); break;
            case 4: questionSetFive(); break;
            case 5: questionSetSix(); break;
            case 6: questionSetSeven(); break;
            case 7: questionSetEight(); break;
            case 8: questionSetNine(); break;
            case 9: questionSetTen(); break;
        }

        //Display saved answer, if there is one
        switch (chosen[screen]){
            case 1: ans_1.performClick(); break;
            case 2: ans_2.performClick(); break;
            case 3: ans_3.performClick(); break;
            case 4: ans_4.performClick(); break;
            default: break;
        }

        //Change question header
        incrementQuestionNum();
    } //Right arrow button
    public void lastQ(View view) {
        //Find next and back buttons
        ImageButton next = (ImageButton)findViewById(R.id.next);
        ImageButton back = (ImageButton)findViewById(R.id.back);

        //Find RadioGroup and buttons
        RadioGroup potOfGreed = (RadioGroup)findViewById(R.id.questions); //RadioGroup

        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        //Save selected answer
        switch(potOfGreed.getCheckedRadioButtonId()){
            case R.id.ans_1: chosen[screen] = 1; break;
            case R.id.ans_2: chosen[screen] = 2; break;
            case R.id.ans_3: chosen[screen] = 3; break;
            case R.id.ans_4: chosen[screen] = 4; break;
            default: break;
        }

        //Clear selection
        potOfGreed.clearCheck();

        //Change screen
        screen--;

        //Dynamically hide/show button
        if (screen < 5) {
            next.setVisibility(View.VISIBLE);
        }
        if (screen == 0) {
            back.setVisibility(View.INVISIBLE);
        }

        //Set question
        switch(fiveQ[screen]) {
            case 0: questionSetOne(); break;
            case 1: questionSetTwo(); break;
            case 2: questionSetThree(); break;
            case 3: questionSetFour(); break;
            case 4: questionSetFive(); break;
            case 5: questionSetSix(); break;
            case 6: questionSetSeven(); break;
            case 7: questionSetEight(); break;
            case 8: questionSetNine(); break;
            case 9: questionSetTen(); break;
        }

        //Choose saved answer
        switch (chosen[screen]){
            case 0: break;
            case 1: ans_1.performClick(); break;
            case 2: ans_2.performClick(); break;
            case 3: ans_3.performClick(); break;
            case 4: ans_4.performClick(); break;
            default: break;
        }

        //Change question header
        decrementQuestionNum();
    } //Left arrow button
    public void submit(View view) {

        //Find radiogroup and buttons
        RadioGroup potOfGreed = (RadioGroup)findViewById(R.id.questions); //RadioGroup

        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        //save selected answer
        switch(potOfGreed.getCheckedRadioButtonId()){
            case R.id.ans_1: chosen[screen] = 1; break;
            case R.id.ans_2: chosen[screen] = 2; break;
            case R.id.ans_3: chosen[screen] = 3; break;
            case R.id.ans_4: chosen[screen] = 4; break;
            default: break;
        }

        //change page
        setContentView(R.layout.results);

        //grade quiz
        for(int i = 0; i < chosen.length; i++ ) {
            if (chosen[i] == answerKey[fiveQ[i]]) {
                correct[i] = 1;
            }
        }
        resultsSetup();
    } //Submit button

    public void resultsSetup() {

        setContentView(R.layout.results); //Change page

        //Find Images and TextViews
        ImageView silentMagician = (ImageView)findViewById(R.id.resultPic); //Image
        TextView results = (TextView)findViewById(R.id.correct);
        TextView points = (TextView)findViewById(R.id.score);

        //Calculate Score
        int score = 0;
        for (int i : correct) score += i;

        //Declare and initialize TextView info
        String scoreboard = "Questions correct: "; //Questions correct
        String fiveHeadedDragon = "Your score was " + score + "/5."; //Score display

        //Questions correct
        for (int i = 0; i < correct.length; i++) {
            int num = i + 1;
            if (correct[i] == 1 && i != 4) {
                scoreboard = scoreboard + num + ", ";
            }
            else if (correct[i] == 1 && i == 4) {
                scoreboard = scoreboard + num;
            }
        }

        //Choose which image to display
        if (score >= 4) {
            silentMagician.setImageResource(R.drawable.goodjob);
        }
        else if (score >= 2) {
            silentMagician.setImageResource(R.drawable.mediocre);
        }
        else {
            silentMagician.setImageResource(R.drawable.failure);
        }

        //Set text
        results.setText(scoreboard);
        points.setText(fiveHeadedDragon);
    }

    public void banishRepeats() {
        //guarantee no duplicate questions
        TextView tv = (TextView) findViewById(R.id.question);

        //Start with array list of all ints 1-9
        ArrayList<Integer> questions = new ArrayList<>(10);
        for (int i = 0; i <= 9; i++) {
            questions.add(i);
        }

        //shuffle
        Collections.shuffle(questions);

        //then take first five nums
        for (int i = 0; i <=4; i++) {
            fiveQ[i] = questions.get(i);
        }
    }

    //Change banner with question number at top
    public void incrementQuestionNum(){
        TextView fiveHeadedDragon = (TextView)findViewById(R.id.q_number);
        switch(screen){
            case 0: fiveHeadedDragon.setText(questionNum[0]); break;
            case 1: fiveHeadedDragon.setText(questionNum[1]); break;
            case 2: fiveHeadedDragon.setText(questionNum[2]); break;
            case 3: fiveHeadedDragon.setText(questionNum[3]); break;
            case 4: fiveHeadedDragon.setText(questionNum[4]); break;
        }
    }
    public void decrementQuestionNum(){
        TextView fiveHeadedDragon = (TextView)findViewById(R.id.q_number);
        switch(screen){
            case 0: fiveHeadedDragon.setText(questionNum[0]); break;
            case 1: fiveHeadedDragon.setText(questionNum[1]); break;
            case 2: fiveHeadedDragon.setText(questionNum[2]); break;
            case 3: fiveHeadedDragon.setText(questionNum[3]); break;
            case 4: fiveHeadedDragon.setText(questionNum[4]); break;
        }
    }

    //Question setters
    public void questionSetOne(){
        TextView prompt = (TextView) findViewById(R.id.question);
        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        prompt.setText(blueEyesWhiteDragon[0]);
        ans_1.setText(answer1[0]);
        ans_2.setText(answer2[0]);
        ans_3.setText(answer3[0]);
        ans_4.setText(answer4[0]);


    }
    public void questionSetTwo(){
        TextView prompt = (TextView) findViewById(R.id.question);
        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        prompt.setText(blueEyesWhiteDragon[1]);
        ans_1.setText(answer1[1]);
        ans_2.setText(answer2[1]);
        ans_3.setText(answer3[1]);
        ans_4.setText(answer4[1]);
    }
    public void questionSetThree(){
        TextView prompt = (TextView) findViewById(R.id.question);
        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        prompt.setText(blueEyesWhiteDragon[2]);
        ans_1.setText(answer1[2]);
        ans_2.setText(answer2[2]);
        ans_3.setText(answer3[2]);
        ans_4.setText(answer4[2]);
    }
    public void questionSetFour(){
        TextView prompt = (TextView) findViewById(R.id.question);
        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        prompt.setText(blueEyesWhiteDragon[3]);
        ans_1.setText(answer1[3]);
        ans_2.setText(answer2[3]);
        ans_3.setText(answer3[3]);
        ans_4.setText(answer4[3]);
    }
    public void questionSetFive(){
        TextView prompt = (TextView) findViewById(R.id.question);
        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        prompt.setText(blueEyesWhiteDragon[4]);
        ans_1.setText(answer1[4]);
        ans_2.setText(answer2[4]);
        ans_3.setText(answer3[4]);
        ans_4.setText(answer4[4]);
    }
    public void questionSetSix(){
        TextView prompt = (TextView) findViewById(R.id.question);
        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        prompt.setText(blueEyesWhiteDragon[5]);
        ans_1.setText(answer1[5]);
        ans_2.setText(answer2[5]);
        ans_3.setText(answer3[5]);
        ans_4.setText(answer4[5]);
    }
    public void questionSetSeven(){
        TextView prompt = (TextView) findViewById(R.id.question);
        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        prompt.setText(blueEyesWhiteDragon[6]);
        ans_1.setText(answer1[6]);
        ans_2.setText(answer2[6]);
        ans_3.setText(answer3[6]);
        ans_4.setText(answer4[6]);
    }
    public void questionSetEight(){
        TextView prompt = (TextView) findViewById(R.id.question);
        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        prompt.setText(blueEyesWhiteDragon[7]);
        ans_1.setText(answer1[7]);
        ans_2.setText(answer2[7]);
        ans_3.setText(answer3[7]);
        ans_4.setText(answer4[7]);
    }
    public void questionSetNine(){
        TextView prompt = (TextView) findViewById(R.id.question);
        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        prompt.setText(blueEyesWhiteDragon[8]);
        ans_1.setText(answer1[8]);
        ans_2.setText(answer2[8]);
        ans_3.setText(answer3[8]);
        ans_4.setText(answer4[8]);
    }
    public void questionSetTen(){
        TextView prompt = (TextView) findViewById(R.id.question);
        RadioButton ans_1 = (RadioButton) findViewById(R.id.ans_1);
        RadioButton ans_2 = (RadioButton) findViewById(R.id.ans_2);
        RadioButton ans_3 = (RadioButton) findViewById(R.id.ans_3);
        RadioButton ans_4 = (RadioButton) findViewById(R.id.ans_4);

        prompt.setText(blueEyesWhiteDragon[9]);
        ans_1.setText(answer1[9]);
        ans_2.setText(answer2[9]);
        ans_3.setText(answer3[9]);
        ans_4.setText(answer4[9]);
    }
}