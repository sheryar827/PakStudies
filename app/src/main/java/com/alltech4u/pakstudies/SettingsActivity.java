package com.alltech4u.pakstudies;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.MenuItem;
import android.widget.Toast;

public class SettingsActivity extends AppCompatPreferenceActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();
    public static SharedPreferences sharedPreferences;
    public static final String
            KEY_SET_BTT_QUIZ_TIMER = "key_set_btt_quiz_timer";
    public static final String
            KEY_PREF_EXAMPLE_SWITCH = "key_set_quiz_timer";
    public static final String
            KEY_QUESTIONS_ANSWER_SOUNDS ="key_questions_answer_sounds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            // gallery EditText change listener
            bindPreferenceSummaryToValue(findPreference(getString(R.string.key_set_quiz_timer)));

            // gallery EditText change listener
            bindPreferenceSummaryToValue(findPreference(getString(R.string.key_set_btt_quiz_timer)));

            sharedPreferences = getActivity().getSharedPreferences("Score", Context.MODE_PRIVATE);
            final SharedPreferences notimemode = getActivity().getSharedPreferences("one",0);
            final SharedPreferences.Editor notimemodeeditor = notimemode.edit();
            final SharedPreferences timemode = getActivity().getSharedPreferences("two",0);
            final SharedPreferences.Editor timemodeeditor = timemode.edit();
            final SharedPreferences bttmode = getActivity().getSharedPreferences("three",0);
            final SharedPreferences.Editor bttmodeeditor = bttmode.edit();
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            SwitchPreference on_off_switch = (SwitchPreference) getPreferenceManager().
                    findPreference(getString(R.string.key_questions_answer_sounds));

            if (sharedPreferences.getInt("Sound", 0) == 0) {
                on_off_switch.setChecked(true);
            }else {
                on_off_switch.setChecked(false);
            }

            // Questions answer sounds preference click listener
            final Preference myPref = findPreference(getString(R.string.key_questions_answer_sounds));
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    if(preference.getSharedPreferences()
                            .getBoolean(getString(R.string.key_questions_answer_sounds),true)) {
                        editor.putInt("Sound", 0).apply();
                    }else {
                        editor.putInt("Sound", 1).apply();
                    }
                    return true;
                }
            });

            final Preference reset_high_score_Pref = findPreference(getString(R.string.key_reset_high_score));
            reset_high_score_Pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    notimemodeeditor.putInt("Quiz1", 0);
                    notimemodeeditor.putInt("Quiz2", 0);
                    notimemodeeditor.putInt("Quiz3", 0);
                    notimemodeeditor.putInt("Quiz4", 0);
                    notimemodeeditor.putInt("Quiz5", 0);
                    notimemodeeditor.putInt("Quiz6", 0);
                    notimemodeeditor.putInt("Quiz7", 0);
                    notimemodeeditor.putInt("Quiz8", 0);
                    notimemodeeditor.putInt("Quiz9", 0);
                    notimemodeeditor.putInt("Quiz10", 0);
                    notimemodeeditor.putInt("Quiz11", 0);
                    notimemodeeditor.putInt("Quiz12", 0);
                    notimemodeeditor.putInt("Quiz13", 0);
                    notimemodeeditor.putInt("Quiz14", 0);
                    notimemodeeditor.putInt("Quiz15", 0);
                    notimemodeeditor.putInt("Quiz16", 0);
                    notimemodeeditor.putInt("Quiz17", 0);
                    notimemodeeditor.putInt("Quiz18", 0);
                    notimemodeeditor.putInt("Quiz19", 0);
                    notimemodeeditor.putInt("Quiz20", 0);
                    notimemodeeditor.putInt("Quiz21", 0);
                    notimemodeeditor.putInt("Quiz22", 0);
                    notimemodeeditor.putInt("Quiz23", 0);
                    notimemodeeditor.putInt("Quiz24", 0);
                    notimemodeeditor.putInt("Quiz25", 0);
                    notimemodeeditor.putInt("Quiz26", 0);
                    notimemodeeditor.putInt("Quiz27", 0);
                    notimemodeeditor.putInt("Quiz28", 0);
                    notimemodeeditor.putInt("Quiz29", 0);
                    notimemodeeditor.putInt("Quiz30", 0);
                    timemodeeditor.putInt("Quiz1", 0);
                    timemodeeditor.putInt("Quiz2", 0);
                    timemodeeditor.putInt("Quiz3", 0);
                    timemodeeditor.putInt("Quiz4", 0);
                    timemodeeditor.putInt("Quiz5", 0);
                    timemodeeditor.putInt("Quiz6", 0);
                    timemodeeditor.putInt("Quiz7", 0);
                    timemodeeditor.putInt("Quiz8", 0);
                    timemodeeditor.putInt("Quiz9", 0);
                    timemodeeditor.putInt("Quiz10", 0);
                    timemodeeditor.putInt("Quiz11", 0);
                    timemodeeditor.putInt("Quiz12", 0);
                    timemodeeditor.putInt("Quiz13", 0);
                    timemodeeditor.putInt("Quiz14", 0);
                    timemodeeditor.putInt("Quiz15", 0);
                    timemodeeditor.putInt("Quiz16", 0);
                    timemodeeditor.putInt("Quiz17", 0);
                    timemodeeditor.putInt("Quiz18", 0);
                    timemodeeditor.putInt("Quiz19", 0);
                    timemodeeditor.putInt("Quiz20", 0);
                    timemodeeditor.putInt("Quiz21", 0);
                    timemodeeditor.putInt("Quiz22", 0);
                    timemodeeditor.putInt("Quiz23", 0);
                    timemodeeditor.putInt("Quiz24", 0);
                    timemodeeditor.putInt("Quiz25", 0);
                    timemodeeditor.putInt("Quiz26", 0);
                    timemodeeditor.putInt("Quiz27", 0);
                    timemodeeditor.putInt("Quiz28", 0);
                    timemodeeditor.putInt("Quiz29", 0);
                    timemodeeditor.putInt("Quiz30", 0);
                    bttmodeeditor.putInt("Quiz1", 0);
                    bttmodeeditor.putInt("Quiz2", 0);
                    bttmodeeditor.putInt("Quiz3", 0);
                    bttmodeeditor.putInt("Quiz4", 0);
                    bttmodeeditor.putInt("Quiz5", 0);
                    bttmodeeditor.putInt("Quiz6", 0);
                    bttmodeeditor.putInt("Quiz7", 0);
                    bttmodeeditor.putInt("Quiz8", 0);
                    bttmodeeditor.putInt("Quiz9", 0);
                    bttmodeeditor.putInt("Quiz10", 0);
                    bttmodeeditor.putInt("Quiz11", 0);
                    bttmodeeditor.putInt("Quiz12", 0);
                    bttmodeeditor.putInt("Quiz13", 0);
                    bttmodeeditor.putInt("Quiz14", 0);
                    bttmodeeditor.putInt("Quiz15", 0);
                    bttmodeeditor.putInt("Quiz16", 0);
                    bttmodeeditor.putInt("Quiz17", 0);
                    bttmodeeditor.putInt("Quiz18", 0);
                    bttmodeeditor.putInt("Quiz19", 0);
                    bttmodeeditor.putInt("Quiz20", 0);
                    bttmodeeditor.putInt("Quiz21", 0);
                    bttmodeeditor.putInt("Quiz22", 0);
                    bttmodeeditor.putInt("Quiz23", 0);
                    bttmodeeditor.putInt("Quiz24", 0);
                    bttmodeeditor.putInt("Quiz25", 0);
                    bttmodeeditor.putInt("Quiz26", 0);
                    bttmodeeditor.putInt("Quiz27", 0);
                    bttmodeeditor.putInt("Quiz28", 0);
                    bttmodeeditor.putInt("Quiz29", 0);
                    bttmodeeditor.putInt("Quiz30", 0);
                    notimemodeeditor.apply();
                    timemodeeditor.apply();
                    bttmodeeditor.apply();
                    Toast.makeText(getActivity().getApplicationContext(),"Highscore Reseted Successfully", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

        }
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            //preference.setSummary(stringValue + " Seconds.");
            if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("key_set_quiz_timer")) {
                    // update the changed gallery name to summary filed
                    preference.setSummary(stringValue+" Seconds");
                }else if (preference.getKey().equals("key_set_btt_quiz_timer")){
                    // update the changed gallery name to summary filed
                    preference.setSummary(stringValue+" Seconds");
                }
            } else {
                preference.setSummary(stringValue+" Seconds");
            }
            return true;
        }
    };

}
