package com.example.chat;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.AmplifyModelProvider;

public class amplifyApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        AmplifyModelProvider modelProvider = AmplifyModelProvider.getInstance();

        try {
            Amplify.addPlugin(new AWSDataStorePlugin(modelProvider));
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());

            Amplify.configure(getApplicationContext());

        } catch (AmplifyException e) {
            e.printStackTrace();
        }
    }
}
