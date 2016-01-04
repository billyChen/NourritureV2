package com.project_training.nourriture.ingredients.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.project_training.nourriture.R;
import com.project_training.nourriture.api.ConstantsPathNourriture;
import com.project_training.nourriture.api.GsonRequest;
import com.project_training.nourriture.ingredients.model.Ingredient;
import com.project_training.nourriture.recipes.model.Recipe;
import com.project_training.nourriture.utils.UsefulFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;

public class IngredientsDetailActivity extends AppCompatActivity {

	@Bind(R.id.tool_bar) Toolbar			toolBar;
	@Bind(R.id.title_ingredient) TextView	ingredientTitle;
	@Bind(R.id.desc_ingredient) TextView	ingredientDesc;
	@Bind(R.id.gv_ingredient_dietary) GridView	ingredientDietaryGridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredients_detail);
		ButterKnife.bind(this);

		setSupportActionBar(toolBar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		toolBar.setNavigationOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		if ( true == UsefulFunctions.checkConnection(this) ) {
			final Thread	checkUpdate = new Thread() {
		
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Intent	intent = getIntent();
							String	_id = intent.getStringExtra("_id");
							
							GsonRequest<Ingredient[]> getIngredients = 
							        new GsonRequest<Ingredient[]>(Method.GET, ConstantsPathNourriture._SHOW_INGREDIENTS_BY_ID_ + _id, Ingredient[].class,
							                new Listener<Ingredient[]>() {
							                	
							                    @Override
							                    public void onResponse(Ingredient[] response) {

													Ingredient 	ingredient = Arrays.asList(response).get(0);
											
													List<String>	dietaryNameList = new ArrayList<String>();
													dietaryNameList.add("Calorie");
													dietaryNameList.add("Protein");
													dietaryNameList.add("Saturated fat");
													dietaryNameList.add("Carbohydrate");
													dietaryNameList.add("Starch");
													dietaryNameList.add("Total sugar");
													dietaryNameList.add("Dietary fiber");
													dietaryNameList.add("Salt");
													dietaryNameList.add("Calcium");
													dietaryNameList.add("Magnesium");
													dietaryNameList.add("Phosphorus");
													dietaryNameList.add("Potassium");
													dietaryNameList.add("Sodium");
													dietaryNameList.add("E");
													dietaryNameList.add("B1");
													dietaryNameList.add("B2");
													dietaryNameList.add("B6");
													dietaryNameList.add("B9");

													List<Float>		dietaryValueList = new ArrayList<Float>();
													dietaryValueList.add(ingredient.getCalories());
													dietaryValueList.add(ingredient.getProtein());
													dietaryValueList.add(ingredient.getSaturatedFat());
													dietaryValueList.add(ingredient.getCarbohydrate());
													dietaryValueList.add(ingredient.getStarch());
													dietaryValueList.add(ingredient.getTotalSugar());
													dietaryValueList.add(ingredient.getDietaryFiber());
													dietaryValueList.add(ingredient.getSalt());
													dietaryValueList.add(ingredient.getCalcium());
													dietaryValueList.add(ingredient.getMagnesium());
													dietaryValueList.add(ingredient.getPhosphorus());
													dietaryValueList.add(ingredient.getPotassium());
													dietaryValueList.add(ingredient.getSodium());
													dietaryValueList.add(ingredient.getE());
													dietaryValueList.add(ingredient.getB1());
													dietaryValueList.add(ingredient.getB2());
													dietaryValueList.add(ingredient.getB6());
													dietaryValueList.add(ingredient.getB9());
													
													ingredientDietaryGridview.setAdapter(new IngredientDetailAdapter(dietaryNameList, dietaryValueList));
							                    }
							                    
							                }, new ErrorListener() {
							                    @Override
							                    public void onErrorResponse(VolleyError error) {
							                    	System.out.println("lolerror" + error.getMessage());

							                    	Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
							                }
						                });

					       Volley.newRequestQueue(getApplicationContext()).add(getIngredients);
						}
					});
				}
			};
			
			checkUpdate.start();
		} else {
			Toast.makeText(this, "Error : No internet connection !", Toast.LENGTH_LONG).show();
		}
	}
	
	 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); 
        if (id == R.id.action_search) {
            return true;
        }
 
        return super.onOptionsItemSelected(item);
    }
    
    class IngredientDetailAdapter extends BaseAdapter {

    	private List<String>	dietaryNameList;
    	private List<Float>		dietaryValueList;
    	
    	
    	public IngredientDetailAdapter(List<String> dietaryNameList, List<Float> dietaryValueList) {
    		this.dietaryNameList = dietaryNameList;
    		this.dietaryValueList = dietaryValueList;
    	}

    	@Override
    	public int getCount() {
    		return dietaryNameList.size();
    	}

    	@Override
    	public Object getItem(int pos) {
    		return null;
    	}

    	@Override
    	public long getItemId(int position) {
    		return position;
    	}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		
    		if ( convertView == null ) {
    			LayoutInflater	inflater = getLayoutInflater();
        		convertView = inflater.inflate(R.layout.ingredient_detail_row, parent, false);
        	}
    		String 	name = dietaryNameList.get(position);
    		float 	value = dietaryValueList.get(position);
    	
    		TextView dietaryName = (TextView) findViewById(R.id.dietary_name);
    		dietaryName.setText(name);
    		
    		TextView dietaryValue = (TextView) findViewById(R.id.dietary_value);
    		dietaryValue.setText(Float.toString(value));
    		
    		return convertView;
    	}

    }

}
