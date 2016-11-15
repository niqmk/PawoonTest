package com.example.michael.pawoontest;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class App extends Application {
	private static Context context;
	public static SqlManager sql_manager;
	public static SQLiteDatabase sql_connection;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		sql_manager = new SqlManager("data/data/com.example.michael.pawoontest/database/", "db.sqlite");
		sql_connection = sql_manager.openConnection();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		unload();
	}

	public static Context getContext() {
		return context;
	}

	public static void unload() {
		sql_manager.closeConnection(sql_manager.getDatabase());
	}
}