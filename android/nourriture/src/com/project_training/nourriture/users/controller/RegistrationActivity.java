package com.project_training.nourriture.users.controller;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project_training.nourriture.R;
import com.project_training.nourriture.api.ConstantsPathNourriture;
import com.project_training.nourriture.api.users.UserSerializer;
import com.project_training.nourriture.users.model.User;
import com.project_training.nourriture.utils.FontsOverride;
import com.project_training.nourriture.utils.UsefulFunctions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity {

	@Bind(R.id.tool_bar) Toolbar	_toolBar;
	@Bind(R.id.button_create_account) ImageButton	_imageBtnCreateAccount;
	@Bind(R.id.edittext_username) EditText	_editTextUsername;
	@Bind(R.id.edittext_password) EditText	_editTextPassword;
	@Bind(R.id.edittext_email) EditText		_editTextEmail;
	@Bind(R.id.radiogroup_gender) RadioGroup _radioGroupBender;
	@Bind(R.id.edittext_firstname) EditText	_editTextFirstname;
	@Bind(R.id.edittext_lastname) EditText	_editTextLastname;
	@Bind(R.id.edittext_address) EditText	_editTextAddress;
	@Bind(R.id.edittext_postalcode) EditText _editTextPostalCode;
	@Bind(R.id.edittext_city) EditText		_editTextCity;
	@Bind(R.id.edittext_country) EditText	_editTextCountry;
	@Bind(R.id.edittext_birthday) EditText	_editTextBirthday;
	
	private StringBuilder	errorMsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/calibri.ttf");
		setContentView(R.layout.activity_registration);
		ButterKnife.bind(this);
		
		setSupportActionBar(_toolBar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		_toolBar.setNavigationOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		ImageButton	_imageBtnCreateAccount = (ImageButton) findViewById(R.id.button_create_account);
		_imageBtnCreateAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
					signup();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
	
		//noinspection SimplifiableIfStatement
	 	if (id == R.id.action_search) {
	 		return true;
	 	}

	 	return super.onOptionsItemSelected(item);
 	}
	
	public void	signup() {

		if ( !validate() ) {
			onSignupFailed();
			return;
		}
		
		_imageBtnCreateAccount.setEnabled(false);

		GsonBuilder	gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(User.class, new UserSerializer());
		gsonBuilder.setPrettyPrinting();
		
		RadioButton	radiobuttonGender = (RadioButton) findViewById(_radioGroupBender.getCheckedRadioButtonId());
		
		User	user = new User();
		user.setUsername(_editTextUsername.getText().toString());
		user.setPassword(_editTextPassword.getText().toString());
		user.setEmail(_editTextEmail.getText().toString());
		user.setGender(radiobuttonGender.getText().toString());
		user.setFirstname(_editTextFirstname.getText().toString());
		user.setLastname(_editTextLastname.getText().toString());
		user.setAddress(_editTextAddress.getText().toString());
		user.setPostalCode(_editTextPostalCode.getText().toString());
		user.setCity(_editTextCity.getText().toString());
		user.setCountry(_editTextCountry.getText().toString());
		user.setBirthday(_editTextBirthday.getText().toString());
		
		Gson	gson = gsonBuilder.create();
		final String	userJson = gson.toJson(user);

		final ProgressDialog	progressDialog = new ProgressDialog(RegistrationActivity.this, R.style.AppTheme_ProgressDialog);
		progressDialog.setIndeterminate(true);
		progressDialog.setMessage("Please wait...");
		progressDialog.show();

		if ( true == UsefulFunctions.checkConnection(getApplicationContext()) ) {
			new Handler().postDelayed(new Runnable() {
	
				@Override
				public void run() {
					JsonObjectRequest addUserRequest;

					try {
						addUserRequest = new JsonObjectRequest(Method.POST, ConstantsPathNourriture._ADD_USERS_, new JSONObject(userJson),
						        new Listener<JSONObject>() {
						        	
						            @Override
						            public void onResponse(JSONObject response) {
						            	try {
						            		onSignupSuccess();
						            		VolleyLog.v("Response: %n %s", response.toString(4));
											progressDialog.dismiss();
						            	} catch (JSONException e) {
						            		Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
						            	}
						            }
	
						        }, new ErrorListener() {
						            @Override
						            public void onErrorResponse(VolleyError error) {
						            	Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
						            }
						        }
						    );
						
				       Volley.newRequestQueue(getApplicationContext()).add(addUserRequest);
					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
				
			}, 3000);
		} else
			Toast.makeText(getApplicationContext(), "Error : No internet connection !", Toast.LENGTH_LONG).show();
	}
	
	public	boolean	validate() {

		boolean		valid = true;
		
		String	username = _editTextUsername.getText().toString();
		String	password = _editTextPassword.getText().toString();
		String	email = _editTextAddress.getText().toString();
		String	firstname = _editTextFirstname.getText().toString();
		String	lastname = _editTextLastname.getText().toString();
		String	address = _editTextAddress.getText().toString();
		String	postal_code = _editTextPostalCode.getText().toString();
		String	city = _editTextCity.getText().toString();
		String	country = _editTextCountry.getText().toString();
		String	birthday = _editTextBirthday.getText().toString();
		
		errorMsg = new StringBuilder();
		
		if ( username.isEmpty() || username.length() < 3 ) {
			_editTextUsername.setError("at least 3 characters");
			errorMsg.append("- Username\n");
			valid = false;
		} else
			_editTextUsername.setError(null);
		
		if ( password.isEmpty() || password.length() < 4 || password.length() > 8 ) {
			_editTextPassword.setError("between 4 and 8 alphanumeric characters");
			errorMsg.append("- Password\n");
			valid = false;
		} else
			_editTextPassword.setError(null);
		
		if ( email.isEmpty() ) {
			_editTextEmail.setError("enter a valid email");
			errorMsg.append("- Email\n");
			valid = false;
		} else
			_editTextEmail.setError(null);
		
		if ( _radioGroupBender.getCheckedRadioButtonId() == -1 ) {
			errorMsg.append("- Gender\n");
			valid = false;
		}
		
		if ( firstname.isEmpty() ) {
			_editTextFirstname.setError("enter your firstname");
			errorMsg.append("- Firstname\n");
			valid = false;
		} else
			_editTextFirstname.setError(null);
		
		if ( lastname.isEmpty() ) {
			_editTextLastname.setError("enter your lastname");
			errorMsg.append("- Lastname\n");
			valid = false;
		} else
			_editTextLastname.setError(null);
		
		if ( address.isEmpty() ) {
			_editTextAddress.setError("enter your address");
			valid = false;
		} else
			_editTextAddress.setError(null);
		
		if ( postal_code.isEmpty() ) {
			_editTextPostalCode.setError("enter your postal code");
			errorMsg.append("- Postal code\n");
			valid = false;
		} else
			_editTextPostalCode.setError(null);
		
		if ( city.isEmpty() ) {
			_editTextCity.setError("enter your city");
			errorMsg.append("- City\n");
			valid = false;
		} else
			_editTextCity.setError(null);
		
		if ( country.isEmpty() ) {
			_editTextCountry.setError("enter your country");
			errorMsg.append("- Country\n");
			valid = false;
		} else
			_editTextCountry.setError(null);
		
		if ( birthday.isEmpty() ) {
			_editTextBirthday.setError("enter your birth day");
			errorMsg.append("- Birthday\n");
			valid = false;
		} else
			_editTextBirthday.setError(null);

		return valid;
	}
	
	public void	onSignupFailed() {
		Toast.makeText(this, "Cannot create an account ! The following fields must be validate : \n" + errorMsg.toString(), Toast.LENGTH_LONG).show();
		errorMsg = new StringBuilder();
		_imageBtnCreateAccount.setEnabled(true);
	}
	
	public void	onSignupSuccess() {
		_imageBtnCreateAccount.setEnabled(true);
		setResult(RESULT_OK, null);
		Toast.makeText(getApplicationContext(), "Account created ! You can connect now", Toast.LENGTH_LONG).show();
		finish();
	}
	
}
