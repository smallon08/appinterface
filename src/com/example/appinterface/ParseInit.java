package com.example.appinterface;

import android.app.Application;

import com.parse.Parse;

public class ParseInit extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		  Parse.initialize(this, "BgSuc0RvWGTmzdiyN2OCNdtqCzTdJ2mwUSx9Eevj", "nYAAwbRNBqE1XhQWs9vyWUmleFkUVspHKgxE6vkY");		  
	}
	
}