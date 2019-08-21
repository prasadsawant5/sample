package services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import constants.ApplicationConstants;
import constants.ServerConstants;
import solaps.com.chargebot.HomeActivity;
import storage.MySharedPreferences;

/**
 * Created by prasadsawant on 12/2/16.
 */

public class VerifyUserAsyncTask extends AsyncTask<String, Integer, Integer> {

    private Context context;
    private String email, token;
    private int responseCode = 0;
    private Activity activity;

    private static final String TAG = VerifyUserAsyncTask.class.getName();

    public VerifyUserAsyncTask(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }


    @Override
    protected Integer doInBackground(String... strings) {
        email = strings[0];
        token = strings[1];

        HttpURLConnection httpURLConnection = null;
        URL url;
        OutputStream outputStream = null;

        try {
            url = new URL(ServerConstants.SERVER_URL + ServerConstants.ACTIVATE_URL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(ServerConstants.METHOD_PUT);
            httpURLConnection.addRequestProperty(ServerConstants.HEADER_CONTENT_TYPE, ServerConstants.HEADER_APPLICATION_JSON);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(ServerConstants.JSON_EMAIL, email);
            jsonObject.put(ServerConstants.JSON_TOKEN, token);

            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes("UTF-8"));

            responseCode = httpURLConnection.getResponseCode();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
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
        if (integer == HttpURLConnection.HTTP_CREATED) {
            MySharedPreferences.setPreferenceBoolean(context, ApplicationConstants.PREFERENCE_IS_VERIFIED, true);
        } else {

        }

        activity.finish();

        Intent homeIntent = new Intent(activity, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(homeIntent);
    }
}
