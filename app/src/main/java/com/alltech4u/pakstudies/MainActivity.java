package com.alltech4u.pakstudies;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Contact> contactList;
    private ContactsAdapter mAdapter;
    private SearchView searchView;
    private AdView adView;
    int quiz_mode=0;

    // url to fetch contacts json
    //private static final String URL = "https://api.androidhive.info/json/contacts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=this.getIntent();
        quiz_mode=intent.getExtras().getInt("Quiz_Mode");

        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, ActivityConfig.FB_BANNER, AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        contactList = SpaceCraftsCollection.getSpaceCrafts();
        mAdapter = new ContactsAdapter(this, contactList, this);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
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
        if (id == R.id.action_search) {
            return true;
        }//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_share) {
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
            AlertDialog.Builder testmodeDialog = new AlertDialog.Builder(MainActivity.this);
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
                    Intent intent = new Intent(MainActivity.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",1);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });

            ttBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ScoreCard.class);
                    intent.putExtra("quiz_mode",2);
                    startActivity(intent);
                    modeDialog.cancel();
                }
            });

            btttestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ScoreCard.class);
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
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(final Contact contact) {

        if(quiz_mode==1) {

                Toast.makeText(getApplicationContext(), "Selected: " + contact.getName(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), NoTimeModeQuizActivity.class);
                //PACK DATA TO SEND
                i.putExtra("PAGE_KEY", contact.getPage());

                //open activity
                this.startActivity(i);
            } else if(quiz_mode==2) {
                Toast.makeText(getApplicationContext(), "Selected: " + contact.getName(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), TimeModeQuizActivity.class);
                //PACK DATA TO SEND
                i.putExtra("PAGE_KEY", contact.getPage());

                //open activity
                this.startActivity(i);
        }else if(quiz_mode==3) {
                Toast.makeText(getApplicationContext(), "Selected: " + contact.getName(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), BTTModeQuizActivity.class);
                //PACK DATA TO SEND
                i.putExtra("PAGE_KEY", contact.getPage());

                //open activity
                this.startActivity(i);
        }else if(quiz_mode==4) {
                Toast.makeText(getApplicationContext(), "Selected: " + contact.getName(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), PrepModeQuizActivity.class);
                //PACK DATA TO SEND
                i.putExtra("PAGE_KEY", contact.getPage());

                //open activity
                this.startActivity(i);
        }


    }
}
