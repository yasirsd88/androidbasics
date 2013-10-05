package adapters.listview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class BaseAdapter extends ArrayAdapter<Map<String, String>[]> {

	Context context;
	int layoutResourceId;
	List<HashMap<String, String>> data = null;

	public BaseAdapter(Context context, int layoutResourceId,
			List<HashMap<String, String>> data2) {
		super(context, layoutResourceId);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		Object holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = initializeHolder(holder);
			row.setTag(holder);
		} else {
			holder = row.getTag();
		}
		initializeRow(row,holder);
		return row;
	}

	private Object initializeHolder(Object holder) {
		return holder;
	}

	protected void initializeRow(View row, Object holder) {

	}
}