package com.alltech4u.pakstudies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

public class ScoreCard extends AppCompatActivity {
    TextView a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, qm;
    TextView a11, a12, a13, a14, a15, a16, a17, a18, a19, a20;
    TextView a21, a22, a23, a24, a25, a26, a27, a28, a29, a30;
    TextView a31, a32, a33, a34, a35, a36, a37, a38, a39, a40;
    TextView a41, a42, a43, a44, a45, a46, a47, a48, a49, a50;
    TextView a51, a52, a53, a54, a55, a56, a57, a58, a59, a60;
    TextView a61, a62, a63, a64, a65, a66, a67, a68, a69, a70;
    TextView a71, a72, a73, a74, a75, a76, a77, a78, a79, a80;
    TextView a81, a82, a83, a84, a85, a86, a87, a88, a89, a90;
    TextView a91, a92;
    private ImageView imageShare;
    int quiz_mode=0;
    private SharedPreferences sharedPreferences;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, ActivityConfig.FB_BANNER, AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        quiz_mode = intent.getIntExtra("quiz_mode",0);
        a1 = (TextView) findViewById(R.id.quiz1);
        a2 = (TextView) findViewById(R.id.quiz2);
        a3 = (TextView) findViewById(R.id.quiz3);
        a4 = (TextView) findViewById(R.id.quiz4);
        a5 = (TextView) findViewById(R.id.quiz5);
        a6 = (TextView) findViewById(R.id.quiz6);
        a7 = (TextView) findViewById(R.id.quiz7);
        a8 = (TextView) findViewById(R.id.quiz8);
        a9 = (TextView) findViewById(R.id.quiz9);
        a10 = (TextView) findViewById(R.id.quiz10);
        a11 = (TextView) findViewById(R.id.quiz11);
        a12 = (TextView) findViewById(R.id.quiz12);
        a13 = (TextView) findViewById(R.id.quiz13);
        a14 = (TextView) findViewById(R.id.quiz14);
        a15 = (TextView) findViewById(R.id.quiz15);
        a16 = (TextView) findViewById(R.id.quiz16);
        a17 = (TextView) findViewById(R.id.quiz17);
        a18 = (TextView) findViewById(R.id.quiz18);
        a19 = (TextView) findViewById(R.id.quiz19);
        a20 = (TextView) findViewById(R.id.quiz20);
        a21 = (TextView) findViewById(R.id.quiz21);
        a22 = (TextView) findViewById(R.id.quiz22);
        a23 = (TextView) findViewById(R.id.quiz23);
        a24 = (TextView) findViewById(R.id.quiz24);
        a25 = (TextView) findViewById(R.id.quiz25);
        a26 = (TextView) findViewById(R.id.quiz26);
        a27 = (TextView) findViewById(R.id.quiz27);
        a28 = (TextView) findViewById(R.id.quiz28);
        a29 = (TextView) findViewById(R.id.quiz29);
        a30 = (TextView) findViewById(R.id.quiz30);
        a31 = (TextView) findViewById(R.id.quiz31);
        a32 = (TextView) findViewById(R.id.quiz32);
        a33 = (TextView) findViewById(R.id.quiz33);
        a34 = (TextView) findViewById(R.id.quiz34);
        a35 = (TextView) findViewById(R.id.quiz35);
        a36 = (TextView) findViewById(R.id.quiz36);
        a37 = (TextView) findViewById(R.id.quiz37);
        a38 = (TextView) findViewById(R.id.quiz38);
        a39 = (TextView) findViewById(R.id.quiz39);
        a40 = (TextView) findViewById(R.id.quiz40);
        a41 = (TextView) findViewById(R.id.quiz41);
        a42 = (TextView) findViewById(R.id.quiz42);
        a43 = (TextView) findViewById(R.id.quiz43);
        a44 = (TextView) findViewById(R.id.quiz44);
        a45 = (TextView) findViewById(R.id.quiz45);
        a46 = (TextView) findViewById(R.id.quiz46);
        a47 = (TextView) findViewById(R.id.quiz47);
        a48 = (TextView) findViewById(R.id.quiz48);
        a49 = (TextView) findViewById(R.id.quiz49);
        a50 = (TextView) findViewById(R.id.quiz50);
        a51 = (TextView) findViewById(R.id.quiz51);
        a52 = (TextView) findViewById(R.id.quiz52);
        a53 = (TextView) findViewById(R.id.quiz53);
        a54 = (TextView) findViewById(R.id.quiz54);
        a55 = (TextView) findViewById(R.id.quiz55);
        a56 = (TextView) findViewById(R.id.quiz56);
        a57 = (TextView) findViewById(R.id.quiz57);
        a58 = (TextView) findViewById(R.id.quiz58);
        a59 = (TextView) findViewById(R.id.quiz59);
        a60 = (TextView) findViewById(R.id.quiz60);
        a61 = (TextView) findViewById(R.id.quiz61);
        a62 = (TextView) findViewById(R.id.quiz62);
        a63 = (TextView) findViewById(R.id.quiz63);
        a64 = (TextView) findViewById(R.id.quiz64);
        a65 = (TextView) findViewById(R.id.quiz65);
        a66 = (TextView) findViewById(R.id.quiz66);
        a67 = (TextView) findViewById(R.id.quiz67);
        a68 = (TextView) findViewById(R.id.quiz68);
        a69 = (TextView) findViewById(R.id.quiz69);
        a70 = (TextView) findViewById(R.id.quiz70);
        a71 = (TextView) findViewById(R.id.quiz71);
        a72 = (TextView) findViewById(R.id.quiz72);
        a73 = (TextView) findViewById(R.id.quiz73);
        a74 = (TextView) findViewById(R.id.quiz74);
        a75 = (TextView) findViewById(R.id.quiz75);
        a76 = (TextView) findViewById(R.id.quiz76);
        a77 = (TextView) findViewById(R.id.quiz77);
        a78 = (TextView) findViewById(R.id.quiz78);
        a79 = (TextView) findViewById(R.id.quiz79);
        a80 = (TextView) findViewById(R.id.quiz80);
        a81 = (TextView) findViewById(R.id.quiz81);
        a82 = (TextView) findViewById(R.id.quiz82);
        a83 = (TextView) findViewById(R.id.quiz83);
        a84 = (TextView) findViewById(R.id.quiz84);
        a85 = (TextView) findViewById(R.id.quiz85);
        a86 = (TextView) findViewById(R.id.quiz86);
        a87 = (TextView) findViewById(R.id.quiz87);
        a88 = (TextView) findViewById(R.id.quiz88);
        a89 = (TextView) findViewById(R.id.quiz89);
        a90 = (TextView) findViewById(R.id.quiz90);
        a91 = (TextView) findViewById(R.id.quiz91);
        a92 = (TextView) findViewById(R.id.quiz92);

