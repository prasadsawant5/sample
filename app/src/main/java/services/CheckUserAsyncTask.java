package services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.SSLContext;
import constants.ApplicationConstants;
import constants.ServerConstants;
import solaps.com.chargebot.SplashScreenActivity;
import storage.MySharedPreferences;
import util.UtilClass;

/**
 * Created by prasadsawant on 11/4/16.
 */

public class CheckUserAsyncTask extends AsyncTask<String, Integer, Integer> {

    private static final String TAG = CheckUserAsyncTask.class.getName();
    private Context context;
    private String fullName, lastName, email, userName, password, facebookId, facebookOAuth, playerId, avatarUrl, contactNumber;
    private boolean facebookFlag;
    private int responseCode = 0;
    private BufferedReader bufferedReader;
    private SSLContext sslContext;

    public CheckUserAsyncTask(Context context) {
        this.context = context;
    }


    @Override
    protected Integer doInBackground(String... strings) {

        URL url;
        HttpURLConnection httpURLConnection = null;
        StringBuilder stringBuilder = new StringBuilder();

        email = strings[0];


        try {

            // sslContext = HttpManager.instanceOf().getSSLContext(context);


            url = new URL(ServerConstants.SERVER_URL + ServerConstants.REGISTER_URL);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            // httpURLConnection.setHostnameVerifier(new NullHostNameVerifier());
            // httpURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            httpURLConnection.setRequestMethod(ServerConstants.METHOD_GET);
            httpURLConnection.setRequestProperty(ServerConstants.HEADER_EMAIL, email);
            httpURLConnection.setDoInput(true);


            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            responseCode = httpURLConnection.getResponseCode();

            Log.i(TAG, String.valueOf(responseCode));

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    stringBuilder.append(line);

                Log.d(TAG, stringBuilder.toString());

                JSONObject jsonObject = new JSONObject(stringBuilder.toString());

                fullName = jsonObject.getString(ServerConstants.JSON_FULL_NAME);
                password = jsonObject.getString(ServerConstants.JSON_PASSWORD);
                contactNumber = jsonObject.getString(ServerConstants.JSON_CONTACT_NO);
//                playerId = jsonObject.getString(ServerConstants.JSON_PLAYER_ID);
//                avatarUrl = jsonObject.getString(ServerConstants.JSON_AVATAR_URL);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.getCause();
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            httpURLConnection.disconnect();

        }

        return responseCode;
    }


    @Override
    protected void onPostExecute(Integer integer) {

        if (integer == HttpURLConnection.HTTP_OK) {
            Log.i(TAG, "User found!");

            MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_FULL_NAME, fullName);
            MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_EMAIL, email);
            MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_PASSWORD, password);
            MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_CONTACT_NO, contactNumber);
            MySharedPreferences.setPreferenceBoolean(context, ApplicationConstants.PREFERENCE_IS_REGISTERED, true);
//            MySharedPreferences.setPreferenceBoolean(context, ApplicationConstants.PREFERENCE_FACEBOOK_FLAG, facebookFlag);
//            MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_PLAYER_ID, playerId);
//            MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_AVATAR_URL, avatarUrl);


//            if (facebookFlag) {
//                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_FACEBOOK_ID, facebookId);
//                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_FACEBOOK_OAUTH, facebookOAuth);
//            }


            Intent intent = new Intent(ApplicationConstants.SPLASH_BROADCAST_RESPONSE);
            intent.putExtra(ApplicationConstants.EXTRA_STATE_INITIALIZATION, ApplicationConstants.STATE_HOME);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        } else {
            Intent intent = new Intent(ApplicationConstants.SPLASH_BROADCAST_RESPONSE);
            intent.putExtra(ApplicationConstants.EXTRA_STATE_INITIALIZATION, ApplicationConstants.STATE_WELCOME);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }
}
