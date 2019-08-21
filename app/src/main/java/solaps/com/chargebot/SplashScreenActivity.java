package solaps.com.chargebot;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import constants.ApplicationConstants;
import services.CheckUserAsyncTask;
import services.WeatherInfoAsyncTask;
import storage.MySharedPreferences;
import util.UtilClass;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getName();
    private String email;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String state = intent.getStringExtra(ApplicationConstants.EXTRA_STATE_INITIALIZATION);

            Log.d(TAG, state);

            switch (state) {

                case ApplicationConstants.STATE_WELCOME:
                    Intent tutorialIntent = new Intent(getApplicationContext(), TutorialActivity.class);
                    tutorialIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(tutorialIntent);
                    finish();
                    break;

                case ApplicationConstants.STATE_HOME:
                    UtilClass.getLocation(getApplicationContext(), SplashScreenActivity.this);

                    if (UtilClass.isConnected(getApplicationContext()))
                        new WeatherInfoAsyncTask(getApplicationContext(), SplashScreenActivity.this).execute();
                    Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    finish();
                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (UtilClass.isPermissionRequired()) {

            if (UtilClass.checkPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                    UtilClass.checkPermission(getApplicationContext(), android.Manifest.permission.GET_ACCOUNTS) &&
                    UtilClass.checkPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) &&
                    UtilClass.checkPermission(getApplicationContext(), android.Manifest.permission.INTERNET) &&
                    UtilClass.checkPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) &&
                    UtilClass.checkPermission(getApplicationContext(), android.Manifest.permission.ACCESS_NETWORK_STATE) &&
                    UtilClass.checkPermission(getApplicationContext(), android.Manifest.permission.READ_SMS)) {

                proceedWithExecution();

            } else {

                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.GET_ACCOUNTS, Manifest.permission.INTERNET, Manifest.permission.READ_CONTACTS,
                                Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_SMS },
                        ApplicationConstants.MY_PERMISSIONS_CODE);

            }
        } else {
            proceedWithExecution();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ApplicationConstants.MY_PERMISSIONS_CODE) {

            if (grantResults[3] == 0) {
                if (UtilClass.isConnected(getApplicationContext()))
                    proceedWithExecution();
                else {
                    //TODO: show Toast message
                }
            }
        }
    }

    private void proceedWithExecution() {

        boolean isRegistered = MySharedPreferences.getPreferenceBoolean(getApplicationContext(),
                ApplicationConstants.PREFERENCE_IS_REGISTERED);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(ApplicationConstants.SPLASH_BROADCAST_RESPONSE));


        if (isRegistered) {
            Intent intent = new Intent(ApplicationConstants.SPLASH_BROADCAST_RESPONSE);
            intent.putExtra(ApplicationConstants.EXTRA_STATE_INITIALIZATION, ApplicationConstants.STATE_HOME);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } else {
            email = UtilClass.getEmailAddress(getApplicationContext(), SplashScreenActivity.this);

            if (email != null && !email.equals("") && UtilClass.isConnected(getApplicationContext())) {
                new CheckUserAsyncTask(getApplicationContext()).execute(email);
            } else {
                Intent intent = new Intent(ApplicationConstants.SPLASH_BROADCAST_RESPONSE);
                intent.putExtra(ApplicationConstants.EXTRA_STATE_INITIALIZATION, ApplicationConstants.STATE_WELCOME);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            }
        }
    }
}
