package com.project_training.nourriture.recipes.controller;

import java.util.Arrays;
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipesDetailActivity extends AppCompatActivity {

	@Bind(R.id.tool_bar) Toolbar			toolBar;
	@Bind(R.id.img_recipe) ImageView 		imgRecipe;
	@Bind(R.id.recipe_name) TextView		recipeName;
	@Bind(R.id.difficulty_recipe) TextView	difficultyRecipe;
	@Bind(R.id.cost_recipe) TextView		costRecipe;
	@Bind(R.id.type_recipe) TextView		typeRecipe;
	@Bind(R.id.time_recipe) TextView		timeRecipe;
	@Bind(R.id.list_ingredients) TextView	ingredientsList;
	@Bind(R.id.recipe_description) TextView recipeDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_detail);
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
							
							GsonRequest<Recipe[]> getRecipes = 
							        new GsonRequest<Recipe[]>(Method.GET, ConstantsPathNourriture._SHOW_RECIPES_BY_ID_ + _id, Recipe[].class,
							                new Listener<Recipe[]>() {
							                	
							                    @Override
							                    public void onResponse(Recipe[] response) {

													Recipe 	recipe = Arrays.asList(response).get(0);

													recipeName.setText(recipe.getName());
													difficultyRecipe.setText(recipe.getDifficulty() + "/5");
													costRecipe.setText(recipe.getCost() + " RMB");
													typeRecipe.setText(recipe.getType());
													timeRecipe.setText(recipe.getTime());
													
													StringBuilder	sbIngredients = new StringBuilder();

													for ( Ingredient ingredient : recipe.getIngredients() ) {
														String	ingredientStr = new String("- " + ingredient.getQuantity() + " " + ingredient.getName() + "\n");
														sbIngredients.append(ingredientStr);
													}
													ingredientsList.setText(sbIngredients.toString());
													
													StringBuilder	sbDescription = new StringBuilder();
													
													sbDescription.append(new String("Country : " + recipe.getCountry() + "\n"));
													sbDescription.append(new String("Calories : " + recipe.getCalories() +"\n"));
													sbDescription.append(new String(recipe.getDescription()));
													
													recipeDescription.setText(sbDescription.toString());
							                    }
							                    
							                }, new ErrorListener() {
							                    @Override
							                    public void onErrorResponse(VolleyError error) {
							                    	System.out.println("lolerror" + error.getMessage());

							                    	Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
							                }
						                });

					       Volley.newRequestQueue(getApplicationContext()).add(getRecipes);
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

}
