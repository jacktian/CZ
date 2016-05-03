package com.android.user;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;

public class UseApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Parse.initialize(this, "wBCgv2yUA8VTqN6du7fQHNlI0nNAwwgLW0AliIUJ", "wuSi6dCFSVqhsd7pUEh5htIiVnxDvg2a1BmBzH7J");
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}

}
