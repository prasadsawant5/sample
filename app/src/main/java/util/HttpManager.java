package util;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

import constants.ApplicationConstants;
import constants.ServerConstants;
import storage.MySharedPreferences;

/**
 * Created by prasadsawant on 12/5/16.
 */

public class HttpManager {

    private static final String TAG = HttpManager.class.getName();

    public static HttpManager instanceOf() {
        return new HttpManager();
    }

    /**
     * @description Method for uploading avatar to AWS S3 bucket
     * @param context
     * @param file
     * @param email
     */
    public void uploadAvatar(Context context, File file, String email) {

        String extension = file.getName().substring((file.getName().lastIndexOf('.')), file.getName().length());

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                ServerConstants.AWS_IDENTITY_POOL,                  // Identity Pool ID
                Regions.US_WEST_2                                   // Region
        );


        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        TransferUtility transferUtility = new TransferUtility(s3, context);

        TransferObserver observer = transferUtility.upload(
                ServerConstants.AWS_BUCKET_NAME,                    /* The bucket to upload to */
                String.valueOf(email.hashCode()) + extension,       /* The key for the uploaded object */
                file                                                /* The file where the data to upload exists */
        );


        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {

            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent / bytesTotal * 100);
            }

            @Override
            public void onError(int id, Exception ex) {

            }
        });


        MySharedPreferences.setPreferenceString(context, ApplicationConstants.PREFERENCE_AVATAR_URL, ServerConstants.AWS_BUCKET_URL + String.valueOf(email.hashCode()) + extension);

    }
}
