package com.project_training.nourriture.search.controller;

import java.util.ArrayList;
import com.project_training.nourriture.R;
import com.project_training.nourriture.adapter.RecipesAdapter;
import com.project_training.nourriture.recipes.controller.RecipesDetailActivity;
import com.project_training.nourriture.recipes.model.Recipe;
import com.project_training.nourriture.utils.FontsOverride;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity {

	@Bind(R.id.tool_bar) Toolbar		toolBar;
	@Bind(R.id.lv_recipes) ListView 	recipesList;
	@Bind(R.id.lv_products) ListView 	productsList;
	@Bind(R.id.lv_ingredients) ListView ingredientsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/calibri.ttf");
		setContentView(R.layout.activity_search_result);
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
		
		final ArrayList<Recipe> recipes = getIntent().getParcelableArrayListExtra("recipesList");
		
		for ( Recipe r : recipes ) {
			System.out.println("r2 " + r);
		}
		final RecipesAdapter recipesAdapter = new RecipesAdapter(this, recipes);
		recipesList.setAdapter(recipesAdapter);
		recipesList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String _id = recipes.get(position).getId();
				Toast.makeText(getApplicationContext(), _id, Toast.LENGTH_LONG).show();
				
				Intent	intent = new Intent(getApplicationContext(), RecipesDetailActivity.class);
				intent.putExtra("_id", _id);
				startActivity(intent);
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
}
