package fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import solaps.com.chargebot.R;
import util.UtilClass;

import static solaps.com.chargebot.R.layout.fragment_tutorial_one;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorialOneFragment extends Fragment {

    private TextView tv;


    public TutorialOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(fragment_tutorial_one, container, false);

        tv = (TextView) rootView.findViewById(R.id.tv);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tv, getString(R.string.font_lato_light));

        return rootView;
    }


}
