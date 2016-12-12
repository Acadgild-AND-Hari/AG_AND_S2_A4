package com.hari.aag.hideandseek;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HideAndSeekActivity extends AppCompatActivity
        implements View.OnClickListener{

    private static final String LOG_TAG = HideAndSeekActivity.class.getSimpleName();
    private static final String PREFS_NAME = HideAndSeekActivity.class.getSimpleName();

    private boolean isHidden = false;

    private static final String HIDE_OR_SEEK = "hideOrSeek";

    @BindView(R.id.image) ImageView monkeyIV;
    @BindView(R.id.hideAndSeekBtn) Button hideAndSeekBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_and_seek);
        ButterKnife.bind(this);

        hideAndSeekBtn.setOnClickListener(this);

        Log.d(LOG_TAG, "Inside - onCreate");
        readValuesFromPrefs();
        updateValueToUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Inside - onPause");
        saveValuesToPrefs();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hideAndSeekBtn:
                isHidden = !isHidden;
                saveValuesToPrefs();
                updateValueToUI();
                break;
        }
    }

    private void updateValueToUI(){
        if (!isHidden) {
            monkeyIV.setVisibility(View.VISIBLE);
            hideAndSeekBtn.setText(R.string.btn_hide);
        } else {
            monkeyIV.setVisibility(View.INVISIBLE);
            hideAndSeekBtn.setText(R.string.btn_show);
        }
    }

    private void readValuesFromPrefs(){
        SharedPreferences mySharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        isHidden = mySharedPrefs.getBoolean(HIDE_OR_SEEK, false);

        Log.d(LOG_TAG, "Values Read from Prefs.");
        dumpPrefValues();
    }

    private void saveValuesToPrefs(){
        SharedPreferences.Editor prefsEditor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        prefsEditor.putBoolean(HIDE_OR_SEEK, isHidden);
        prefsEditor.commit();

        Log.d(LOG_TAG, "Values Saved to Prefs.");
        dumpPrefValues();
    }

    private void dumpPrefValues(){
        Log.d(LOG_TAG, HIDE_OR_SEEK + " - " + isHidden);
    }
}
