package fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import constants.ApplicationConstants;
import solaps.com.chargebot.R;
import util.UtilClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {
    private static final String TAG = DeviceFragment.class.getName();

    private TextView tvDeviceName;
    private ImageButton ibBack;
    private FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_device, container, false);

        tvDeviceName = (TextView) rootView.findViewById(R.id.tv_device_name);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvDeviceName, getString(R.string.font_lato_regular));

        ibBack = (ImageButton) rootView.findViewById(R.id.ib_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
//                fragmentTransaction.addToBackStack( ApplicationConstants.FRAGMENT_HOME);
//                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

}
