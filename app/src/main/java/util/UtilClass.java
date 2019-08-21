package util;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import constants.ApplicationConstants;
import solaps.com.chargebot.R;
import storage.MySharedPreferences;

/**
 * Created by prasadsawant on 11/4/16.
 */

public class UtilClass {

    private static LocationManager locationManager;
    private static LocationListener locationListener;
    private static Criteria criteria;
    private static String provider;
    private static double[] coOrdinates;
    private static final String TAG = UtilClass.class.getName();


    /**
     * @description generic method for showing Toast messages
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        View view = toast.getView();
        int color = ContextCompat.getColor(context, R.color.colorAccent);
        view.setBackgroundColor(color);
        toast.show();

    }

    /**
     * @description checks if Internet is ON or not
     * @param context
     * @return boolean (determining the state of the internet)
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null)
                if (info.getState() == NetworkInfo.State.CONNECTED || info.getState() == NetworkInfo.State.CONNECTING) {
                    return true;
                }
        }
        return false;

    }


    /**
     * @description checks if bluetooth is ON or not
     * @return boolean (determininng the state of bluetooth)
     */
    public static boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            return false;
        } else {
            if (!bluetoothAdapter.isEnabled())
                return false;
            else
                return true;
        }
    }


    public static HashMap getUserInformation(Context context, Activity activity) {

        String email = "", firstName = "", lastName = "", model = "";
        HashMap<String, String> userInfo = new HashMap<>();

        final String nameColumn = "display_name_alt";

        email = MySharedPreferences.getPreferenceString(context, ApplicationConstants.PREFERENCE_EMAIL);
        model = Build.MANUFACTURER + " " + Build.MODEL;

        if (email == null || email.equals(""))
            email = getEmailAddress(context, activity);

        try {
            Cursor cursor = context.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null,
                    null, null, null);

            int count = cursor.getCount();
            cursor.moveToFirst();
            int position = cursor.getPosition();

            if (count == 1 && position == 0) {
                String fullName = cursor.getString(cursor.getColumnIndex(nameColumn));

                String[] fullNameArray;

                if (fullName != null) {
                    fullNameArray = fullName.split(", ");
                    firstName = fullNameArray[1];
                    lastName = fullNameArray[0];
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        userInfo.put(ApplicationConstants.HASH_FIRSTNAME, firstName);
        userInfo.put(ApplicationConstants.HASH_LASTNAME, lastName);
        userInfo.put(ApplicationConstants.HASH_EMAIL, email);
        userInfo.put(ApplicationConstants.HASH_DEVICE, model);

        return userInfo;

    }


    public static double[] getLocation(final Context context, Activity activity) {

        coOrdinates = new double[2];

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        provider = locationManager.getBestProvider(criteria, true);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(TAG, location.getProvider());

                coOrdinates[0] = location.getLatitude();
                coOrdinates[1] = location.getLongitude();

                if (!MySharedPreferences.getPreferenceString(context, ApplicationConstants.PREFERENCE_LATITUDE).equals(String.valueOf(coOrdinates[0])) ||
                        !MySharedPreferences.getPreferenceString(context, ApplicationConstants.PREFERENCE_LONGITUDE).equals(String.valueOf(coOrdinates[1]))) {

                    Log.i(TAG, "Updating co-ordinates");

                    MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_LATITUDE, String.valueOf(coOrdinates[0]));
                    MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_LONGITUDE, String.valueOf(coOrdinates[1]));

                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, ApplicationConstants.MY_PERMISSIONS_CODE);

            coOrdinates[0] = 0.0;
            coOrdinates[1] = 0.0;

        }

        try {
            locationManager.requestLocationUpdates(provider, 0, ApplicationConstants.MIN_DISTANCE, locationListener);
        } catch (Exception e) {
            e.printStackTrace();
            coOrdinates[0] = 0.0;
            coOrdinates[1] = 0.0;
        }

        if (coOrdinates[0] == 0.0 && coOrdinates[1] == 0.0) {
            Log.i(TAG, "Getting last known location");

            Location mLocation = null;

            try {
                mLocation = locationManager.getLastKnownLocation(provider);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (mLocation != null) {
                coOrdinates[0] = mLocation.getLatitude();
                coOrdinates[1] = mLocation.getLongitude();

                Log.i(TAG, "Updating co-ordinates");

                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_LATITUDE, String.valueOf(coOrdinates[0]));
                MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_LONGITUDE, String.valueOf(coOrdinates[1]));
            }
        }


        return coOrdinates;

    }


    public static void closeLocationUpdate(Context context, Activity activity) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return;

            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, ApplicationConstants.MY_PERMISSIONS_CODE);
        }
        locationManager.removeUpdates(locationListener);
    }


    /**
     * @description gets the user's GMail Address
     * @param context
     * @return String (user's GMail Address)
     */
    public static String getEmailAddress(Context context, Activity activity) {

        Account emailAddress = null;
        String email = "";

        try {
            AccountManager accountManager = AccountManager.get(context);

            if (isPermissionRequired()) {

                if (!checkPermission(context, Manifest.permission.GET_ACCOUNTS)) {
                    ActivityCompat.requestPermissions(activity, new String[]{ Manifest.permission.GET_ACCOUNTS }, ApplicationConstants.MY_PERMISSIONS_CODE);
                }
            }

            Account[] accounts = accountManager.getAccountsByType("com.google");

            if (accounts.length > 0) {
                emailAddress = accounts[0];
                email = emailAddress.name;
            } else {
                email = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return email;
    }


    /**
     * @description checks the Android build version, used for determining permission requests
     * @return boolean
     */
    public static boolean isPermissionRequired() {

        if (Build.VERSION.SDK_INT > 22)
            return true;
        else
            return false;
    }

    /**
     * @description checks if a certain permission is granted or not
     * @param context
     * @param permission
     * @return boolean
     */
    public static boolean checkPermission (Context context, String permission) {

        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }


    public static String getCity (Context context) {
        String city = "";

        if (coOrdinates != null) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(MySharedPreferences.getPreferenceString(context, ApplicationConstants.PREFERENCE_LATITUDE)),
                        Double.parseDouble(MySharedPreferences.getPreferenceString(context, ApplicationConstants.PREFERENCE_LONGITUDE)), 1);

                if (addressList.size() > 0)
                    return addressList.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return city;
    }


    public static void setCustomFont (Context context, TextView tv, String fontName) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
        tv.setTypeface(typeface);
    }

    public static void setCustomFont (Context context, Button btn, String fontName) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
        btn.setTypeface(typeface);
    }

    public static void setCustomFont (Context context, EditText et, String fontName) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
        et.setTypeface(typeface);
    }

    public static void setTouchListner (TextView tv) {
        final TextView textView = tv;
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                textView.setFocusableInTouchMode(true);
                return false;
            }
        });
    }
}
