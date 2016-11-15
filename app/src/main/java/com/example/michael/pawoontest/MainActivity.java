package com.example.michael.pawoontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
	private Button btn_detail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_detail = (Button) findViewById(R.id.btn_detail);
		btn_detail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final Intent intent = new Intent(MainActivity.this, DetailActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onDestroy() {
		QueryController.removeApp();
		super.onDestroy();
	}
}
