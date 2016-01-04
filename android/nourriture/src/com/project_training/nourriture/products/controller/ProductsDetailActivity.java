package com.project_training.nourriture.products.controller;

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
import com.project_training.nourriture.products.model.Allergen;
import com.project_training.nourriture.products.model.Product;
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

public class ProductsDetailActivity extends AppCompatActivity {

	@Bind(R.id.tool_bar) Toolbar			toolBar;
	@Bind(R.id.img_product) ImageView 		imgProduct;
	@Bind(R.id.title_product) TextView		titleProduct;
	@Bind(R.id.price_product) TextView		priceProduct;
	@Bind(R.id.country_product) TextView	countryProduct;
	@Bind(R.id.description_product) TextView	descProduct;
	@Bind(R.id.ingredient_product) TextView		ingredientProduct;
	@Bind(R.id.calorie_product) TextView		calorieProduct;
	@Bind(R.id.allergen_product) TextView	allergenProduct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products_detail);
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
							
							GsonRequest<Product[]> getProducts = 
							        new GsonRequest<Product[]>(Method.GET, ConstantsPathNourriture._SHOW_PRODUCTS_BY_ID_ + _id, Product[].class,
							                new Listener<Product[]>() {
							                	
							                    @Override
							                    public void onResponse(Product[] response) {

													Product 	product = Arrays.asList(response).get(0);

													titleProduct.setText(product.getName());
													priceProduct.setText(Float.toString(product.getPrice()) + " RMB");
													countryProduct.setText(product.getCountry());
													descProduct.setText(product.getDescription());
													
													StringBuilder	sbIngredients = new StringBuilder();

													for ( Ingredient ingredient : product.getIngredients() ) {
														String	ingredientStr = new String("- " + ingredient.getName() + "\n");
														sbIngredients.append(ingredientStr);
													}
													
													ingredientProduct.setText(sbIngredients.toString());
													
													calorieProduct.setText("Calories : " + Float.toString(product.getCalories()));
													
													StringBuilder	sbAllergens = new StringBuilder();

													for ( Allergen allergen : product.getAllergens() ) {
														String	sbAllergen = new String("- " + allergen.getName()+  "\n");
														sbAllergens.append(sbAllergen);
													}
													
													allergenProduct.setText("Allergens : " + sbAllergens);
							                    }
							                    
							                }, new ErrorListener() {
							                    @Override
							                    public void onErrorResponse(VolleyError error) {
							                    	System.out.println("lolerror" + error.getMessage());

							                    	Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
							                }
						                });

					       Volley.newRequestQueue(getApplicationContext()).add(getProducts);
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
