package com.alltech4u.pakstudies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.alltech4u.pakstudies.database.books;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.ArrayList;
import java.util.Collections;

public class BTTModeQuizActivity extends AppCompatActivity {

    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;
    private Button mQuit;
    private View v;
    private ImageView imageShare;
    private boolean question_answer_Sound;
    ArcProgress question_attempted;
    ArcProgress question_correct;
    ArcProgress question_timer;

    String ans = null;

    private String mAnswer;
    private int mScore = 0, i = 0, j = 0, skip = 0;
    private int mQuestionNumber = 0;
    ArrayList<Integer> list = new ArrayList<Integer>();
    MediaPlayer correctPlayer, incorrectPlayer;
    private static long COUNTDOWN_IN_MILLIS = 0;
    private long timeLeftInMillis = 0;
    private CountDownTimer countDownTimer;
    private ImageButton imageButton, rateapp, disapp, pageapp, moreapp;
    books Books;
    int page = 0;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_mode_quiz);

        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, ActivityConfig.FB_BANNER, AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();

        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String question_att_timer = sharedPref.getString(SettingsActivity.KEY_SET_BTT_QUIZ_TIMER, null);
        COUNTDOWN_IN_MILLIS = Integer.parseInt(question_att_timer) * 1000;

        imageButton = (ImageButton) findViewById(R.id.bthSetting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        //imageButton.startAnimation(zoomAnimation);

        Intent intent = this.getIntent();
        page = intent.getExtras().getInt("PAGE_KEY");
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);
        mButtonChoice4 = (Button) findViewById(R.id.choice4);
        mQuit = (Button) findViewById(R.id.quit);
        question_attempted = (ArcProgress) findViewById(R.id.question_attempted);
        question_attempted.setFinishedStrokeColor(Color.parseColor("#FFD4CE2E"));
        question_attempted.setTextColor(Color.parseColor("#FFD4CE2E"));
        question_correct = (ArcProgress) findViewById(R.id.question_correct);
        question_correct.setFinishedStrokeColor(Color.parseColor("#51b364"));
        question_correct.setTextColor(Color.parseColor("#51b364"));
        question_timer = (ArcProgress) findViewById(R.id.question_timer);
        question_timer.setBottomText("TIME");
        question_timer.setFinishedStrokeColor(Color.parseColor("#e9403d"));
        question_timer.setTextColor(Color.parseColor("#e9403d"));
        question_attempted.setMax(100);
        question_correct.setMax(100);
        question_timer.setMax(Integer.parseInt(question_att_timer));
        correctPlayer = MediaPlayer.create(this, R.raw.correct);
        incorrectPlayer = MediaPlayer.create(this, R.raw.incorrect);

        //Get the quiz end point
        int quesendPoint = (page * 10) + 1;
        //Get the qui starting point
        int quesstartPoint = (page - 1) * 10;
        if (quesstartPoint == 0) {
            quesstartPoint = 1;
        }
        for (i = quesstartPoint; i < quesendPoint; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);

        //Now the linking of all the datbase files with the Question activity
        Books = books.getInstance(getApplicationContext());
        Books.open();

        getSupportActionBar().setTitle("Quiz " + page + "");

        firstQuestion();
        startCountDown();
    }

    private void firstQuestion() {
        mButtonChoice1.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice2.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice3.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice4.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice1.setEnabled(true);
        mButtonChoice2.setEnabled(true);
        mButtonChoice3.setEnabled(true);
        mButtonChoice4.setEnabled(true);

        attempted_progress();
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        question_timer.setMax((int) timeLeftInMillis / 1000);
        mQuestionNumber = list.get(j);

        mQuestionView.setText(Books.readQuestion(mQuestionNumber));
        mButtonChoice1.setText(Books.readOptionA(mQuestionNumber));
        mButtonChoice2.setText(Books.readOptionB(mQuestionNumber));
        mButtonChoice3.setText(Books.readOptionC(mQuestionNumber));
        mButtonChoice4.setText(Books.readOptionD(mQuestionNumber));
        ans = Books.readAnswer(mQuestionNumber);

        //mQuit.setText(ans);
    }

    //Next question in the quiz
    private void updateQuestion() {
        mButtonChoice1.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice2.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice3.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice4.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice1.setEnabled(true);
        mButtonChoice2.setEnabled(true);
        mButtonChoice3.setEnabled(true);
        mButtonChoice4.setEnabled(true);
        j++;
        if (j < 10) {
            mQuestionNumber = list.get(j);
            attempted_progress();
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            question_timer.setMax((int) timeLeftInMillis / 1000);
        } else {
            finishQuiz();
        }
        mQuestionView.setText(Books.readQuestion(mQuestionNumber));
        mButtonChoice1.setText(Books.readOptionA(mQuestionNumber));
        mButtonChoice2.setText(Books.readOptionB(mQuestionNumber));
        mButtonChoice3.setText(Books.readOptionC(mQuestionNumber));
        mButtonChoice4.setText(Books.readOptionD(mQuestionNumber));
        ans = Books.readAnswer(mQuestionNumber);
        //mQuit.setText(ans);
    }

    //Skip the question in the quiz
    private void skipQuestion() {
        //notimemodeSV.startAnimation(LeftSwipe);
        mButtonChoice1.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice2.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice3.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice4.setBackgroundColor(Color.parseColor("#0091EA"));
        mButtonChoice1.setEnabled(true);
        mButtonChoice2.setEnabled(true);
        mButtonChoice3.setEnabled(true);
        mButtonChoice4.setEnabled(true);

        j++;
        skip++;
        if (j < 10) {
            mQuestionNumber = list.get(j);
            attempted_progress();
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        } else {
            finishQuiz();
        }

        mQuestionView.setText(Books.readQuestion(mQuestionNumber));
        mButtonChoice1.setText(Books.readOptionA(mQuestionNumber));
        mButtonChoice2.setText(Books.readOptionB(mQuestionNumber));
        mButtonChoice3.setText(Books.readOptionC(mQuestionNumber));
        mButtonChoice4.setText(Books.readOptionD(mQuestionNumber));
        ans = Books.readAnswer(mQuestionNumber);
        //mQuit.setText(ans);
    }

    //timer for quiz
    private void startCountDown() {
        countDownTimer = new CountDownTimer(COUNTDOWN_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                question_timer.setProgress((int) timeLeftInMillis / 1000);

                if ((int) timeLeftInMillis / 1000 < 10) {
                    question_timer.setFinishedStrokeColor(Color.parseColor("#970000"));
                    question_timer.setTextColor(Color.parseColor("#970000"));
                    //donutProgress.setPrefixText("");
                    question_timer.setSuffixText("");
                } else {
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
                finishQuiz();
            }
        }.start();
    }

    private void checkAnsWhenTimeFinish() {
        if (ans.equals("A")) {
            mButtonChoice1.setBackgroundColor(Color.GREEN);
        } else if (ans.equals("B")) {
            mButtonChoice2.setBackgroundColor(Color.GREEN);
        } else if (ans.equals("C")) {
            mButtonChoice3.setBackgroundColor(Color.GREEN);
        } else if (ans.equals("D")) {
            mButtonChoice4.setBackgroundColor(Color.GREEN);
        }
        mButtonChoice1.setEnabled(false);
        mButtonChoice2.setEnabled(false);
        mButtonChoice3.setEnabled(false);
        mButtonChoice4.setEnabled(false);
        correct_incorrect_progress();
    }

    //score for quiz
    private void updateScore() {
        mScore = mScore + 1;
        correct_incorrect_progress();
    }

    //check which button is clicked
    public void onClick(View view) {
        v = view;
        if (v.getId() == R.id.quit) {
            AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(BTTModeQuizActivity.this);
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
                    finishQuiz();
                    modeDialog.cancel();
                }
            });

            modeDialog.setCanceledOnTouchOutside(false);
        } else if (v.getId() == R.id.skip) {
            skipQuestion();
        } else if (v.getId() == R.id.bthSetting) {
            Intent intent = new Intent(BTTModeQuizActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else {
            checkAnswer();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateQuestion();
                }
            }, 1000);
            mButtonChoice1.setEnabled(false);
            mButtonChoice2.setEnabled(false);
            mButtonChoice3.setEnabled(false);
            mButtonChoice4.setEnabled(false);
        }
    }

    //check the answer
    private void checkAnswer() {
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        //To play background sound
        if (question_answer_Sound) {
            if (ans.equals("A")) {
                if (v.getId() == R.id.choice1) {
                    updateScore();
                    mButtonChoice1.setBackgroundColor(Color.GREEN);
                    correctPlayer.start();
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice1.setBackgroundColor(Color.GREEN);
                    incorrectPlayer.start();
                    correct_incorrect_progress();
                }
            } else if (ans.equals("B")) {
                if (v.getId() == R.id.choice2) {
                    updateScore();
                    mButtonChoice2.setBackgroundColor(Color.GREEN);
                    correctPlayer.start();
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice2.setBackgroundColor(Color.GREEN);
                    incorrectPlayer.start();
                    correct_incorrect_progress();
                }
            } else if (ans.equals("C")) {
                if (v.getId() == R.id.choice3) {
                    updateScore();
                    mButtonChoice3.setBackgroundColor(Color.GREEN);
                    correctPlayer.start();
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice3.setBackgroundColor(Color.GREEN);
                    incorrectPlayer.start();
                    correct_incorrect_progress();
                }
            } else if (ans.equals("D")) {
                if (v.getId() == R.id.choice4) {
                    updateScore();
                    mButtonChoice4.setBackgroundColor(Color.GREEN);
                    correctPlayer.start();
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice4.setBackgroundColor(Color.GREEN);
                    incorrectPlayer.start();
                    correct_incorrect_progress();
                }
            }
        } else {

            if (ans.equals("A")) {
                if (v.getId() == R.id.choice1) {
                    updateScore();
                    mButtonChoice1.setBackgroundColor(Color.GREEN);
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice1.setBackgroundColor(Color.GREEN);
                    correct_incorrect_progress();
                }
            } else if (ans.equals("B")) {
                if (v.getId() == R.id.choice2) {
                    updateScore();
                    mButtonChoice2.setBackgroundColor(Color.GREEN);
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice2.setBackgroundColor(Color.GREEN);
                    correct_incorrect_progress();
                }
            } else if (ans.equals("C")) {
                if (v.getId() == R.id.choice3) {
                    updateScore();
                    mButtonChoice3.setBackgroundColor(Color.GREEN);
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice3.setBackgroundColor(Color.GREEN);
                    correct_incorrect_progress();
                }
            } else if (ans.equals("D")) {
                if (v.getId() == R.id.choice4) {
                    updateScore();
                    mButtonChoice4.setBackgroundColor(Color.GREEN);
                    //This line of code is optiona
                } else {
                    Button wrongBtn = (Button) findViewById(v.getId());
                    wrongBtn.setBackgroundColor(Color.RED);
                    mButtonChoice4.setBackgroundColor(Color.GREEN);
                    correct_incorrect_progress();
                }
            }
        }
    }

    //finish the quiz
    private void finishQuiz() {
        countDownTimer.cancel();
        SharedPreferences quizmode = getSharedPreferences("three", 0);
        SharedPreferences.Editor qm = quizmode.edit();
        int maxScore = mScore * 10;
        if (quizmode.getInt("Quiz" + page + "", 0) < maxScore) {
            qm.putInt("Quiz" + page + "", maxScore);
            qm.apply();
        }

        AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(BTTModeQuizActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View appear = inflater.inflate(R.layout.next_quiz, null);
        TextView attempted = (TextView) appear.findViewById(R.id.attempted);
        TextView correct = (TextView) appear.findViewById(R.id.correct);
        TextView incorrect = (TextView) appear.findViewById(R.id.incorrect);
        Button nextLevel = (Button) appear.findViewById(R.id.nextlevel);
        Button quitPlay = (Button) appear.findViewById(R.id.quitplay);
        j = (j + 1) - skip;
        if (j > 10) {
            j = 10;
        }
        attempted.setText(Integer.toString(j));
        correct.setText(Integer.toString(mScore));
        int incor = j - mScore;
        incorrect.setText(Integer.toString(incor));
        testmodeDialog.setView(appear);
        final AlertDialog modeDialog = testmodeDialog.create();
        modeDialog.show();

        quitPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeDialog.cancel();
                countDownTimer.cancel();
                finish();
            }
        });

        nextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page == Integer.parseInt(getString(R.string.total_quiz))) {
                    finish();
                } else {

                    Intent intent = new Intent(BTTModeQuizActivity.this, BTTModeQuizActivity.class);
                    intent.putExtra("PAGE_KEY", page + 1);
                    startActivity(intent);
                }
                modeDialog.cancel();

            }
        });

        modeDialog.setCanceledOnTouchOutside(false);

    }

    //how many questions attempted by the user
    private void attempted_progress() {
        int questionNum;
        questionNum = j + 1;
        question_attempted.setBottomText(Integer.toString(questionNum) + "/" + Integer.toString(10));
        int question_attempt_percentage = (questionNum * 100) / 10;
        question_attempted.setProgress(question_attempt_percentage);
    }

    //how many questions are correct and how many ar incorrect
    private void correct_incorrect_progress() {
        int cicp = (j + 1) - skip;
        question_correct.setBottomText(Integer.toString(mScore) + "/" + Integer.toString(cicp));
        int question_correct_progress = (mScore * 100) / cicp;
        question_correct.setProgress(question_correct_progress);
    }

    private boolean MystartActivity(Intent intent) {
        try {
            startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
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
                String shareSub = "" + getString(R.string.share_sub);
                String shareBody = "" + getString(R.string.share_body1) + "\n" +
                        getString(R.string.share_body2) + "\n" +
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
            Intent intent = new Intent(BTTModeQuizActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_moreapps) {
            try {
                //replace &quot;Unified+Apps&quot; with your developer name
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Urdu%20TechCrunch&hl=en")));
            } catch (ActivityNotFoundException anfe) {
                //replace &quot;Unified+Apps&quot; with your developer name
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/search?q=pub:Urdu%20TechCrunch&hl=en")));
            }
            return true;
        } else if (id == R.id.action_rateus) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("details?id=" + getString(R.string.app_id)));
            if (!MystartActivity(intent)) {
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getString(R.string.app_id)));
                if (!MystartActivity(intent)) {
                    Toast.makeText(getApplicationContext(), "Could not open Android market", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        } else if (id == R.id.action_scorecard) {
            AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(BTTModeQuizActivity.this);
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
                    Intent intent = new Intent(BTTModeQuizActivity.this, ScoreCard.class);
                    intent.putExtra("quiz_mode", 1);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });

            ttBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BTTModeQuizActivity.this, ScoreCard.class);
                    intent.putExtra("quiz_mode", 2);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });

            btttestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BTTModeQuizActivity.this, ScoreCard.class);
                    intent.putExtra("quiz_mode", 3);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(BTTModeQuizActivity.this);
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
                finish();
                modeDialog.cancel();

            }
        });

        modeDialog.setCanceledOnTouchOutside(false);
        //super.onBackPressed();
    }

    @Override
    public void onResume() {
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String question_timer = sharedPref.getString(SettingsActivity.KEY_SET_BTT_QUIZ_TIMER, null);
        //question_attempt_timer = Integer.parseInt(question_timer);
        COUNTDOWN_IN_MILLIS = Integer.parseInt(question_timer) * 1000;
        question_answer_Sound = sharedPref.getBoolean
                (SettingsActivity.KEY_QUESTIONS_ANSWER_SOUNDS, false);
        //Toast.makeText(this, question_timer, Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    public boolean isFinishing() {
        countDownTimer.cancel();
        return true;
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
        //close database
        Books.close();
    }
}


