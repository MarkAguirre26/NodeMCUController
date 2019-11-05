package click.nemo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends Activity {

    private String TAG = this.getClass().getSimpleName();
    CoordinatorLayout coordinatorLayout;
    ImageButton logoButton, switch1, switch2;
    //    SwipeRefreshLayout pullToRefresh;
    //    ScrollView scrollView;
    int tapCount = 0;
    private String numberOfDispense = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        this.setTitle(R.string.app_name);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        logoButton = findViewById(R.id.logoButton);
//        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
//        scrollView = (ScrollView) findViewById(R.id.scrollView);

//        WifiUtils.withContext(getApplicationContext()).enableWifi(MainActivity.this::checkResult);

        // ConnectToNetwork();

//        logoButton.setOnTouchListener(this);

//        setPullToRefresh();

        // ----------SWITCH 1-----------------
        setLastState();


        switch2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    switch2.setTag(Constant.SWITCH_2_ON);
                    switch2.setImageResource(R.drawable.lightning_bolt_filled_active);

                    Log.d(TAG, "ACTION_BUTTON_PRESS");
                    final String url = AppUrl.SWITCH_URL + Constant.SWITCH_2_ON;
                    new MyHttpRequestTask().execute(url);


                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    switch2.setTag(Constant.SWITCH_2_OFF);
                    switch2.setImageResource(R.drawable.lightning_bolt_filled);
                    Log.d(TAG, "ACTION_BUTTON_RELEASE");
                    final String url = AppUrl.SWITCH_URL + Constant.SWITCH_2_OFF;
                    new MyHttpRequestTask().execute(url);
                }

                return true;
            }
        });
    }


    private void setLastState() {
        if (preference.getDefaults(Constant.SWITCH_1, getApplicationContext()).equals(Constant.SWITCH_1_ON)) {
            switch1.setTag(Constant.SWITCH_1_ON);
            switch1.setImageResource(R.drawable.on_off_green);
        } else {
            switch1.setTag(Constant.SWITCH_1_OFF);
            switch1.setImageResource(R.drawable.on_off_gray);
        }
        // ----------SWITCH 2-----------------
        if (preference.getDefaults(Constant.SWITCH_2, getApplicationContext()).equals(Constant.SWITCH_2_ON)) {
            switch2.setTag(Constant.SWITCH_2_ON);
            switch2.setImageResource(R.drawable.on_off_green);
        } else {
            switch2.setTag(Constant.SWITCH_2_OFF);
            switch2.setImageResource(R.drawable.on_off_gray);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    public void switch_Clicked(View view) {
        ImageButton btn = (ImageButton) view;
        toggleSwitch(btn);
    }

    private void setButton(ImageButton imgButton, boolean errExist) {

        String imgButtonId = getResources().getResourceEntryName(imgButton.getId());

        if (imgButtonId.equals(Constant.SWITCH_1)) {
            if (imgButton.getTag().equals(Constant.SWITCH_1_ON)) {
                imgButton.setTag(Constant.SWITCH_1_OFF);
                imgButton.setImageResource(R.drawable.on_off_gray);
                preference.setDefaults(Constant.SWITCH_1, Constant.SWITCH_1_OFF, getApplicationContext());
            } else {
                imgButton.setTag(Constant.SWITCH_1_ON);
                imgButton.setImageResource(R.drawable.on_off_green);
                preference.setDefaults(Constant.SWITCH_1, Constant.SWITCH_1_ON, getApplicationContext());

            }

            setDisbledButton(imgButton, R.drawable.on_off_gray, errExist);

        }


    }


    void setDisbledButton(ImageButton imageButton, int resource, boolean err) {
        if (err) {
            imageButton.setImageResource(resource);
        }
    }

    void toggleSwitch(final ImageButton imgButton) {

        setButton(imgButton, false);
        final String url = AppUrl.SWITCH_URL + imgButton.getTag();
        new MyHttpRequestTask().execute(url);


    }


    public void appName_Clicked(View view) {

        if (tapCount == 7) {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            finish();
        }
        tapCount++;
    }


}
