package com.androidbasics;

import database.sqlite.SQLiteHelper;
import android.app.Activity;
import androidbasics.helper.BaseHelper;

public class BaseActivity extends Activity {

	protected static SQLiteHelper db = null;
	protected BaseHelper helpers = new BaseHelper();

	void initDb() {
		if (db == null) {
			db = new SQLiteHelper(getApplicationContext());
			db.open();
		}
	}

	void destroyDbObject() {
		if (db == null) {
			db.close();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		destroyDbObject();
	}
}
