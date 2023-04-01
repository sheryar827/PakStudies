package com.alltech4u.pakstudies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alltech4u.pakstudies.database.books;
import com.github.lzyzsd.circleprogress.ArcProgress;

public class QuestionOfTheDay extends AppCompatActivity {
    private books Books;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;
    private ImageView imageShare;
    private View v;
    private boolean question_answer_Sound;
    private String ans=null;
    private static long COUNTDOWN_IN_MILLIS = 60000;
    private long timeLeftInMillis = 0;
    ArcProgress question_timer;
    private int mQuestionNumber = 0;
    private MediaPlayer correctPlayer, incorrectPlayer;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.pref_main, false);
        setContentView(R.layout.activity_question_of_the_day);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        correctPlayer = MediaPlayer.create(this, R.raw.correct);
        incorrectPlayer = MediaPlayer.create(this, R.raw.incorrect);
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);
        mButtonChoice4 = (Button) findViewById(R.id.choice4);
        question_timer = (ArcProgress) findViewById(R.id.question_timer);
        question_timer.setBottomText("TIME");
        question_timer.setFinishedStrokeColor(Color.parseColor("#e9403d"));
        question_timer.setTextColor(Color.parseColor("#e9403d"));
        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Now the linking of all the datbase files with the Question activity
        Books = books.getInstance(getApplicationContext());
        Books.open();
        Intent intent=this.getIntent();
        mQuestionNumber=intent.getExtras().getInt("PAGE_KEY");
        firstQuestion();
    }

    private void firstQuestion() {
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        question_timer.setMax((int)timeLeftInMillis/1000);
        startCountDown();

        mQuestionView.setText(Books.readQuestion(mQuestionNumber));
        mButtonChoice1.setText(Books.readOptionA(mQuestionNumber));
        mButtonChoice2.setText(Books.readOptionB(mQuestionNumber));
        mButtonChoice3.setText(Books.readOptionC(mQuestionNumber));
        mButtonChoice4.setText(Books.readOptionD(mQuestionNumber));
        ans = Books.readAnswer(mQuestionNumber);
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                question_timer.setProgress((int)timeLeftInMillis/1000);

                if((int)timeLeftInMillis/1000 < 10){
                    question_timer.setFinishedStrokeColor(Color.parseColor("#970000"));
                    question_timer.setTextColor(Color.parseColor("#970000"));
                    //donutProgress.setPrefixText("");
                    question_timer.setSuffixText("");
                }else {
                    question_timer.setFinishedStrokeColor(Color.parseColor("#007400"));
                    question_timer.setTextColor(Color.parseColor("#007400"));
                    //donutProgress.setPrefixText("");
                    question_timer.setSuffixText("");
                }

            }
            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                question_timer.setProgress(0);
                checkAnsWhenTimeFinish();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishQuiz();
                    }
                }, 1000);
            }
        }.start();
    }

    private void checkAnsWhenTimeFinish(){
        if (ans.equals("A")) {
            mButtonChoice1.setBackgroundColor(Color.GREEN);
        }else if (ans.equals("B")) {
            mButtonChoice2.setBackgroundColor(Color.GREEN);
        }else if (ans.equals("C")) {
            mButtonChoice3.setBackgroundColor(Color.GREEN);
        }else if (ans.equals("D")) {
            mButtonChoice4.setBackgroundColor(Color.GREEN);
        }
        mButtonChoice1.setEnabled(false);
        mButtonChoice2.setEnabled(false);
        mButtonChoice3.setEnabled(false);
        mButtonChoice4.setEnabled(false);
    }

    public void onClick(View view){
        v=view;
        if (view.getId() == R.id.quit){
            AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(QuestionOfTheDay.this);
            LayoutInflater inflater = getLayoutInflater();
            View appear = inflater.inflate(R.layout.quit_quiz, null);
            Button noBtn = (Button) appear.findViewById(R.id.notleavequiz);
            Button yesBtn = (Button) appear.findViewById(R.id.leavequiz);
            //Button btttestBtn = (Button) appear.findViewById(R.id.prep);
            testmodeDialog.setView(appear);
            final AlertDialog modeDialog = testmodeDialog.create();
            modeDialog.show();
            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modeDialog.cancel();
                }
            });

            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    countDownTimer.cancel();
                    modeDialog.cancel();
                }
            });

            modeDialog.setCanceledOnTouchOutside(false);
        }else if(view.getId()==R.id.skip){

        }else if (view.getId() == R.id.bthSetting) {
            Intent intent = new Intent(QuestionOfTheDay.this, SettingsActivity.class);
            startActivity(intent);
        }else{
            checkAnswer();
            mButtonChoice1.setEnabled(false);
            mButtonChoice2.setEnabled(false);
            mButtonChoice3.setEnabled(false);
            mButtonChoice4.setEnabled(false);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishQuiz();
                }
            }, 1000);

        }
    }

    private void checkAnswer(){
        //SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        //To play background sound
        if (question_answer_Sound) {
            if (ans.equals("A")) {
                if (v.getId() == R.id.choice1) {
                    mButtonChoice1.setBackgroundColor(Color.GREEN);
                    correctPlayer.start();
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice1.setBackgroundColor(Color.GREEN);
                    incorrectPlayer.start();
                }
            } else if (ans.equals("B")) {
                if (v.getId() == R.id.choice2) {
                    mButtonChoice2.setBackgroundColor(Color.GREEN);
                    correctPlayer.start();
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice2.setBackgroundColor(Color.GREEN);
                    incorrectPlayer.start();
                }
            } else if (ans.equals("C")) {
                if (v.getId() == R.id.choice3) {
                    mButtonChoice3.setBackgroundColor(Color.GREEN);
                    correctPlayer.start();
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice3.setBackgroundColor(Color.GREEN);
                    incorrectPlayer.start();
                }
            } else if (ans.equals("D")) {
                if (v.getId() == R.id.choice4) {
                    mButtonChoice4.setBackgroundColor(Color.GREEN);
                    correctPlayer.start();
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice4.setBackgroundColor(Color.GREEN);
                    incorrectPlayer.start();
                }
            }
        }else {

            if (ans.equals("A")) {
                if (v.getId() == R.id.choice1) {
                    mButtonChoice1.setBackgroundColor(Color.GREEN);
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice1.setBackgroundColor(Color.GREEN);
                }
            } else if (ans.equals("B")) {
                if (v.getId() == R.id.choice2) {
                    mButtonChoice2.setBackgroundColor(Color.GREEN);
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice2.setBackgroundColor(Color.GREEN);
                }
            } else if (ans.equals("C")) {
                if (v.getId() == R.id.choice3) {
                    mButtonChoice3.setBackgroundColor(Color.GREEN);
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice3.setBackgroundColor(Color.GREEN);
                }
            } else if (ans.equals("D")) {
                if (v.getId() == R.id.choice4) {
                    mButtonChoice4.setBackgroundColor(Color.GREEN);
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice4.setBackgroundColor(Color.GREEN);
                }
            }
        }
    }

    private void finishQuiz(){
        countDownTimer.cancel();
        AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(QuestionOfTheDay.this);
        LayoutInflater inflater = getLayoutInflater();
        View appear = inflater.inflate(R.layout.question_answered, null);
        Button morequestionsBtn = (Button) appear.findViewById(R.id.morequestionsBtn);
        Button closeBtn = (Button) appear.findViewById(R.id.closeBtn);
        testmodeDialog.setView(appear);
        final AlertDialog modeDialog = testmodeDialog.create();
        modeDialog.show();
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeDialog.cancel();
                finish();
            }
        });
        morequestionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(QuestionOfTheDay.this, MyQuizBook.class);
                startActivity(intent);
                modeDialog.cancel();
            }
        });
        modeDialog.setCanceledOnTouchOutside(false);
    }

    private boolean MystartActivity(Intent intent)
    {
        try {
            startActivity(intent);
            return true;
        }catch (ActivityNotFoundException e) {
            return false;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        imageShare = (ImageView) menu.findItem(R.id.action_share)
                .getActionView();
        imageShare.setImageResource(R.drawable.ic_share_black_24dp);
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        imageShare.startAnimation(zoomAnimation);

        imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareSub = ""+getString(R.string.share_sub);
                String shareBody = ""+getString(R.string.share_body1)+"\n"+
                        getString(R.string.share_body2)+"\n"+
                        getString(R.string.share_body3);
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(QuestionOfTheDay.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_rateus) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("details?id="+getString(R.string.app_id)));
            if(!MystartActivity(intent))
            {
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+getString(R.string.app_id)));
                if(!MystartActivity(intent))
                {
                    Toast.makeText(getApplicationContext(),"Could not open Android market", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }else if (id == R.id.action_moreapps) {
            try {
                //replace &quot;Unified+Apps&quot; with your developer name
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Urdu%20TechCrunch&hl=en")));
            }
            catch (ActivityNotFoundException anfe) {
                //replace &quot;Unified+Apps&quot; with your developer name
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/search?q=pub:Urdu%20TechCrunch&hl=en")));
            }
            return true;
        }else if (id == R.id.action_scorecard) {
            AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(QuestionOfTheDay.this);
            LayoutInflater inflater = getLayoutInflater();
            View appear = inflater.inflate(R.layout.test_mode, null);
            Button nttestBtn = (Button) appear.findViewById(R.id.test);
            Button ttBtn = (Button) appear.findViewById(R.id.btt);
            Button btttestBtn = (Button) appear.findViewById(R.id.prep);
            testmodeDialog.setView(appear);
            final AlertDialog modeDialog = testmodeDialog.create();
            modeDialog.show();

            nttestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QuestionOfTheDay.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",1);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });

            ttBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QuestionOfTheDay.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",2);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });

            btttestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QuestionOfTheDay.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",3);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });
            return true;
        }else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(QuestionOfTheDay.this);
        LayoutInflater inflater = getLayoutInflater();
        View appear = inflater.inflate(R.layout.quit_quiz, null);
        Button noBtn = (Button) appear.findViewById(R.id.notleavequiz);
        Button yesBtn = (Button) appear.findViewById(R.id.leavequiz);
        //Button btttestBtn = (Button) appear.findViewById(R.id.prep);
        testmodeDialog.setView(appear);
        final AlertDialog modeDialog = testmodeDialog.create();
        modeDialog.show();
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeDialog.cancel();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                modeDialog.cancel();
                finish();
            }
        });

        modeDialog.setCanceledOnTouchOutside(false);
        //super.onBackPressed();
    }

    @Override
    public void onResume(){
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String question_timer = sharedPref.getString(SettingsActivity.KEY_PREF_EXAMPLE_SWITCH,null);
        //question_attempt_timer = Integer.parseInt(question_timer);
        COUNTDOWN_IN_MILLIS = Integer.parseInt(question_timer)*1000;
        question_answer_Sound = sharedPref.getBoolean
                (SettingsActivity.KEY_QUESTIONS_ANSWER_SOUNDS, false);
        //Toast.makeText(this, question_timer, Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    public boolean isFinishing(){
        countDownTimer.cancel();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //close database
        Books.close();
    }
}
