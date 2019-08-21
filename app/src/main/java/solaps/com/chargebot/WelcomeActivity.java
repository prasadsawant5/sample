package solaps.com.chargebot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import constants.ApplicationConstants;
import util.UtilClass;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogin, btnRegister;
    private HashMap<String, String> userInfo;

    private static final String TAG = WelcomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tvWelcome = (TextView) findViewById(R.id.tv_welcome);
        UtilClass.setCustomFont(getApplicationContext(), tvWelcome, getResources().getString(R.string.font_lato_light));

        btnLogin = (Button) findViewById(R.id.btn_login);
        UtilClass.setCustomFont(getApplicationContext(), btnLogin, getResources().getString(R.string.font_lato_light));

        btnRegister = (Button) findViewById(R.id.btn_register);
        UtilClass.setCustomFont(getApplicationContext(), btnRegister, getResources().getString(R.string.font_lato_light));


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);

            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userInfo = UtilClass.getUserInformation(getApplicationContext(), WelcomeActivity.this);
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                registerIntent.putExtra(ApplicationConstants.EXTRA_FIRST_NAME, userInfo.get(ApplicationConstants.HASH_FIRSTNAME));
                registerIntent.putExtra(ApplicationConstants.EXTRA_LAST_NAME, userInfo.get(ApplicationConstants.HASH_LASTNAME));
                registerIntent.putExtra(ApplicationConstants.EXTRA_EMAIL, userInfo.get(ApplicationConstants.HASH_EMAIL));
                registerIntent.putExtra(ApplicationConstants.EXTRA_MODEL, userInfo.get(ApplicationConstants.HASH_DEVICE));
                registerIntent.putExtra(ApplicationConstants.EXTRA_DEVICE_ID, userInfo.get(ApplicationConstants.HASH_DEVICE_ID));
                startActivity(registerIntent);

            }
        });
    }

}
