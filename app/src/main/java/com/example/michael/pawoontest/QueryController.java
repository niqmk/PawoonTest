package com.example.michael.pawoontest;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

public class QueryController {
	public static ArrayList<AppModel> getAppList() {
		final ArrayList<AppModel> list = new ArrayList<>();
		if (App.sql_connection == null) {
			return list;
		}
		final String sql = "SELECT userId, id, title, body";
		Cursor c = null;
		try {
			c = App.sql_connection.rawQuery(sql, null);
			while (c.moveToNext()) {
				final AppModel app_model = new AppModel();
				app_model.userId = c.getInt(c.getColumnIndex("userId"));
				app_model.id = c.getInt(c.getColumnIndex("id"));
				app_model.title = c.getString(c.getColumnIndex("title"));
				app_model.body = c.getString(c.getColumnIndex("body"));
				list.add(app_model);
			}
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLiteException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}

	public static void addApp(final ArrayList<AppModel> list) {
		for (AppModel app_model : list) {
			addApp(app_model);
		}
	}

	public static void addApp(final AppModel app_model) {
		if (App.sql_connection == null) {
			return;
		}
		App.sql_connection.execSQL(
				"INSERT INTO app (userId, id, title, body) VALUES (?, ?, ?, ?)",
				new Object[]{
						app_model.userId,
						app_model.id,
						app_model.title,
						app_model.body
				});
	}

	public static boolean isAppExisted() {
		if (App.sql_connection == null) {
			return false;
		}
		boolean exist = false;
		String sql = "SELECT COUNT(*) FROM app";
		Cursor c = null;
		try {
			c = App.sql_connection.rawQuery(sql, null);
			c.moveToFirst();
			if (c.getInt(0) > 0) {
				exist = true;
			} else {
				exist = false;
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return exist;
	}

	public static void removeApp() {
		if (App.sql_connection == null) {
			return;
		}
		App.sql_connection.execSQL("DELETE FROM app", null);
	}
}