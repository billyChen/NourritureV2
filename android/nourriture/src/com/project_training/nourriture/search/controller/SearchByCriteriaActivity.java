package com.project_training.nourriture.search.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.project_training.nourriture.R;
import com.project_training.nourriture.api.ConstantsPathNourriture;
import com.project_training.nourriture.api.GsonRequest;
import com.project_training.nourriture.api.search.ServerRequestSearch;
import com.project_training.nourriture.ingredients.model.Ingredient;
import com.project_training.nourriture.recipes.model.Recipe;
import com.project_training.nourriture.utils.UsefulFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class SearchByCriteriaActivity extends AppCompatActivity {

	@Bind(R.id.tool_bar) Toolbar		toolBar;
	
	@Bind(R.id.progress_circular) ProgressBar	progressCircular;
	@Bind(R.id.search_layout) LinearLayout searchLayout;
	
	@Bind(R.id.input_search) EditText	inputSearch;
	@Bind(R.id.dish_type) Spinner		dishType;
	@Bind(R.id.country_list) Spinner	countrySpinner;
	@Bind(R.id.ingredient_list) Spinner	ingredientSpinner;
	
	@Bind(R.id.calories_value) TextView	calorieValue;
	@Bind(R.id.calories_volume) SeekBar calorieVolume;
	@Bind(R.id.cost_value) TextView		costValue;
	@Bind(R.id.cost_volume) SeekBar		costVolume;
	@Bind(R.id.difficulty_value) TextView difficultyValue;
	@Bind(R.id.difficulty_volume) SeekBar difficultyVolume;
	@Bind(R.id.btn_search) ImageButton	btnSearch;
	
	@Bind(R.id.progress_circular_searching) ProgressBar progressSearching;
	
	private List<String>	dishesNameList;
	private List<String>	countriesNameList;
	private List<String>	ingredientsNameList;

	
	@Override
	protected void	onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_criteria);
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

    		dishesNameList = new ArrayList<String>();
    		dishesNameList.add(new String("indifferent"));
    		dishesNameList.add(new String("dessert"));

    		ArrayAdapter<String>	dishAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, dishesNameList);
    		dishAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
    		dishType.setAdapter(dishAdapter);
    		
    		countriesNameList = new ArrayList<String>();
    		countriesNameList.add(new String("france"));
    		
    		ArrayAdapter<String>	countryAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, countriesNameList);
    		countryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
    		countrySpinner.setAdapter(countryAdapter);
    		
    		calorieVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    			
    			@Override
    			public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
    				calorieValue.setText(String.valueOf(progress));
    			}
    			
    			@Override
    			public void onStopTrackingTouch(SeekBar arg0) {}
    			
    			@Override
    			public void onStartTrackingTouch(SeekBar arg0) {}
    		});

    		difficultyVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    			
    			@Override
    			public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
    				difficultyValue.setText(String.valueOf(progress));
    			}
    			
    			@Override
    			public void onStopTrackingTouch(SeekBar arg0) {}
    			
    			@Override
    			public void onStartTrackingTouch(SeekBar arg0) {}
    		});

    		costVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    			
    			@Override
    			public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
    				costValue.setText(String.valueOf(progress));
    			}
    			
    			@Override
    			public void onStopTrackingTouch(SeekBar arg0) {}
    			
    			@Override
    			public void onStartTrackingTouch(SeekBar arg0) {}
    		});
    		
    		
			final Thread	checkUpdate = new Thread() {
		
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
	
							GsonRequest<Ingredient[]> getIngredients = 
							        new GsonRequest<Ingredient[]>(Method.GET, ConstantsPathNourriture._LIST_INGREDIENTS_, Ingredient[].class,
							                new Listener<Ingredient[]>() {
							                	
							                    @Override
							                    public void onResponse(Ingredient[] response) {
	
							                        List<Ingredient> ingredients = Arrays.asList(response);
							                        
							                        ingredientsNameList = new ArrayList<String>();
							                        
							                        for ( Ingredient i : ingredients )
							                        	ingredientsNameList.add(i.getName());
							                        
							                        ArrayAdapter<String>	ingredientAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, ingredientsNameList);
							                		ingredientAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
							                		ingredientSpinner.setAdapter(ingredientAdapter);
							                		
							                		btnSearch.setOnClickListener(new OnClickListener() {
							                			
							                			@Override
							                			public void onClick(View view) {
	

									                		final Recipe recipe = new Recipe();
									                		
									                		recipe.setName(inputSearch.getText().toString());
			//						                		search.setType(dishType.getTransitionName());
									                		recipe.setCountry(countrySpinner.getSelectedItem().toString());
									                		recipe.setCost(costVolume.getProgress());
									                		recipe.setCalories(calorieVolume.getProgress());
									                		
									                		List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
									                		ingredientsList.add(new Ingredient(ingredientSpinner.getSelectedItem().toString()));
									                		recipe.setListIngredients(ingredientsList);
									                		
//									                		search.setDifficulty(difficultyVolume.getProgress());

									                		progressSearching.setVisibility(LinearLayout.VISIBLE);
									                		btnSearch.setClickable(false);
							                				if ( true == UsefulFunctions.checkConnection(getApplicationContext()) ) {
							                					new Handler().postDelayed(new Runnable() {
	
							                						@Override
							                						public void run()  {
							                							
							                							ServerRequestSearch<Recipe[]> searchRequest;
	
							                							searchRequest = new ServerRequestSearch<Recipe[]>(recipe.getName(), 
							                																		recipe.getCountry(),
							                																		0,
							                																		recipe.getCost(),
							                																		0,
							                																		recipe.getCalories(),
							                																		recipe.getIngredients(),
//																                									search.getType(),
//																                									search.getCost(),
//																                									search.getDifficulty(),
							                																		Method.POST, 
							                																		ConstantsPathNourriture._ADVANCED_SEARCH_, 
							                																		Recipe[].class,
							                							        new Listener<Recipe[]>() {
							                							        	
							                							            @Override
							                							            public void onResponse(Recipe[] response) {
							                											System.out.println("RecipeResponse : " + response);
							                											
							                											ArrayList<Recipe> recipesList = new ArrayList<Recipe>(Arrays.asList(response));
//																						
							                											for ( Recipe r : recipesList ) {
							                												System.out.println("r " + r);							                											}
							                											Intent intent = new Intent(SearchByCriteriaActivity.this, SearchResultActivity.class);
							                											intent.putParcelableArrayListExtra("recipesList", recipesList);
							                											
							                											startActivity(intent);
							                							            }
	
							                							        }, new ErrorListener() {
							                							            @Override
							                							            public void onErrorResponse(VolleyError error) {
							                							        		System.out.println("RecipeResponse : " + error.getMessage());
							                									            	Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
							                							            }
							                							        }
							                							    );
							                							btnSearch.setClickable(false);
							                							Volley.newRequestQueue(getApplicationContext()).add(searchRequest);
							                						}
							                					}, 3000);
							                				}
							                			}
							                		});
							                		
							                		progressSearching.setVisibility(LinearLayout.GONE);
							                		progressCircular.setVisibility(LinearLayout.GONE);
							                		searchLayout.setVisibility(LinearLayout.VISIBLE);
							                    }
	
							                }, new ErrorListener() {
							                    @Override
							                    public void onErrorResponse(VolleyError error) {
							                       	
							                    	progressCircular.setVisibility(LinearLayout.GONE);
							                    	Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
							                    	
							                    }
							                }
						                );
						
					       Volley.newRequestQueue(getApplicationContext()).add(getIngredients);
						}
					});
				}
			};
			checkUpdate.start();
		} else {
			Toast.makeText(this, "Error : No internet connection !", Toast.LENGTH_LONG).show();
        	progressCircular.setVisibility(LinearLayout.GONE);
		}
	}
	
	@Override
	public boolean	onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();		
		inflater.inflate(R.menu.main, menu);
		return true;
	}
}
