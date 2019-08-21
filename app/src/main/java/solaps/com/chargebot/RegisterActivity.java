package solaps.com.chargebot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import constants.ApplicationConstants;
import services.RegisterUserAsyncTask;
import util.UtilClass;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getName();

    private Button btnRegister;
    private EditText etFullName, etEmail, etPassword, etPhone, etCountryCode;
    private String fullName, email, device, deviceId, password, phoneNumber;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = (EditText) findViewById(R.id.et_full_name);
        UtilClass.setCustomFont(getApplicationContext(), etFullName, getString(R.string.font_lato_light));

        etEmail = (EditText) findViewById(R.id.et_email);
        UtilClass.setCustomFont(getApplicationContext(), etEmail, getString(R.string.font_lato_light));

        etPassword = (EditText) findViewById(R.id.et_password);
        UtilClass.setCustomFont(getApplicationContext(), etPassword, getString(R.string.font_lato_light));

        etCountryCode = (EditText) findViewById(R.id.et_country_code);
        UtilClass.setCustomFont(getApplicationContext(), etPassword, getString(R.string.font_lato_light));

        etPhone = (EditText) findViewById(R.id.et_phone);
        UtilClass.setCustomFont(getApplicationContext(), etPhone, getString(R.string.font_lato_light));

        btnRegister = (Button) findViewById(R.id.btn_login);
        UtilClass.setCustomFont(getApplicationContext(), btnRegister, getString(R.string.font_lato_light));


        fullName = this.getIntent().getStringExtra(ApplicationConstants.EXTRA_FIRST_NAME) + " " + this.getIntent().getStringExtra(ApplicationConstants.EXTRA_LAST_NAME);
        email = this.getIntent().getStringExtra(ApplicationConstants.EXTRA_EMAIL);
        device = this.getIntent().getStringExtra(ApplicationConstants.EXTRA_MODEL);
        deviceId = this.getIntent().getStringExtra(ApplicationConstants.EXTRA_DEVICE_ID);

        if (fullName != null && !fullName.equals(""))
            etFullName.setText(fullName);

        if (email != null && !email.equals(""))
            etEmail.setText(email);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilClass.isConnected(getApplicationContext())) {

                    fullName = etFullName.getText().toString();
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    phoneNumber = etCountryCode.getText().toString() + etPhone.getText().toString();

                    if (fullName != null && !fullName.equals("") &&  email != null && !email.equals("") &&
                            password != null && !password.equals("") && phoneNumber != null && !phoneNumber.equals("")) {

                        if (device == null)
                            device = "";

                        if (deviceId == null)
                            deviceId = "";

                        double[] location = UtilClass.getLocation(getApplicationContext(), RegisterActivity.this);
                        latitude = location[0];
                        longitude = location[1];

                        etFullName.setEnabled(false);
                        etEmail.setEnabled(false);
                        etPassword.setEnabled(false);
                        etCountryCode.setEnabled(false);
                        etPhone.setEnabled(false);

                        btnRegister.setEnabled(false);
                        new RegisterUserAsyncTask(getApplicationContext(), RegisterActivity.this, latitude, longitude).execute(fullName, email, password, phoneNumber, device, deviceId);


                    } else {
                        //TODO: Show a Toast message (Form validation failed).
                    }

                } else {
                    //TODO: Show a Toast message (No internet connection).
                }
            }
        });


        UtilClass.setTouchListner(etFullName);
        UtilClass.setTouchListner(etEmail);
        UtilClass.setTouchListner(etPassword);
        UtilClass.setTouchListner(etCountryCode);
        UtilClass.setTouchListner(etPhone);
    }

}
