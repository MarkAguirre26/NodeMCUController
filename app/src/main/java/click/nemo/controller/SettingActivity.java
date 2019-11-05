package click.nemo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SettingActivity extends Activity {


    RelativeLayout settingLayout;
    EditText txtPassword, txtSSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        txtPassword = findViewById(R.id.txtPassword);
        txtSSID = findViewById(R.id.txtSSID);
        settingLayout = findViewById(R.id.settingLayout);

        // getting Integer
        String secretPin = preference.getDefaults("secretPin", getApplicationContext());
        String SSID = preference.getDefaults("SSID", getApplicationContext());
        txtPassword.setText(secretPin);
        txtSSID.setText(SSID);

        settingLayout.setOnTouchListener(new OnSwipeTouchListener(SettingActivity.this) {
            public void onSwipeTop() {
//                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                // Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();

            }

            public void onSwipeLeft() {
//                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();


                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

            public void onSwipeBottom() {
//                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }


    public void save_Clicked(View view) {

        String secretPin = txtPassword.getText().toString();
        String SSID = txtSSID.getText().toString();

        preference.setDefaults("secretPin", secretPin, getApplicationContext());
        preference.setDefaults("SSID", SSID, getApplicationContext());

        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();


    }
}
