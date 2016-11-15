package com.example.michael.pawoontest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppHolder> {
	private ArrayList<AppModel> app_list;

	public AppListAdapter(final ArrayList<AppModel> app_list) {
		this.app_list = app_list;
	}

	@Override
	public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_app, parent, false);
		return new AppHolder(view);
	}

	@Override
	public void onBindViewHolder(AppHolder holder, final int position) {
		final AppModel app_model = app_list.get(position);
		holder.lbl_title.setText(app_model.title);
	}

	@Override
	public int getItemCount() {
		return app_list.size();
	}

	public class AppHolder extends RecyclerView.ViewHolder {
		public TextView lbl_title;

		public AppHolder(View view) {
			super(view);
			lbl_title = (TextView) view.findViewById(R.id.lbl_title);
		}
	}
}