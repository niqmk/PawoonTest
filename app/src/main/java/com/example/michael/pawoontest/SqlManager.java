package com.example.michael.pawoontest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.database.sqlite.SQLiteDatabase;

public class SqlManager {
	private final String sql_path;
	private final String sql_file;
	private SQLiteDatabase database;

	public SqlManager(final String sql_path, final String sql_file) {
		this.sql_path = sql_path;
		this.sql_file = sql_file;
	}

	public SQLiteDatabase openConnection() {
		createDatabase();
		database = SQLiteDatabase.openDatabase(sql_path + sql_file, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		return database;
	}

	public void closeConnection(SQLiteDatabase DBConnection) {
		if (DBConnection != null) {
			DBConnection.close();
		}
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	private void createDatabase() {
		boolean dbExist = checkDataBase();
		if (dbExist) {
		} else {
			try {
				InputStream input = App.getContext().getAssets().open(sql_file);
				new File(sql_path + sql_file).getParentFile().mkdir();
				OutputStream output = new FileOutputStream(sql_path + sql_file);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = input.read(buffer)) > 0) {
					output.write(buffer, 0, length);
				}
				output.flush();
				output.close();
				input.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private boolean checkDataBase() {
		File dbFile = new File(sql_path + sql_file);
		return dbFile.exists();
	}
}