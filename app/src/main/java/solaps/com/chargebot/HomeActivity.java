package solaps.com.chargebot;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import constants.ApplicationConstants;
import fragments.HomeFragment;
import fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();

    private FragmentTransaction fragmentTransaction;
    private ImageButton ibHome, ibAdd, ibProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
        fragmentTransaction.addToBackStack(ApplicationConstants.FRAGMENT_HOME);
        fragmentTransaction.commit();

        ibHome = (ImageButton) findViewById(R.id.ib_home);
        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = HomeActivity.this.getFragmentManager().getBackStackEntryCount() - 1;
                FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                String tag = backEntry.getName();

                if (!tag.equals(ApplicationConstants.FRAGMENT_HOME)) {
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
                    fragmentTransaction.addToBackStack(ApplicationConstants.FRAGMENT_HOME);
                    fragmentTransaction.commit();
                }
            }
        });

        ibAdd = (ImageButton) findViewById(R.id.ib_add);
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ibProfile = (ImageButton) findViewById(R.id.ib_profile);
        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = HomeActivity.this.getFragmentManager().getBackStackEntryCount() - 1;
                FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                String tag = backEntry.getName();

                if (!tag.equals(ApplicationConstants.FRAGMENT_PROFILE)) {
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, new ProfileFragment());
                    fragmentTransaction.addToBackStack(ApplicationConstants.FRAGMENT_PROFILE);
                    fragmentTransaction.commit();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}
