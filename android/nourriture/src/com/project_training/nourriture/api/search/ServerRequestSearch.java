package com.project_training.nourriture.api.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.project_training.nourriture.api.GsonRequest;
import com.project_training.nourriture.ingredients.model.Ingredient;

public class ServerRequestSearch<T> extends GsonRequest<T> {

	private String 	name;
//	private String	type;
	private String	country;
	private long	cost1;
	private long	cost2;
	private long	calories1;
	private long	calories2;
	private List<Ingredient>	ingredients;
	//private	long	difficulty;
	
	public ServerRequestSearch(String name,
					String country,
					long cost1,
					long cost2,
					long calories1,
					long calories2,
					List<Ingredient> ingredients,
//					long difficulty,
					String url,
					Class<T> clazz, 
					Listener<T> listener, 
					ErrorListener errorListener) {
		super(url, clazz, listener, errorListener);
		this.name = name;
		this.country = country;
		this.cost1 = cost1;
		this.cost2 = cost2;
		this.calories1 = calories1;
		this.calories2 = calories2;
		this.ingredients = ingredients;
	}
	
	public ServerRequestSearch(String name,
					String country,
					long cost1,
					long cost2,
					long calories1,
					long calories2,
					List<Ingredient> ingredients,
					int method,
					String url, 
					Class<T> clazz, 
					Listener<T> listener, 
					ErrorListener errorListener) {
		super(method, url, clazz, listener, errorListener);
		this.name = name;
		this.country = country;
		this.cost1 = cost1;
		this.cost2 = cost2;
		this.calories1 = calories1;
		this.calories2 = calories2;
		this.ingredients = ingredients;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return searchHeader(name,
				country,
				cost1,
				cost2,
				calories1,
				calories2,
				ingredients);
	}

	@Override
	public Map<String, String> getParams() throws AuthFailureError {
	    return searchHeader(name,
							country,
							cost1,
							cost2,
							calories1,
							calories2,
							ingredients);
	}

	Map<String, String> searchHeader(String name,
									String country,
									long cost1,
									long cost2,
									long calories1,
									long calories2,
									List<Ingredient> ingredients) {
		
	    Map<String, String> headerMap = new HashMap<String, String>();
	    
	    headerMap.put("name", name);
	    headerMap.put("country", country);
	    headerMap.put("cost1", Long.toString(cost1));
	    headerMap.put("cost2", Long.toString(cost2));
	    headerMap.put("calories1", Long.toString(calories1));
	    headerMap.put("calories2", Long.toString(calories2));
	    headerMap.put("ingredients", ingredients.get(0).getName());
	    
	    return headerMap;
	}
}
