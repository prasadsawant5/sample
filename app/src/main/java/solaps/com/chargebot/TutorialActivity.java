package solaps.com.chargebot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import adapters.ScreenSliderPagerAdapter;
import util.UtilClass;
import views.CustomDialogBox;

public class TutorialActivity extends AppCompatActivity {


    private Button btnGetStarted;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private ImageView ivDotOne, ivDotTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        ivDotOne.setImageDrawable(getResources().getDrawable(R.drawable.dot_selected_shape));
                        ivDotTwo.setImageDrawable(getResources().getDrawable(R.drawable.dot_unselected_shape));
                        break;

                    case 1:
                        ivDotTwo.setImageDrawable(getResources().getDrawable(R.drawable.dot_selected_shape));
                        ivDotOne.setImageDrawable(getResources().getDrawable(R.drawable.dot_unselected_shape));
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnGetStarted = (Button) findViewById(R.id.btn_get_started);
        UtilClass.setCustomFont(getApplicationContext(), btnGetStarted, getString(R.string.font_lato_light));


        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent welcomeIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(welcomeIntent);
            }
        });

        ivDotOne = (ImageView) findViewById(R.id.iv_dot_one);
        ivDotTwo = (ImageView) findViewById(R.id.iv_dot_two);
    }
}
