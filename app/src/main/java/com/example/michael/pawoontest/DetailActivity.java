package com.example.michael.pawoontest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
	private RecyclerView list;
	private AppListAdapter app_list_adapter;

	private ArrayList<AppModel> app_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		list = (RecyclerView) findViewById(R.id.list);
		list.setLayoutManager(new LinearLayoutManager(this));
		list.setItemAnimator(new DefaultItemAnimator());
		setInitial();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void setInitial() {
		if (QueryController.isAppExisted()) {
			app_list = QueryController.getAppList();
			setList();
		} else {
			URLController.request(Request.Method.GET, "http://jsonplaceholder.typicode.com/posts", new URLController.URLCallback() {
				@Override
				public void didURLResponse(String response) {
					final AppModel app_model = new AppModel(response);
					DetailActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							app_list = app_model.list;
							QueryController.addApp(app_model.list);
							setList();
						}
					});
				}

				@Override
				public void didURLFailed(int status_code, String response) {
					DetailActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							finish();
						}
					});
				}
			});
		}
	}

	private void setList() {
		app_list_adapter = new AppListAdapter(app_list);
		list.setAdapter(app_list_adapter);
	}
}