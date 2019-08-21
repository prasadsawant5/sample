package fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import solaps.com.chargebot.R;
import util.UtilClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorialTwoFragment extends Fragment {

    private TextView tv;


    public TutorialTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_tutorial_two, container, false);

        tv = (TextView) rootView.findViewById(R.id.tv);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tv, getString(R.string.font_lato_light));

        return rootView;
    }

}
