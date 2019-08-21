package fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import adapters.DeviceAdapter;
import constants.ApplicationConstants;
import services.WeatherInfoAsyncTask;
import solaps.com.chargebot.R;
import storage.MySharedPreferences;
import util.UtilClass;

/**
 * Created by prasadsawant on 11/16/16.
 */

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();

    private TextView tvToday, tvDay, tvSunrise, tvSunset, tvCity, tvSummary, tvNoInternet;
    private ImageView ivSunrise, ivSunset;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String sunrise, sunset;
    private RecyclerView rvDevices;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private FragmentTransaction fragmentTransaction;
    private String[] devices = new String[] { "Device # 1", "Device # 2", "Device # 3", "Device # 4", "Device # 5", "Device # 6", "Device # 7", "Device # 8" };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        tvToday = (TextView) rootView.findViewById(R.id.tv_today);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvToday, getString(R.string.font_lato_regular));

        tvDay = (TextView) rootView.findViewById(R.id.tv_day);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvDay, getString(R.string.font_lato_regular));

        tvSunrise = (TextView) rootView.findViewById(R.id.tv_sunrise);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvSunrise, getString(R.string.font_lato_regular));

        tvSunset = (TextView) rootView.findViewById(R.id.tv_sunset);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvSunset, getString(R.string.font_lato_regular));

        tvCity = (TextView) rootView.findViewById(R.id.tv_city);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvCity, getString(R.string.font_lato_regular));

        tvSummary = (TextView) rootView.findViewById(R.id.tv_summary);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvSummary, getString(R.string.font_lato_regular));

        tvNoInternet = (TextView) rootView.findViewById(R.id.tv_no_internet);

        fragmentTransaction = getFragmentManager().beginTransaction();


        rvDevices = (RecyclerView) rootView.findViewById(R.id.rv_devices);
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvDevices.setLayoutManager(linearLayoutManager);
        rvDevices.setAdapter(new DeviceAdapter(getResources().getStringArray(R.array.devices), getActivity().getApplicationContext(), fragmentTransaction));
//        dividerItemDecoration = new DividerItemDecoration(rvDevices.getContext(), linearLayoutManager.getOrientation());
//        dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.recycler_view_border));

        ivSunrise = (ImageView) rootView.findViewById(R.id.iv_sunrise);
        ivSunset = (ImageView) rootView.findViewById(R.id.iv_sunset);

        simpleDateFormat = new SimpleDateFormat(ApplicationConstants.DAY_FORMAT);
        date = new Date();
        String day = simpleDateFormat.format(date);

        if (day != null)
            tvDay.setText(day);

        sunrise = MySharedPreferences.getPreferenceString(getActivity().getApplicationContext(), ApplicationConstants.PREFERENCE_SUNRISE);
        sunset = MySharedPreferences.getPreferenceString(getActivity().getApplicationContext(), ApplicationConstants.PREFERENCE_SUNSET);



        if (UtilClass.isConnected(getActivity().getApplicationContext())) {
            if (tvNoInternet.getVisibility() != View.GONE)
                tvNoInternet.setVisibility(View.GONE);

            if (sunrise != null) {
                tvSunrise.setText(sunrise);
            } else {
                new WeatherInfoAsyncTask(getActivity().getApplicationContext(), getActivity(), tvSunrise, tvSunset).execute();
                sunrise = MySharedPreferences.getPreferenceString(getActivity().getApplicationContext(), ApplicationConstants.PREFERENCE_SUNRISE);
                sunset = MySharedPreferences.getPreferenceString(getActivity().getApplicationContext(), ApplicationConstants.PREFERENCE_SUNSET);
                tvSunrise.setText(sunrise);
                tvSunset.setText(sunset);

            }

            if (sunset != null) {
                tvSunset.setText(sunset);
            } else {
                if (MySharedPreferences.getPreferenceString(getActivity().getApplicationContext(), ApplicationConstants.PREFERENCE_SUNRISE) != null) {
                    new WeatherInfoAsyncTask(getActivity().getApplicationContext(), getActivity(), tvSunrise, tvSunset).execute();
                    sunrise = MySharedPreferences.getPreferenceString(getActivity().getApplicationContext(), ApplicationConstants.PREFERENCE_SUNRISE);
                    sunset = MySharedPreferences.getPreferenceString(getActivity().getApplicationContext(), ApplicationConstants.PREFERENCE_SUNSET);
                    tvSunrise.setText(sunrise);
                    tvSunset.setText(sunset);
                }
            }

        } else {
            ivSunrise.setVisibility(View.GONE);
            tvSunrise.setVisibility(View.GONE);

            ivSunset.setVisibility(View.GONE);
            tvSunset.setVisibility(View.GONE);

            tvNoInternet.setVisibility(View.VISIBLE);
            UtilClass.setCustomFont(getActivity().getApplicationContext(), tvNoInternet, getString(R.string.font_lato_hardline));
        }

        tvCity.setText(UtilClass.getCity(getActivity().getApplicationContext()));

        return rootView;
    }
}
