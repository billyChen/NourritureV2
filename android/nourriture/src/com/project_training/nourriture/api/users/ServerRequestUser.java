package com.project_training.nourriture.api.users;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.project_training.nourriture.api.GsonRequest;

import android.util.Base64;

public class ServerRequestUser<T> extends GsonRequest<T> {

	private String	username;
	private String	password;
	
	public ServerRequestUser(String username,
							String password,
							String url, 
							Class<T> clazz, 
							Listener<T> listener, 
							ErrorListener errorListener) {
		super(url, clazz, listener, errorListener);
		this.username = username;
		this.password = password;
	}
	
	public ServerRequestUser(String username,
							String password,
							int method,
							String url, 
							Class<T> clazz, 
							Listener<T> listener, 
							ErrorListener errorListener) {
		super(method, url, clazz, listener, errorListener);
		this.username = username;
		this.password = password;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
	    return createBasicAuthHeader(username, password);
	}

	@Override
	public Map<String, String> getParams() throws AuthFailureError {
	    return createBasicAuthHeader(username, password);
	}

	Map<String, String> createBasicAuthHeader(String username, String password) {
	    
		Map<String, String> headerMap = new HashMap<String, String>();
	    
		headerMap.put("username", username);
	    headerMap.put("password", password);
	    
	    return headerMap;
	}

}
