package services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import constants.ApplicationConstants;
import constants.ServerConstants;
import solaps.com.chargebot.HomeActivity;
import solaps.com.chargebot.R;
import storage.MySharedPreferences;
import util.UtilClass;
import views.CustomDialogBox;

/**
 * Created by prasadsawant on 11/9/16.
 */

public class RegisterUserAsyncTask extends AsyncTask<String, Integer, Integer> {

    private static final String TAG = RegisterUserAsyncTask.class.getName();

    private Context context;
    private String fullName, email, password, phoneNumber, playerId = "", device;
    private Activity activity;
    private int responseCode = 0;
    private double latitude, longitude;


    public RegisterUserAsyncTask(Context context, Activity activity, double latitude, double longitude) {
        this.context = context;
        this.activity = activity;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    protected Integer doInBackground(String... strings) {

        fullName = strings[0];
        email = strings[1];
        password = strings[2];
        phoneNumber = strings[3];
        device = strings[4];

        URL url;
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            url = new URL(ServerConstants.SERVER_URL + ServerConstants.REGISTER_URL);
            Log.i(TAG, ServerConstants.SERVER_URL + ServerConstants.REGISTER_URL);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(ServerConstants.METHOD_POST);
            httpURLConnection.setRequestProperty(ServerConstants.HEADER_CONTENT_TYPE, ServerConstants.HEADER_APPLICATION_JSON);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(ServerConstants.JSON_FULL_NAME, fullName);
            jsonObject.put(ServerConstants.JSON_EMAIL, email);
            jsonObject.put(ServerConstants.JSON_PASSWORD, password);
            jsonObject.put(ServerConstants.JSON_MODEL, device);
            jsonObject.put(ServerConstants.JSON_CONTACT_NO, phoneNumber);
            jsonObject.put(ServerConstants.JSON_OS, context.getString(R.string.os));
            jsonObject.put(ServerConstants.JSON_LONGITUDE, longitude);
            jsonObject.put(ServerConstants.JSON_LATITUDE, latitude);


            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes("UTF-8"));

            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);

            responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_FULL_NAME, fullName);
                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_EMAIL, email);
                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_PASSWORD, password);
                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_CONTACT_NO, phoneNumber);
                MySharedPreferences.setPreferenceBoolean(context, ApplicationConstants.PREFERENCE_IS_REGISTERED, true);
                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_LATITUDE, String.valueOf(latitude));
                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_LONGITUDE, String.valueOf(longitude));
            }




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();

                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            httpURLConnection.disconnect();
        }

        return responseCode;
    }


    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        UtilClass.closeLocationUpdate(context, activity);

        if (integer == HttpURLConnection.HTTP_CREATED) {
            CustomDialogBox customDialogBox = new CustomDialogBox(activity, activity);
            customDialogBox.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_shape));
            customDialogBox.show();

        } else {

        }
    }
}
