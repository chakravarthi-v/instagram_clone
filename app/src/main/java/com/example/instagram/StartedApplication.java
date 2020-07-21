package com.example.instagram;
import android.app.Application;
import android.util.Log;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
public class StartedApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
// Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        /* user + M4vSrVYUj6yt*/
// Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("myappID")
                .clientKey("b38MzIonl7eL")
                .server("http://13.59.75.47/parse")
                .build()
        );

        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }


}
