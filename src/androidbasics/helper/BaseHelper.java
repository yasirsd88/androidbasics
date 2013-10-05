package androidbasics.helper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class BaseHelper {
	/*
	 * CONVERTING ARRAYLIST TO COMMA SEPERATED STRING
	 */

	public static String ListToString(String glue, List<Integer> myList) {
		String newString = "";
		for (Iterator<Integer> it = myList.iterator(); it.hasNext();) {
			newString += it.next();
			if (it.hasNext()) {
				newString += ", ";
			}
		}
		return newString;
	}

	public static void showToastMessage(Context context, String text,
			int duration) {
		Toast.makeText(context, text, duration).show();
	}

	public static int[] getScreenDimensions(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int h = metrics.heightPixels;
		int w = metrics.widthPixels;
		int dimensions[] = { w, h };
		return dimensions;
	}

	public static String getScreenDimensionsString(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int h = metrics.heightPixels;
		int w = metrics.widthPixels;

		return w + "x" + h;
	}

	public static Bitmap getBitmapFromAssetWithException(String strName,
			Context context) {
		AssetManager assetManager = context.getAssets();
		InputStream istr = null;
		try {
			istr = assetManager.open(strName.trim());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Bitmap bitmap = BitmapFactory.decodeStream(istr);
		return bitmap;
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isFloat(String input) {
		try {
			Float.parseFloat(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static int getImageResId(String imagename, Context context) {
		String uri = "drawable/" + imagename;

		// int imageResource = R.drawable.icon;
		return context.getResources().getIdentifier(uri, null,
				"com.golf.moregolfclub");
	}

	public static int getResId(String variableName, Context context, Class<?> c) {

		try {
			Field idField = c.getDeclaredField(variableName);
			return idField.getInt(idField);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static String convertToCurrency(float value) {
		DecimalFormat format = new DecimalFormat("#.##");
		try {
			value = Float.parseFloat(format.format(value));
		} catch (Exception e) {
			e.printStackTrace();
		}
		NumberFormat defaultFormat = NumberFormat
				.getCurrencyInstance(Locale.CANADA);
		return defaultFormat.format(value);
	}

	public static String[] getStringList(String column,
			List<HashMap<String, String>> list) {
		String[] values = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			values[i] = list.get(i).get(column);
		}
		return values;
	}

	public static ProgressDialog showProgressDialog(Context context,
			String title, String message) {
		ProgressDialog pd = new ProgressDialog(context);
		pd.setTitle(title);
		pd.setMessage(message);
		if (pd != null && !pd.isShowing())
			pd.show();
		return pd;
	}

	public static List<HashMap<String, String>> addCheckboxSupport(
			List<HashMap<String, String>> list) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("isChecked", "false");
		}
		return list;
	}

	/**
	 * Method to join array elements of type string
	 * 
	 * @author Hendrik Will, imwill.com
	 * @param inputArray
	 *            Array which contains strings
	 * @param glueString
	 *            String between each array element
	 * @return String containing all array elements seperated by glue string
	 */
	public static String implodeArray(String[] inputArray, String glueString) {

		/** Output variable */
		String output = "";

		if (inputArray.length > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(inputArray[0]);

			for (int i = 1; i < inputArray.length; i++) {
				sb.append(glueString);
				sb.append(inputArray[i]);
			}

			output = sb.toString();
		}

		return output;
	}

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
