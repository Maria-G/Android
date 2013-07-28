package com.example.mariayapcapp;

import com.example.mariayapcapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
   // public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	private Intent scoringActivity;
	private String[] names;
	private final String NAME_ID = "nameId";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		names = this.getResources().getStringArray(R.array.activity_main_names);

		scoringActivity = new Intent(getBaseContext(), ScoringActivity.class);
		
		setItemClickListenerOnNamesLV();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setItemClickListenerOnNamesLV(){
		final ListView lv = (ListView) findViewById(R.id.listViewNames);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		
			public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {    
				
				scoringActivity.putExtra(NAME_ID, getNameId(myItemInt));

				startActivity(scoringActivity);				
			}
		});
	}
	
	private String getNameId(int nameIdx){
		return names[nameIdx];
	}

	
	

}
