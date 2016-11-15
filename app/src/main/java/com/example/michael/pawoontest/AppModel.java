package com.example.michael.pawoontest;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppModel implements Parcelable {
	public int userId;
	public int id;
	public String title = "";
	public String body = "";
	public ArrayList<AppModel> list = new ArrayList<>();

	public AppModel() {
	}

	public AppModel(final String response) {
		try {
			final JSONArray array = new JSONArray(response);
			for (int counter = 0; counter < array.length(); counter++) {
				final AppModel app_model = new AppModel(array.getJSONObject(counter));
				list.add(app_model);
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public AppModel(final JSONObject json) {
		try {
			userId = json.getInt("userId");
			id = json.getInt("id");
			title = json.getString("title");
			body = json.getString("body");
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	protected AppModel(Parcel in) {
		userId = in.readInt();
		id = in.readInt();
		title = in.readString();
		body = in.readString();
		list = in.createTypedArrayList(AppModel.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(userId);
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(body);
		dest.writeTypedList(list);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<AppModel> CREATOR = new Creator<AppModel>() {
		@Override
		public AppModel createFromParcel(Parcel in) {
			return new AppModel(in);
		}

		@Override
		public AppModel[] newArray(int size) {
			return new AppModel[size];
		}
	};
}