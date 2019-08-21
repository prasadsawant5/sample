package services;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import constants.ApplicationConstants;
import constants.ServerConstants;
import storage.MySharedPreferences;
import util.UtilClass;

/**
 * Created by prasadsawant on 11/14/16.
 */

public class WeatherInfoAsyncTask extends AsyncTask<Object, Integer, Integer> {

    private static final String TAG = WeatherInfoAsyncTask.class.getName();

    private Context context;
    private Activity activity;
    private String latitude, longitude, sunrise, sunset;
    private int responseCode = 0;
    private double[] coOrdinates;
    private TextView tvSunrise, tvSunset;

    public WeatherInfoAsyncTask(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public WeatherInfoAsyncTask(Context context, Activity activity, TextView tvSunrise, TextView tvSunset) {
        this.context = context;
        this.activity = activity;
        this.tvSunrise = tvSunrise;
        this.tvSunset = tvSunset;
    }

    @Override
    protected Integer doInBackground(Object[] params) {

        URL url;
        HttpURLConnection httpUrlConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();

        latitude = MySharedPreferences.getPreferenceString(context, ApplicationConstants.PREFERENCE_LATITUDE);
        longitude = MySharedPreferences.getPreferenceString(context, ApplicationConstants.PREFERENCE_LONGITUDE);


        if (latitude == null || latitude.equals("") || longitude == null || longitude.equals("")) {
            coOrdinates = UtilClass.getLocation(context, activity);

            latitude = String.valueOf(coOrdinates[0]);
            longitude = String.valueOf(coOrdinates[1]);

            if (!latitude.equals(MySharedPreferences.getPreferenceString(context, ApplicationConstants.PREFERENCE_LATITUDE))) {
                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_LATITUDE, latitude);
            }

            if (!longitude.equals(MySharedPreferences.getPreferenceString(context, ApplicationConstants.PREFERENCE_LONGITUDE))) {
                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_LONGITUDE, longitude);
            }
        }

        try {

            url = new URL(ServerConstants.SUNRISE_SUNSET_URL + ServerConstants.SUNRISE_SUNSET_LAT + latitude
                    + ServerConstants.SUNRISE_SUNSET_LON + longitude);

            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestMethod(ServerConstants.METHOD_GET);

            bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject obj = new JSONObject(sb.toString());

            Log.i(TAG, obj.toString());

            JSONObject results = obj.getJSONObject(ServerConstants.JSON_RESULTS);
            sunrise = results.getString(ServerConstants.JSON_SUNRISE);
            sunset = results.getString(ServerConstants.JSON_SUNSET);

            if (sunrise != null && !sunrise.equals("")) {
                int index = sunrise.lastIndexOf(ApplicationConstants.SEMICOLON_SEC);
                sunrise = sunrise.substring(0, index) + sunrise.substring(index + 3, sunrise.length());
            }

            if (sunset != null && !sunset.equals("")) {
                int index = sunset.lastIndexOf(ApplicationConstants.SEMICOLON_SEC);
                sunset = sunset.substring(0, index) + sunset.substring(index + 3, sunset.length());
            }

            responseCode = httpUrlConnection.getResponseCode();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            httpUrlConnection.disconnect();
        }


        return responseCode;
    }


    @Override
    protected void onPostExecute(Integer integer) {

        if (integer == HttpURLConnection.HTTP_OK) {
            MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_SUNRISE, sunrise);
            MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_SUNSET, sunset);

            if (tvSunrise != null)
                tvSunrise.setText(sunrise);

            if (tvSunset != null)
                tvSunset.setText(sunset);
        }

    }
}
