package com.example.mariayapcapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
public class ScoringActivity extends Activity {
	final int SPINNER_IDX_IN_LISTVIEW_ITEM = 1;
	private ArrayList<LinearLayout> questionLayouts = new ArrayList<LinearLayout>();
	private Intent mainActivity;
	private int numQuestions;
	private String nameId;
	public static final String PREFS_NAME = "MyPrefsFile";
	private SharedPreferences settings;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scoring);
		//updateNameId
		updateNameId();
		
		//set Title
		this.setTitle("Scores - " + nameId);
		
		//set numQuestions	
		numQuestions = getQuestions().length;
		
	    // Restore preferences
	    settings = getSharedPreferences(PREFS_NAME, 0);
	    
	    //Create MainActivity
		mainActivity = new Intent(getBaseContext(), MainActivity.class);
		
		populateQuestionsView();   
		setClickListenerOnDoneBtn();
		
	}
	
    @Override
    protected void onStop(){
       super.onStop();
      
       //Add all spinner selections to preferences
       updatePreferences();

    }
    
    private void updatePreferences(){
        // Editor object to make preference changes.
        SharedPreferences.Editor editor = settings.edit();
		
		for( int questionIdx = 0; questionIdx < numQuestions; questionIdx++ ){
			
			String key = nameId + questionIdx;
			Spinner sp = getSpinnerAt(questionIdx);
			
			//SelectionIdx
			int idx = sp.getSelectedItemPosition();
			
			//Add SelectionIdx to prefs			 
		    editor.putInt(key, idx);
		}
		
		// Commit the edits!
	    editor.commit();
    }
	
	
	private void updateNameId(){
		Bundle extras = getIntent().getExtras();
		if(extras !=null)
		{
		     nameId = extras.getString("nameId");
		}
	}

	private void setClickListenerOnDoneBtn(){

		Button btnQuestionsDone =(Button)findViewById(R.id.btnQuestionsDone);
		btnQuestionsDone.setOnClickListener(new View.OnClickListener() {

		            @Override
		            public void onClick(View v) {
		            	
		                startActivity(mainActivity);
		            }
		        });
	}
	
	
	
	private Spinner getSpinnerAt(int questionIdx){
		//get inner linearlayout (horizontal)
		ViewGroup questionLayout = this.questionLayouts.get(questionIdx);
		//get spinner
		Spinner sp = (Spinner) questionLayout.getChildAt(SPINNER_IDX_IN_LISTVIEW_ITEM);
		return sp;
	}
	
	
	 private int getSpinnerSelection(int spinnerIdx){
		//Get spinner selection value according to preferences
			String key = nameId + spinnerIdx;
			int selectId = settings.getInt(key, 0);
			return selectId;
	 }
	    
     private ArrayAdapter<String> createSpinnerAdapter(){
    	
    	Resources res = this.getResources();
    	
		String[] spEntries = res.getStringArray(R.array.activity_questions_page_spinner_values);
    	List<String> SpinnerArray =  new ArrayList<String>();
        for ( int j = 0; j < spEntries.length; j++ ){
        	SpinnerArray.add(spEntries[j]);
    	}

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        
        return spinnerAdapter;
    }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scoring, menu);
		return true;
	}
	
	
	private void populateQuestionsView(){
		LinearLayout containerLayout = (LinearLayout) findViewById(R.id.linearLayoutQuestionsContainer);
				
		String[] qArr = getQuestions();	

		//Create Spinner Adapter
		ArrayAdapter<String> spinnerAdapter = createSpinnerAdapter();
		
		
		for( int idx = 0; idx < numQuestions; idx++ ){
			
			//Create View 
			LinearLayout questionLayout = new LinearLayout(getBaseContext());
			
			//Create TextView
			TextView tv = new TextView(getBaseContext());
			tv.setText(qArr[idx]);
			
			//Create Spinner
			final Spinner sp = new Spinner(getBaseContext());
			sp.setAdapter(spinnerAdapter);

			//Get spinner selection value according to preferences
			int selectionId = getSpinnerSelection(idx);
			
			//Set selection on spinner
			sp.setSelection(selectionId);
			
			//Add TextView and Spinner to layout
			questionLayout.addView(tv);
			questionLayout.addView(sp);
			
			//Add View
        	containerLayout.addView(questionLayout);
        	this.questionLayouts.add(idx, questionLayout);
			
		}
		

		
	}
	
	private String[] getQuestions(){
		Resources res = this.getResources(); 
		return res.getStringArray(R.array.activity_questions_page_questions); 
	}
	
}
