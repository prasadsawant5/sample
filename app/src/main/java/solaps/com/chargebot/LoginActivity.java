package solaps.com.chargebot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import util.UtilClass;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();

    private ImageButton ibFacebook, ibTwitter, ibGoogle;
    private TextView tvConnect, tvOr, tvForgot;
    private EditText etEmailPhone, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvConnect = (TextView) findViewById(R.id.tv_connect_with);
        UtilClass.setCustomFont(getApplicationContext(), tvConnect, getString(R.string.font_lato_light));

        tvOr = (TextView) findViewById(R.id.tv_or);
        UtilClass.setCustomFont(getApplicationContext(), tvOr, getString(R.string.font_lato_light));

        etEmailPhone = (EditText) findViewById(R.id.et_email_phone);
        UtilClass.setCustomFont(getApplicationContext(), etEmailPhone, getString(R.string.font_lato_light));
        UtilClass.setTouchListner(etEmailPhone);


        etPassword = (EditText) findViewById(R.id.et_password);
        UtilClass.setCustomFont(getApplicationContext(), etPassword, getString(R.string.font_lato_light));
        UtilClass.setTouchListner(etPassword);

        btnLogin = (Button) findViewById(R.id.btn_login);
        UtilClass.setCustomFont(getApplicationContext(), btnLogin, getString(R.string.font_lato_light));

        tvForgot = (TextView) findViewById(R.id.tv_forgot_password);
        UtilClass.setCustomFont(getApplicationContext(), tvForgot, getString(R.string.font_lato_bold));

        ibFacebook = (ImageButton) findViewById(R.id.ib_facebook);
        ibTwitter = (ImageButton) findViewById(R.id.ib_twitter);
        ibGoogle = (ImageButton) findViewById(R.id.ib_google);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        ibFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
