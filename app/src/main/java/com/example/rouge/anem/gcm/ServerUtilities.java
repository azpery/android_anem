package com.example.rouge.anem.gcm;

/**
 * Created by darkvador on 14/04/15.
 */
import static com.example.rouge.anem.gcm.CommonUtilities.SERVER_URL;
import static com.example.rouge.anem.gcm.CommonUtilities.TAG;
import static com.example.rouge.anem.gcm.CommonUtilities.displayMessage;

import java.util.Random;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rouge.anem.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;


public final class ServerUtilities {
    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    /**
     * Register this account/device pair within the server.
     *
     */
    static void register(final Context context, String name, String email, final String regId) {
        Log.i(TAG, "registering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL;
        String[] params = {serverUrl,regId,name,email};

        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
                displayMessage(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
                post( params);
                String message = context.getString(R.string.server_registered);
                CommonUtilities.displayMessage(context, message);
                return;

        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);
    }

    /**
     * Unregister this account/device pair within the server.
     */
    static void unregister(final Context context, final String regId) {
        Log.i(TAG, "unregistering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL + "/unregister";
        String[] params = {serverUrl,regId};
           post(params);
            //GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
            CommonUtilities.displayMessage(context, message);

    }


    private static void post(String[] params) {
        AsyncTask<String, String, Boolean> mThreadCon =new AsyncSignUpNotification().execute(params);
    }
}
