package me.skyle.activities;

import java.io.InputStream;

import me.skyle.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;

public class ItemDialogActivity extends Activity {

	private final static String TAG = "TagDialogActivity";
	private EditText filterEditor;
	private ArrayAdapter<String> itemAdapter;
	public static String selected = "";
	int selectedPosition;
	private ListView listView;
	private String[] itemTypes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_item_dialog);
	    
	    Log.i(TAG, "Let's start");
	    
	    itemTypes = getResources().getStringArray(R.array.clothing_items);

	    itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemTypes);   	    
		
	    listView = (ListView) findViewById(R.id.tag_dialog_list);
		listView.setAdapter(itemAdapter);

		filterEditor = (EditText) findViewById(R.id.tag_dialog_search_box);

		// Add a listener to the EditText that will update the filter.
		filterEditor.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before,
			    int count) {
		    	updateText();
		    	
		    }

		    public void beforeTextChanged(CharSequence s, int start, int count,
			    int after) {
		    	updateText();
		    	emptyText();
		    }

		    public void afterTextChanged(Editable s) {
		    	updateText();
		    	emptyText();
		    }

		    private void updateText() {
				// Update the filter according to the contents of the field.
				itemAdapter.getFilter().filter(filterEditor.getText().toString());				
			}
		    
		    private void emptyText() {
		    	/*
		    	if (itemAdapter.isEmpty() && FLAG != AnswerActivity.ITEM_REQUEST_CODE) { // you cannot add custom items because you need an item id (that is set in a database)
					Log.i(TAG, "PRAZEN!");
					itemAdapter.add(filterEditor.getText().toString());
				}
				*/
		    }
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {

			    selected = parent.getItemAtPosition(position).toString();
			    AddItemActivity.ITEM_TYPE = selected;
				
				Log.i(TAG, "selected: "+selected+", item-position: "+position);
				//ItemDialogActivity.this.finish();
			}});
	}
}