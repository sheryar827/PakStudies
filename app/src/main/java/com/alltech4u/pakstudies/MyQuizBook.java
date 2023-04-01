package com.alltech4u.pakstudies;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.alltech4u.pakstudies.database.books;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

import java.util.ArrayList;
import java.util.Collections;

import guy4444.smartrate.SmartRate;

public class MyQuizBook extends AppCompatActivity {
    AlertDialog.Builder builder;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;
    private AdView adView;
    private ImageButton scorecard;
    ArrayList<Integer> list = new ArrayList<Integer>();
    private Button mQuit;
    private ImageView imageShare;
    private ImageButton imageButton, rateapp, disapp, pageapp, moreapp;
    NotificationCompat.Builder notification;
    books Books;

    private static final int uniqueID = 45612;
    public static final String PREFS_NAME = "data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quiz_book);

        // For continual calls -
        SmartRate.Rate(MyQuizBook.this
                , "Rate Us"
                , "Tell others what you think about this app"
                , "Continue"
                , "Please take a moment and rate us on Google Play"
                , "click here"
                , "Ask me later"
                , "Never ask again"
                , "Cancel"
                , "Thanks for the feedback"
                , Color.parseColor("#2196F3")
                , 3
                , 48
                , 72
        );

        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();

        //Now the linking of all the datbase files with the Question activity
        Books = books.getInstance(getApplicationContext());
        Books.open();

        for(int i=1; i < 770; i++){
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.drawable.pakstudies);
        notification.setTicker("This is the ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Question of the day");
        notification.setContentText(Books.readQuestion(list.get(150)));
        //close the database
        Books.close();
        Intent notifyIntent = new Intent(this,QuestionOfTheDay.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notifyIntent.putExtra("PAGE_KEY", list.get(150));
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        notification.setContentIntent(notifyPendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        //NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID,notification.build());


        PreferenceManager.setDefaultValues(this, R.xml.pref_main, false);
        imageButton = (ImageButton) findViewById(R.id.bthSetting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);
        mButtonChoice4 = (Button) findViewById(R.id.choice4);
        mQuit = (Button) findViewById(R.id.quit);
        //scorecard = (ImageButton) findViewById(R.id.pagebtn);
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

    public void onClick(View v) {
        if (v.getId() == R.id.choice1) {
            Intent intent = new Intent(MyQuizBook.this, MainActivity.class);
            intent.putExtra("Quiz_Mode", 1);
            startActivity(intent);
        }else if (v.getId() == R.id.choice2) {
            Intent intent = new Intent(MyQuizBook.this, MainActivity.class);
            intent.putExtra("Quiz_Mode", 2);
            startActivity(intent);
        }else if (v.getId() == R.id.choice3) {
            Intent intent = new Intent(MyQuizBook.this, MainActivity.class);
            intent.putExtra("Quiz_Mode", 3);
            startActivity(intent);
        }else if (v.getId() == R.id.choice4) {
            Intent intent = new Intent(MyQuizBook.this, MainActivity.class);
            intent.putExtra("Quiz_Mode", 4);
            startActivity(intent);
        }else if (v.getId() == R.id.bthSetting) {
            Intent intent = new Intent(MyQuizBook.this, SettingsActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.quit) {
            AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(MyQuizBook.this);
            LayoutInflater inflater = getLayoutInflater();
            View appear = inflater.inflate(R.layout.exit_dialog, null);
            Button laterBtn = (Button) appear.findViewById(R.id.btn_later);
            Button rateusBtn = (Button) appear.findViewById(R.id.btn_rateus);
            testmodeDialog.setView(appear);
            final AlertDialog modeDialog = testmodeDialog.create();
            modeDialog.show();
            laterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modeDialog.cancel();
                    finish();
                }
            });
            rateusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("details?id=urdutechcrunch.com.ntsgk2018"));
                    if(!MystartActivity(intent))
                    {
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=urdutechcrunch.com.ntsgk2018"));
                        if(!MystartActivity(intent))
                        {
                            Toast.makeText(getApplicationContext(),"Could not open Android market", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            modeDialog.setCanceledOnTouchOutside(false);
        }else if (v.getId() == R.id.rate_us) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("details?id=urdutechcrunch.com.ntsgk2018"));
            if(!MystartActivity(intent))
            {
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=urdutechcrunch.com.ntsgk2018"));
                if(!MystartActivity(intent))
                {
                    Toast.makeText(getApplicationContext(),"Could not open Android market", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quiz_start, menu);
        imageShare = (ImageView) menu.findItem(R.id.action_settings)
                .getActionView();
        imageShare.setImageResource(R.drawable.ic_settings_black_24dp);
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        imageShare.startAnimation(zoomAnimation);
        imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQuizBook.this, SettingsActivity.class);
                startActivity(intent);
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


        if (id == R.id.action_share) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareSub = ""+getString(R.string.share_sub);
            String shareBody = ""+getString(R.string.share_body1)+"\n"+
                    getString(R.string.share_body2)+"\n"+
                    getString(R.string.share_body3);
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(myIntent, "Share Using"));
            return true;
        }else if (id == R.id.action_rateus) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("details?id="+getString(R.string.app_id)));
            if(!MystartActivity(intent))
            {
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+R.string.app_id));
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
            AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(MyQuizBook.this);
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
                    Intent intent = new Intent(MyQuizBook.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",1);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });

            ttBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyQuizBook.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",2);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });

            btttestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyQuizBook.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",3);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });
            return true;
        }else if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(MyQuizBook.this);
        LayoutInflater inflater = getLayoutInflater();
        View appear = inflater.inflate(R.layout.exit_dialog, null);
        Button laterBtn = (Button) appear.findViewById(R.id.btn_later);
        Button rateusBtn = (Button) appear.findViewById(R.id.btn_rateus);
        testmodeDialog.setView(appear);
        final AlertDialog modeDialog = testmodeDialog.create();
        modeDialog.show();
        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeDialog.cancel();
                finish();
            }
        });
        rateusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("details?id=urdutechcrunch.com.ntsgk2018"));
                if(!MystartActivity(intent))
                {
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=urdutechcrunch.com.ntsgk2018"));
                    if(!MystartActivity(intent))
                    {
                        Toast.makeText(getApplicationContext(),"Could not open Android market", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        modeDialog.setCanceledOnTouchOutside(false);
        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(MyQuizBook.this);
        }
        else
        {
            builder = new AlertDialog.Builder(MyQuizBook.this, AlertDialog.BUTTON_NEUTRAL);
        }
        builder.setTitle("Thank You");
        builder.setMessage("Thank You For Using Our Application Please Give Us Your Suggestions and Feedback ");
        builder.setNegativeButton("RATE US",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=ADD YOUR APPS PACKAGE NAME")); // Add package name of your application
                        startActivity(intent);
                        Toast.makeText(MyQuizBook.this, "Thank you for your Rating",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setPositiveButton("QUIT",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        finish();
                    }
                });

        builder.show();*/
    }

}


