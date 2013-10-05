package webservice.httprequest;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;

public class HttpHelper {
	public static ContentValues createContentValues(Map<?, ?> list) {

		ContentValues values = new ContentValues();
		Iterator<?> entries = list.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> entry = (Entry<String, String>) entries
					.next();
			values.put(entry.getKey(), entry.getValue());
		}
		return values;
	}
}
