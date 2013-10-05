package database.sqlite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DatabaseHelper extends SQLiteOpenHelper {

	private Context mycontext;

	private static String DB_PATH = "data/data/PACKAGE-NAME-HERE/databases/";
	private static String DB_NAME = "DB FILE FROM ASSETS";
	private static final int DATABASE_VERSION = 1;
	public SQLiteDatabase SQLiteDB;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);

		this.mycontext = context;
		boolean dbexist = checkdatabase();
		if (dbexist) {
		} else {
			try {
				createdatabase();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	public static void removeDatabase() {
		try {
			File file = new File(DB_PATH + DB_NAME);
			if (file.exists()) {
				backupDatabase();
			}
			if (file.delete()) {

			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createdatabase() throws IOException {
		boolean dbexist = checkdatabase();
		if (dbexist) {
		} else {
			this.getReadableDatabase();
			try {
				copydatabase();
			} catch (IOException e) {
			}
		}
	}

	private boolean checkdatabase() {
		boolean checkdb = false;
		try {
			String myPath = DB_PATH + DB_NAME;
			File dbfile = new File(myPath);
			checkdb = dbfile.exists();
		} catch (SQLiteException e) {
			// Log.e("message", "Database doesn't exist");
		}

		return checkdb;
	}

	public static void backupDatabase() throws IOException {
		final String inFileName = DB_PATH + DB_NAME;
		File dbFile = new File(inFileName);
		FileInputStream fis = new FileInputStream(dbFile);
		String outFileName = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES).toString()
				+ "/backup_sqlite.db";
		// Open the empty db as the output stream
		OutputStream output = new FileOutputStream(outFileName);
		// Transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = fis.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}
		// Close the streams
		output.flush();
		output.close();
		fis.close();
	}

	private void copydatabase() throws IOException {
		// Open your local db as the input stream
		InputStream myinput = mycontext.getAssets().open(DB_NAME);
		// Path to the just created empty db
		String outfilename = DB_PATH + DB_NAME;
		// Open the empty db as the output stream
		OutputStream myoutput = new FileOutputStream(outfilename);
		// transfer byte to inputfile to outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myinput.read(buffer)) > 0) {
			myoutput.write(buffer, 0, length);
		}
		// Close the streams
		myoutput.flush();
		myoutput.close();
		myinput.close();
	}

	public void open() {
		// Open the database
		String mypath = DB_PATH + DB_NAME;
		SQLiteDB = SQLiteDatabase.openDatabase(mypath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {
		SQLiteDB.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}