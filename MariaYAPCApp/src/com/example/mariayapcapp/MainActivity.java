package com.example.mariayapcapp;

import java.util.ArrayList;
import java.util.List;

import com.example.mariayapcapp.R;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private final static int NAMES_VIEW_INDEX = 0;
    private final static int QUESTIONS_VIEW_INDEX = 1;
	private ArrayList<int[]> questionValues = new ArrayList<int[]>();
	private ArrayList<LinearLayout> questionLayouts = new ArrayList<LinearLayout>();
	private int nameIdx = -1;
	private ViewFlipper vf; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.vf = (ViewFlipper) findViewById(R.id.viewFlipper); 
		
		createQuestionsArray();
		setItemClickListenerOnNamesLV();
		populateQuestionsView();
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String[] getQuestions(){
		Resources res = this.getResources(); 
		return res.getStringArray(R.array.activity_questions_page_questions); 
	}
	
	private String[] getNames(){
		Resources res = this.getResources(); 
		return res.getStringArray(R.array.activity_main_names); 
	}
	
	private void populateQuestionsView(){
		LinearLayout containerLayout = (LinearLayout) findViewById(R.id.linearLayoutQuestionsContainer);
				
		String[] qArr = getQuestions();		
		int numQuestions = qArr.length;

		//Create Spinner Adapter
		ArrayAdapter<String> spinnerAdapter = createSpinnerAdapter();
		
		
		for( int idx = 0; idx < numQuestions; idx++ ){
			
			//Create View 
			LinearLayout questionLayout = new LinearLayout(getBaseContext());
			
			TextView tv = new TextView(getBaseContext());
			tv.setText(qArr[idx]);
			
			Spinner sp = new Spinner(getBaseContext());
			sp.setAdapter(spinnerAdapter);
			
			questionLayout.addView(tv);
			questionLayout.addView(sp);
			

        	containerLayout.addView(questionLayout);
        	this.questionLayouts.add(idx, questionLayout);
			
		}
		

		
	}
	 

    private void createQuestionsArray(){
		
		String[] qArr = getQuestions();
		int numQuestions = qArr.length;
		
		String[] nArr = getNames();
		
		for(int i = 0; i < nArr.length; i++){
			questionValues.add(new int[numQuestions]);
		}
	}
	
	private void setItemClickListenerOnNamesLV(){
		final ListView lv = (ListView) findViewById(R.id.listViewNames);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		
			public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
				nameIdx = myItemInt;
				updateSpinners();
				vf.setDisplayedChild(QUESTIONS_VIEW_INDEX);		
			}
		});
	}
	

    public void onClickDone(View v){

    	updateQuestionValues();
		
    	vf.setDisplayedChild(NAMES_VIEW_INDEX);
    }
    
    
    private void updateQuestionValues(){

		final int spinnerIdx = 1;
		
		int[] values = questionValues.get(nameIdx);
	
		int numQuestions = this.questionLayouts.size();
		
		for( int questionIdx = 0; questionIdx < numQuestions; questionIdx++ ){
			//get inner linearlayout (horizontal)
			ViewGroup questionLayout = this.questionLayouts.get(questionIdx);
			//get spinner
			Spinner sp = (Spinner) questionLayout.getChildAt(spinnerIdx);
			
			//SelectionIdx
			int idx = sp.getSelectedItemPosition();
			
			//Add SelectionIdx to values.
			 values[questionIdx] = idx;
		}
		
		
    }
    
 
    private void updateSpinners(){
    	final int spinnerIdx = 1;
		
		int[] values = questionValues.get(nameIdx);
	
		int numQuestions = this.questionLayouts.size();
		
		for( int questionIdx = 0; questionIdx < numQuestions; questionIdx++ ){
			//get inner linearlayout (horizontal)
			ViewGroup questionLayout = this.questionLayouts.get(questionIdx);
			//get spinner
			Spinner sp = (Spinner) questionLayout.getChildAt(spinnerIdx);
			//Set selection according to idx in questionValues for name corresponding with nameIdx
			sp.setSelection(values[questionIdx]);
		}
    }
   
    
    private ArrayAdapter<String> createSpinnerAdapter(){
    	
    	Resources res = this.getResources();
    	
    	//Create SpinnerAdaptor
		String[] spEntries = res.getStringArray(R.array.activity_questions_page_spinner_values);
    	List<String> SpinnerArray =  new ArrayList<String>();
        for ( int j = 0; j < spEntries.length; j++ ){
        	SpinnerArray.add(spEntries[j]);
    	}

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //
        
        return spinnerAdapter;
    }

   

}
