package com.example.appinterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appinterface.library.DatabaseHandler;


public class NewComment extends Activity {
	String name;
	String comment;
	EditText input ;
	Button subBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_comment);
		
		input = (EditText) findViewById(R.id.editTextSimple);
		subBtn = (Button) findViewById(R.id.button1);
		
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        /**
         * Hashmap to load data from the Sqlite database
         **/
         HashMap<String,String> user = new HashMap<String, String>();
         user = db.getUserDetails();
         name = user.get("email").split("@")[0];
         //TextView email = (TextView) findViewById(R.id.textView);
         Toast.makeText(this, "email:"+ name, Toast.LENGTH_LONG).show();
         
         subBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				comment = input.getText().toString();
		         Toast.makeText(NewComment.this, name+"input:"+ comment, Toast.LENGTH_LONG).show();
		         new FetchTask().execute();
		         input.setText("");
		         input.setHint("Enter other comment.");
				
			}
		});
         
         comment = input.getText().toString();
         //new FetchTask().execute();
         
         
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_comment, menu);
		return true;
	}
	
    public class FetchTask extends AsyncTask<Void, Void, JSONArray> {
	    @Override
	    protected JSONArray doInBackground(Void... params) {
	        try {
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("http://54.254.179.218/fyppcom.php");

	            
	            // Add your data
	            
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
	            nameValuePairs.add(new BasicNameValuePair("name",name));
	            nameValuePairs.add(new BasicNameValuePair("comment",comment));

	            
	          //  nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	            // Execute HTTP Post Request
	            HttpResponse response = httpclient.execute(httppost);

	            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            sb.append(reader.readLine() + "\n");
	            String line = "0";
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            reader.close();
	            String result11 = sb.toString();
 
	            // parsing data
 	            return new JSONArray(result11);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	        finally{
	        	
	        	
	        }
	    }
	     

	    @Override
	    protected void onPostExecute(JSONArray result) {
	        if (result != null) {
	            // do something
	            Toast.makeText(NewComment.this, "commet is posted:"+ name, Toast.LENGTH_LONG).show();
	        	
	        } else {
	            // error occured
	        }
	    }
	}

}
