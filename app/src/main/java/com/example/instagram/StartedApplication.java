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
        

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("myappID")
                .clientKey("Add your masterkey")(cant provide here with masterkey since its the acces key to the parse dashboard)
                .server("http://13.59.75.47/parse")
                .build()
        );

        
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }


}
