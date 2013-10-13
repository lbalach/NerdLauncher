package com.lacreatelit.android.nerdlauncher;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NerdLauncherFragment extends ListFragment {
	
	private static final String TAG = "NerdLauncherFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		PackageManager packageManager = getActivity().getPackageManager();
		List<ResolveInfo> activityInfoList = packageManager
				.queryIntentActivities(intent, 0);
		
		Log.i(TAG, "Number of activities = " + activityInfoList.size());

		// Sort the List of activities based upon the display label
		Collections.sort(activityInfoList, new Comparator<ResolveInfo>() {

			@Override
			public int compare(ResolveInfo lhs, ResolveInfo rhs) {
				
				PackageManager packageManager = getActivity()
													.getPackageManager();
				return String.CASE_INSENSITIVE_ORDER.compare(
						lhs.loadLabel(packageManager).toString(), 
						rhs.loadLabel(packageManager).toString());
			}
		});
		
		// Define the ArrayAdapter to be used for the ListView
		ArrayAdapter<ResolveInfo> arrayAdapter = new ArrayAdapter<ResolveInfo>(
				getActivity(), android.R.layout.simple_list_item_1, 
				activityInfoList) {

					@Override
					public View getView(int position, View convertView,
							ViewGroup parent) {
						
						View view = super.getView(position, convertView, parent);
						TextView textView = (TextView)view;
						
						PackageManager packageManager = getActivity()
								.getPackageManager();
						
						ResolveInfo resolveInfo = getItem(position);
						textView.setText(resolveInfo.loadLabel(packageManager)
								.toString());
						
						return view;
					}
		};
		
		// Set the ArrayAdapter as the data source for the ListView
		setListAdapter(arrayAdapter);
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		
		ResolveInfo resolveInfo = (ResolveInfo)listView.getAdapter()
				.getItem(position);
		ActivityInfo activityInfo = resolveInfo.activityInfo;
		
		if(activityInfo == null) {
			Log.d(TAG, "Cannot resolve activity");
			return;
		}
		
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setClassName(activityInfo.applicationInfo.packageName, 
				activityInfo.name);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		startActivity(intent);
		
		
	}
	
	
	

}
