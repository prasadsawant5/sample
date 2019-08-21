package views;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import constants.ApplicationConstants;
import services.VerifyUserAsyncTask;
import solaps.com.chargebot.R;
import storage.MySharedPreferences;
import util.UtilClass;

/**
 * Created by prasadsawant on 11/11/16.
 */

public class CustomDialogBox extends Dialog implements View.OnClickListener {

    private Context context;
    private Button btnChange, btnDone;
    private TextView tvActivationCode;
    private EditText etActivationCode;
    private Activity activity;

    private static final String TAG = CustomDialogBox.class.getName();


    private BroadcastReceiver tokenReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ApplicationConstants.ACTION_TOKEN_RECEIVED)) {
                final String token = intent.getStringExtra(ApplicationConstants.EXTRA_TOKEN);

                if (token != null && !token.equals("")) {
                    etActivationCode.setText(token);
                    new VerifyUserAsyncTask(getContext().getApplicationContext(), activity).execute(MySharedPreferences.getPreferenceString(getContext(), ApplicationConstants.PREFERENCE_EMAIL), token);
                    dismiss();
                }
            }

        }
    };

    public CustomDialogBox(Context context, Activity activity) {
        super(context);
        this.context = context;
        this.activity = activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);

        btnChange = (Button) findViewById(R.id.btn_change);
        UtilClass.setCustomFont(context, btnChange, context.getString(R.string.font_lato_light));

        btnDone = (Button) findViewById(R.id.btn_done);
        UtilClass.setCustomFont(context, btnDone, context.getString(R.string.font_lato_light));

        tvActivationCode = (TextView) findViewById(R.id.tv_activation_code);
        UtilClass.setCustomFont(context, tvActivationCode, context.getString(R.string.font_lato_light));

        etActivationCode = (EditText) findViewById(R.id.et_activation_code);
        UtilClass.setCustomFont(context, etActivationCode, context.getString(R.string.font_lato_light));
        etActivationCode.setFocusable(false);

        etActivationCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                etActivationCode.setFocusableInTouchMode(true);
                return false;
            }
        });

        btnChange.setOnClickListener(this);
        btnDone.setOnClickListener(this);


        LocalBroadcastManager.getInstance(getContext()).registerReceiver(tokenReceiver, new IntentFilter(ApplicationConstants.ACTION_TOKEN_RECEIVED));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                break;

            case R.id.btn_done:
                break;
        }
        dismiss();

    }


    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(tokenReceiver);
    }


    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }
}
