package database.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SQLiteHelper extends DatabaseHelper {

	private String query;
	private String tableName;
	private String fields;
	private ArrayList<HashMap<String, String>> collection;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public SQLiteHelper(Context context) {
		super(context);
		this.tableName = "";
		this.query = "";
		this.fields = "*";
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public void select() {
		this.query = "SELECT " + this.fields + " ";
	}

	public void from() {
		this.query += "FROM " + this.tableName + " ";
	}

	public void where(String condition) {
		this.query += "WHERE " + condition + " ";
	}

	public void join(String join) {
		this.query += "INNER JOIN " + join + " ";
	}

	public void left(String join) {
		this.query += "LEFT JOIN " + join + " ";
	}

	public void right(String join) {
		this.query += "RIGHT JOIN " + join + " ";
	}

	public static List<HashMap<String, String>> filterList(
			List<HashMap<String, String>> list, String filterTxt, String column) {
		List<HashMap<String, String>> filteredList = new ArrayList<HashMap<String, String>>();

		for (HashMap<String, String> item : list) {
			if (item.get(column) != null
					&& item.get(column).toLowerCase(Locale.ENGLISH)
							.contains(filterTxt.toLowerCase(Locale.ENGLISH))) {
				filteredList.add(item);
			}
		}
		return filteredList;
	}

	/**
	 * 
	 * @param cursor
	 * @return
	 */
	private List<HashMap<String, String>> createNameValuePairs(Cursor cursor) {
		if (cursor.getCount() > 0) {
			collection = new ArrayList<HashMap<String, String>>();
			String[] columns = cursor.getColumnNames();
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				Map<String, String> row = new HashMap<String, String>();
				for (int j = 0; j < columns.length; j++) {
					String value = cursor.getString(cursor
							.getColumnIndex(columns[j]));
					row.put(columns[j], value);
				}
				cursor.moveToNext();
				collection.add((HashMap<String, String>) row);
			}
		}
		cursor.close();
		return collection;
	}

	/**
	 * 
	 * @param condition
	 * @return
	 */
	public HashMap<String, String> findFirst(String condition) {
		this.query = "SELECT * FROM " + this.tableName + " WHERE " + condition
				+ ";";
		List<HashMap<String, String>> Result = createNameValuePairs(this
				.executeRawQuery());
		if (Result != null)
			return Result.get(0);
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public List<HashMap<String, String>> find() {
		this.query = "SELECT * FROM " + this.tableName + ";";
		return createNameValuePairs(this.executeRawQuery());
	}

	/**
	 * 
	 * @param Sql
	 */
	public void execSQL(String Sql) {
		SQLiteDB.execSQL(Sql);
	}

	public Cursor fetchAll(String table, String[] columns) {
		return SQLiteDB.query(table, columns, null, null, null, null, null);
	}

	public Cursor executeRawQuery() {
		return SQLiteDB.rawQuery(this.query, null);
	}

	public boolean update(ContentValues values, String whereClause,
			String[] whereArgs) {
		return SQLiteDB.update(this.tableName, values, whereClause, whereArgs) > 0;
	}

	public long save(ContentValues values) {
		return SQLiteDB.insert(this.tableName, null, values);
	}

	public int delete(String whereClause, String[] whereArgs) {
		return SQLiteDB.delete(this.tableName, whereClause, whereArgs);
	}

	public void truncateTable() {
		SQLiteDB.execSQL("delete from " + this.tableName);
	}
}
