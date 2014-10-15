package com.example.testlogin;


import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private Button btn;
	private Button wsBtn;
	TextView grt; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        btn=(Button)findViewById(R.id.btnNextView);
        wsBtn = (Button)findViewById(R.id.btnCallWebservice);
        grt = (TextView) findViewById(R.id.textViewWS);
        btn.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                Intent itent=new Intent();
                itent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(itent);
                MainActivity.this.finish();
            }
        });
        
        wsBtn.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
            	
            	new HttpRequestTask().execute(); 
            }
        });
	}
	 
	private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://192.168.0..110:8080/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Log.d("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
        	grt.setText(greeting.getId() + " " + greeting.getContent());
        }

    }
}
