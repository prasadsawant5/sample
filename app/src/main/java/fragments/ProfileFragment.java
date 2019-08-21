package fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;

import constants.ApplicationConstants;
import solaps.com.chargebot.HomeActivity;
import solaps.com.chargebot.R;
import storage.MySharedPreferences;
import util.UtilClass;

/**
 * Created by prasadsawant on 11/16/16.
 */

public class ProfileFragment extends Fragment {

    private TextView tvProfile, tvName, tvXp, tvLevel;
    private ImageButton ibBack, ibProfilePicture;
    private FragmentTransaction fragmentTransaction;
    private String avatarPath;
    private File avatar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        tvProfile = (TextView) rootView.findViewById(R.id.tv_profile);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvProfile, getString(R.string.font_lato_regular));

        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvName, getString(R.string.font_lato_regular));

        tvXp = (TextView) rootView.findViewById(R.id.tv_xp);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvXp, getString(R.string.font_lato_regular));

        tvLevel = (TextView) rootView.findViewById(R.id.tv_level);
        UtilClass.setCustomFont(getActivity().getApplicationContext(), tvLevel, getString(R.string.font_lato_regular));

        tvName.setText(MySharedPreferences.getPreferenceString(getActivity().getApplicationContext(), ApplicationConstants.PREFERENCE_FULL_NAME));


        ibBack = (ImageButton) rootView.findViewById(R.id.ib_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
                fragmentTransaction.addToBackStack(ApplicationConstants.FRAGMENT_HOME);
                fragmentTransaction.commit();
            }
        });


        ibProfilePicture = (ImageButton) rootView.findViewById(R.id.ib_profile_picture);
        ibProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilClass.isPermissionRequired()) {

                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, ApplicationConstants.MY_PERMISSIONS_CODE);
                    } else {
                        getProfilePictureIntent();
                    }

                } else {
                    getProfilePictureIntent();
                }
            }
        });

        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ApplicationConstants.SELECT_IMAGE) {
            Uri selectedImageUri = data.getData();

            avatarPath = getImagePath(selectedImageUri);

            if (avatarPath != null) {
                if (!avatarPath.equals("")) {
                    ibProfilePicture.setImageURI(selectedImageUri);
                    avatar = new File(avatarPath);
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ApplicationConstants.MY_PERMISSIONS_CODE) {

            if (grantResults[0] == 0) {
                getProfilePictureIntent();
            }
        }
    }

    private void getProfilePictureIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, ApplicationConstants.SELECT_IMAGE);
    }


    private String getImagePath(Uri uri) {
        String imagePath = null;

        if (uri == null)
            return null;

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            imagePath = cursor.getString(columnIndex);
        }

        cursor.close();

        return imagePath;
    }
}
