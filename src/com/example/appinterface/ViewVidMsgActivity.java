package com.example.appinterface;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ViewVidMsgActivity extends  ListActivity{
	
	protected List<ParseObject> mMessages;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_vid_msg);
		
		ParseUser.logInInBackground("doctor", "123123", new LogInCallback() {			
			@Override
			public void done(ParseUser user, ParseException e) {
				if(e==null){		
                    Toast.makeText(getApplicationContext(),
                            "logined in parse successfully", Toast.LENGTH_LONG).show();

					

					
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(ViewVidMsgActivity.this);
					builder.setTitle("error loggin")
							.setMessage(e.getMessage())
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();								
				}
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_vid_msg, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		ParseQuery<ParseObject>	query = new ParseQuery<ParseObject>(ParseConstants.CLASS_MESSAGES);
		query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
		query.addDescendingOrder(ParseConstants.KEY_CREATE_AT);
		query.findInBackground(new FindCallback<ParseObject>() {			
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				
				if(e==null){
					mMessages = messages;
					
					String[] usernames = new String[mMessages.size()];
//					Toast.makeText(getListView().getContext(),"msg size:"+mMessages.size(), Toast.LENGTH_LONG).show();
					int i = 0;
					for(ParseObject message : mMessages){
						usernames[i] = message.getString(ParseConstants.KEY_SENDER_NAME);
//						Toast.makeText(getListView().getContext(),"name:"+usernames[i], Toast.LENGTH_LONG).show();
						i++;

					}
					MessageAdapter adapter = new MessageAdapter(
							getListView().getContext(), 
							mMessages);
					setListAdapter(adapter);
					
				}
			}
		});
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		ParseObject message = mMessages.get(position);
		String messageType = message.getString(ParseConstants.KEY_FILE_TYPE);
		ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
		Uri fileUri = Uri.parse(file.getUrl());
		
			Intent intent = new Intent(Intent.ACTION_VIEW,fileUri);
			intent.setDataAndType(fileUri, "video/*");
			startActivity(intent);
		
		
	}

}
