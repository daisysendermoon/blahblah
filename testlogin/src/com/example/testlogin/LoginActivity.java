package com.example.testlogin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;

/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************ In order for Google+ sign in
 * to work with your app, you must first go to:
 * https://developers.google.com/+/mobile
 * /android/getting-started#step_1_enable_the_google_api and follow the steps in
 * "Step 1" to create an OAuth 2.0 client for your package.
 */
public class LoginActivity extends Activity {


	    EditText etName, etPass;
	    String retStr;
	    Button bnLogin, bnCancel;
	    String url;
	    Map map = new HashMap<String, String>();
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_login);
	        etName = (EditText) findViewById(R.id.userEditText);
	        etPass = (EditText) findViewById(R.id.pwdEditText);
	       
	        System.out.println("etName = "+etName);
	        System.out.println("etPass = "+etPass);
	       
	        bnLogin = (Button) findViewById(R.id.bnLogin);
	        bnCancel = (Button) findViewById(R.id.bnCancel);
//	        bnCancel.setOnClickListener(new FinishListener(this));
	        bnLogin.setOnClickListener(new OnClickListener()
	        {
	            @Override
	            public void onClick(View v)
	            {
	                if (validate())
	                {
	                    if (loginPro())
	                    {
	                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
	                        startActivity(intent);
	                        finish();
	                    }
	                    else
	                    {
	                        DialogUtil.showDialog(LoginActivity.this, "User name or password wrong, please try again.", false);
	                    	Log.d("login fail", "fail");
	                    }
	                	
	                	
	                }
	            }
	        });
	    }

	    private boolean loginPro()
	    {
	        String username = etName.getText().toString();
	        String pwd = etPass.getText().toString();
	        System.out.println("username = "+username);
	        System.out.println("pwd = "+pwd);
	        JSONObject jsonObj;
	        try
	        {
	            query(username, pwd);
	            return true;
	        }
	        catch (Exception e)
	        {
	            DialogUtil.showDialog(this, "Server Error, please try again.", false);
	            e.printStackTrace();
	        }

	        return false;
	    }

	    private boolean validate()
	    {
	        String username = etName.getText().toString().trim();
	        if (username.equals(""))
	        {
	            DialogUtil.showDialog(this, "User name is needed.", false);
	            return false;
	        }
	        String pwd = etPass.getText().toString().trim();
	        if (pwd.equals(""))
	        {
	            DialogUtil.showDialog(this, "Password is needed", false);
	            return false;
	        }
	        return true;
	    }

	    private void query(String username, String password) throws Exception
	    {
	        
	        map.put("j_username", username);
	        map.put("j_password", password);
	        url = HttpUtil.BASE_URL + "j_security_check";
	        new HttpRequestTask().execute();
	        
	    }
	    
	    
	    private class HttpRequestTask extends AsyncTask<Void, Void, String> {
	        @Override
	        protected String doInBackground(Void... param) {
	            try {
	            	return HttpUtil.postRequest(url, map);
	            } catch (Exception e) {
	                Log.d("LoginActivity", e.getMessage(), e);
	            }
	            return null;

	        }

	        @Override
	        protected void onPostExecute(String ret) {
	            Log.d("success", "success");
	            retStr = ret;
	        }


	    }
	}
