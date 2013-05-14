package com.xjl.downloadservice;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class NextActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);
	}

	public void back(View view)
	{
		this.finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.next, menu);
		return true;
	}

}