        qm = (TextView) findViewById(R.id.quizmode);
        if(quiz_mode==1) {
            sharedPreferences = getSharedPreferences("one", 0);
            qm.setText("No Time Mode Score Card");
        }else if(quiz_mode==2) {
            sharedPreferences = getSharedPreferences("two", 0);
            qm.setText("Time Mode Score Card");
        }else if(quiz_mode==3) {
            sharedPreferences = getSharedPreferences("three", 0);
            qm.setText("Beat The Time Mode Score Card");
        }
        try {
            a1.setText("" + sharedPreferences.getInt("Quiz1", 0));
            a2.setText("" + sharedPreferences.getInt("Quiz2", 0));
            a3.setText("" + sharedPreferences.getInt("Quiz3", 0));
            a4.setText("" + sharedPreferences.getInt("Quiz4", 0));
            a5.setText("" + sharedPreferences.getInt("Quiz5", 0));
            a6.setText("" + sharedPreferences.getInt("Quiz6", 0));
            a7.setText("" + sharedPreferences.getInt("Quiz7", 0));
            a8.setText("" + sharedPreferences.getInt("Quiz8", 0));
            a9.setText("" + sharedPreferences.getInt("Quiz9", 0));
            a10.setText("" + sharedPreferences.getInt("Quiz10", 0));
            a11.setText("" + sharedPreferences.getInt("Quiz11", 0));
            a12.setText("" + sharedPreferences.getInt("Quiz12", 0));
            a13.setText("" + sharedPreferences.getInt("Quiz13", 0));
            a14.setText("" + sharedPreferences.getInt("Quiz14", 0));
            a15.setText("" + sharedPreferences.getInt("Quiz15", 0));
            a16.setText("" + sharedPreferences.getInt("Quiz16", 0));
            a17.setText("" + sharedPreferences.getInt("Quiz17", 0));
            a18.setText("" + sharedPreferences.getInt("Quiz18", 0));
            a19.setText("" + sharedPreferences.getInt("Quiz19", 0));
            a20.setText("" + sharedPreferences.getInt("Quiz20", 0));
            a21.setText("" + sharedPreferences.getInt("Quiz21", 0));
            a22.setText("" + sharedPreferences.getInt("Quiz22", 0));
            a23.setText("" + sharedPreferences.getInt("Quiz23", 0));
            a24.setText("" + sharedPreferences.getInt("Quiz24", 0));
            a25.setText("" + sharedPreferences.getInt("Quiz25", 0));
            a26.setText("" + sharedPreferences.getInt("Quiz26", 0));
            a27.setText("" + sharedPreferences.getInt("Quiz27", 0));
            a28.setText("" + sharedPreferences.getInt("Quiz28", 0));
            a29.setText("" + sharedPreferences.getInt("Quiz29", 0));
            a30.setText("" + sharedPreferences.getInt("Quiz30", 0));
            a31.setText("" + sharedPreferences.getInt("Quiz31", 0));
            a32.setText("" + sharedPreferences.getInt("Quiz32", 0));
            a33.setText("" + sharedPreferences.getInt("Quiz33", 0));
            a34.setText("" + sharedPreferences.getInt("Quiz34", 0));
            a35.setText("" + sharedPreferences.getInt("Quiz35", 0));
            a36.setText("" + sharedPreferences.getInt("Quiz36", 0));
            a37.setText("" + sharedPreferences.getInt("Quiz37", 0));
            a38.setText("" + sharedPreferences.getInt("Quiz38", 0));
            a39.setText("" + sharedPreferences.getInt("Quiz39", 0));
            a40.setText("" + sharedPreferences.getInt("Quiz40", 0));
            a41.setText("" + sharedPreferences.getInt("Quiz41", 0));
            a42.setText("" + sharedPreferences.getInt("Quiz42", 0));
            a43.setText("" + sharedPreferences.getInt("Quiz43", 0));
            a44.setText("" + sharedPreferences.getInt("Quiz44", 0));
            a45.setText("" + sharedPreferences.getInt("Quiz45", 0));
            a46.setText("" + sharedPreferences.getInt("Quiz46", 0));
            a47.setText("" + sharedPreferences.getInt("Quiz47", 0));
            a48.setText("" + sharedPreferences.getInt("Quiz48", 0));
            a49.setText("" + sharedPreferences.getInt("Quiz49", 0));
            a50.setText("" + sharedPreferences.getInt("Quiz50", 0));
            a51.setText("" + sharedPreferences.getInt("Quiz51", 0));
            a52.setText("" + sharedPreferences.getInt("Quiz52", 0));
            a53.setText("" + sharedPreferences.getInt("Quiz53", 0));
            a54.setText("" + sharedPreferences.getInt("Quiz54", 0));
            a55.setText("" + sharedPreferences.getInt("Quiz55", 0));
            a56.setText("" + sharedPreferences.getInt("Quiz56", 0));
            a57.setText("" + sharedPreferences.getInt("Quiz57", 0));
            a58.setText("" + sharedPreferences.getInt("Quiz58", 0));
            a59.setText("" + sharedPreferences.getInt("Quiz59", 0));
            a60.setText("" + sharedPreferences.getInt("Quiz60", 0));
            a61.setText("" + sharedPreferences.getInt("Quiz61", 0));
            a62.setText("" + sharedPreferences.getInt("Quiz62", 0));
            a63.setText("" + sharedPreferences.getInt("Quiz63", 0));
            a64.setText("" + sharedPreferences.getInt("Quiz64", 0));
            a65.setText("" + sharedPreferences.getInt("Quiz65", 0));
            a66.setText("" + sharedPreferences.getInt("Quiz66", 0));
            a67.setText("" + sharedPreferences.getInt("Quiz67", 0));
            a68.setText("" + sharedPreferences.getInt("Quiz68", 0));
            a69.setText("" + sharedPreferences.getInt("Quiz69", 0));
            a70.setText("" + sharedPreferences.getInt("Quiz70", 0));
            a71.setText("" + sharedPreferences.getInt("Quiz71", 0));
            a72.setText("" + sharedPreferences.getInt("Quiz72", 0));
            a73.setText("" + sharedPreferences.getInt("Quiz73", 0));
            a74.setText("" + sharedPreferences.getInt("Quiz74", 0));
            a75.setText("" + sharedPreferences.getInt("Quiz75", 0));
            a76.setText("" + sharedPreferences.getInt("Quiz76", 0));
            a77.setText("" + sharedPreferences.getInt("Quiz77", 0));
            a78.setText("" + sharedPreferences.getInt("Quiz78", 0));
            a79.setText("" + sharedPreferences.getInt("Quiz79", 0));
            a80.setText("" + sharedPreferences.getInt("Quiz80", 0));
            a81.setText("" + sharedPreferences.getInt("Quiz81", 0));
            a82.setText("" + sharedPreferences.getInt("Quiz82", 0));
            a83.setText("" + sharedPreferences.getInt("Quiz83", 0));
            a84.setText("" + sharedPreferences.getInt("Quiz84", 0));
            a85.setText("" + sharedPreferences.getInt("Quiz85", 0));
            a86.setText("" + sharedPreferences.getInt("Quiz86", 0));
            a87.setText("" + sharedPreferences.getInt("Quiz87", 0));
            a88.setText("" + sharedPreferences.getInt("Quiz88", 0));
            a89.setText("" + sharedPreferences.getInt("Quiz89", 0));
            a90.setText("" + sharedPreferences.getInt("Quiz90", 0));
            a91.setText("" + sharedPreferences.getInt("Quiz91", 0));
            a92.setText("" + sharedPreferences.getInt("Quiz92", 0));
        } catch (Exception e) {
            Toast.makeText(ScoreCard.this, "" + e, Toast.LENGTH_LONG).show();
            qm.setText("" + e);
        }
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
            Intent intent = new Intent(ScoreCard.this, SettingsActivity.class);
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
        }else if (id == R.id.action_scorecard) {
            AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(ScoreCard.this);
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
                    Intent intent = new Intent(ScoreCard.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",1);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });

            ttBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ScoreCard.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",2);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });

            btttestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ScoreCard.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",3);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });
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
        }else if (item.getItemId() == android.R.id.home) {
            finish();
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
