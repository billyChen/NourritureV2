package com.project_training.nourriture.users.controller;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.project_training.nourriture.R;
import com.project_training.nourriture.api.App;
import com.project_training.nourriture.api.ConstantsPathNourriture;
import com.project_training.nourriture.api.GsonRequest;
import com.project_training.nourriture.api.users.ServerRequestUser;
import com.project_training.nourriture.api.users.UserSerializer;
import com.project_training.nourriture.users.model.User;
import com.project_training.nourriture.utils.FontsOverride;
import com.project_training.nourriture.utils.UsefulFunctions;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment {
	
	@Bind(R.id.btn_log_in) ImageButton			_btnLogIn;
	@Bind(R.id.edittext_username) EditText		_editTextUsername;
	@Bind(R.id.edittext_password) EditText		_editTextPassword;
	@Bind(R.id.btn_create_account) ImageButton 	_btnCreateAccount;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		FontsOverride.setDefaultFont(this.getActivity(), "DEFAULT", "fonts/calibri.ttf");
		View	view = inflater.inflate(R.layout.fragment_user_login, container, false);
		
		ButterKnife.bind(this, view);
		ButterKnife.setDebug(true);
		
		
		_btnLogIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				login();
			}
		});
		
		_btnCreateAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent	intent = new Intent(getActivity(), RegistrationActivity.class);
				startActivity(intent);
			}
		});
		
		return view;
	}
	
	public void		login() {
		
		if ( !validate() ) {
			onLoginFailed();
			return ;
		}
		
		_btnLogIn.setEnabled(false);
		
		final User	user = new User();
		user.setUsername(_editTextUsername.getText().toString());
		user.setPassword(_editTextPassword.getText().toString());
		
		final ProgressDialog	progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_ProgressDialog);
		progressDialog.setIndeterminate(true);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("Authenticating...");
		progressDialog.show();

		if ( true == UsefulFunctions.checkConnection(getActivity()) ) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					ServerRequestUser<User> userAuth;

					userAuth = new ServerRequestUser<User>(user.getUsername(), user.getPassword(), Method.POST, ConstantsPathNourriture._AUTHENTICATION_USER_, User.class,
					        new Listener<User>() {
					        	
					            @Override
					            public void onResponse(User response) {
					            	onLoginSuccess();
									VolleyLog.v("Response: %n %s", response);
									progressDialog.dismiss();
					            }

					        }, new ErrorListener() {
					            @Override
					            public void onErrorResponse(VolleyError error) {
					            	Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
					            }
					        }
					    );
				
					Volley.newRequestQueue(getActivity()).add(userAuth);
				}
			}, 3000);
		} else {
			Toast.makeText(getActivity(), "Error : No internet connection !", Toast.LENGTH_LONG).show();
			progressDialog.dismiss();
		}
	}
	
	public boolean	validate() {
		
		boolean		valid = true;
		
		String	username = _editTextUsername.getText().toString();
		String	password = _editTextPassword.getText().toString();
		
		if ( username.isEmpty() ) {
			_editTextUsername.setError("enter an valid username");
			valid = false;
		} else
			_editTextUsername.setError(null);
		
		if ( password.isEmpty() || password.length() < 4 || password.length() > 8 ) {
			_editTextPassword.setError("enter a password");
			valid = false;
		} else
			_editTextPassword.setError(null);
		
		return valid;
	}
	
	public void		onLoginSuccess() {
		Toast.makeText(getActivity(), "Authentication success", Toast.LENGTH_LONG).show();
		_btnLogIn.setEnabled(true);
	}
	
	public void		onLoginFailed() {
		Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_LONG).show();
		_btnLogIn.setEnabled(true);
	}
}
