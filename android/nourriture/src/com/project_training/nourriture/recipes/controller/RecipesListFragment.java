package com.project_training.nourriture.recipes.controller;

import java.util.Arrays;
import java.util.List;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.project_training.nourriture.R;
import com.project_training.nourriture.adapter.RecipesAdapter;
import com.project_training.nourriture.api.ConstantsPathNourriture;
import com.project_training.nourriture.api.GsonRequest;
import com.project_training.nourriture.recipes.model.Recipe;
import com.project_training.nourriture.utils.UsefulFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipesListFragment extends Fragment {

	@Bind(R.id.progress_circular) ProgressBar	progressCircular;
	@Bind(R.id.recipes_not_found) TextView 		recipesNotFound;
	@Bind(R.id.lv_recipes) ListView				lvRecipes;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View	view = inflater.inflate(R.layout.fragment_recipes_list, container, false);
		ButterKnife.bind(this, view);

		if ( true == UsefulFunctions.checkConnection(getActivity()) ) {
			final Thread	checkUpdate = new Thread() {
		
				@Override
				public void run() {
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {

							GsonRequest<Recipe[]> getRecipes = 
							        new GsonRequest<Recipe[]>(Method.GET, ConstantsPathNourriture._LIST_RECIPES_, Recipe[].class,
							                new Listener<Recipe[]>() {
							                	
							                    @Override
							                    public void onResponse(Recipe[] response) {

							                        List<Recipe> recipes = Arrays.asList(response);

							                        if ( recipes.isEmpty() ) {
							                        	recipesNotFound.setVisibility(LinearLayout.VISIBLE);
							                        }
							                        
							                		final RecipesAdapter recipesAdapter = new RecipesAdapter(getActivity(), recipes);
													
													lvRecipes.setAdapter(recipesAdapter);
													lvRecipes.setOnItemClickListener(new OnItemClickListener() {
											
														@Override
														public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
											
															String _id = recipesAdapter.getListRecipes().get(position).getId();
															Toast.makeText(getActivity(), _id, Toast.LENGTH_LONG).show();
															
															Intent	intent = new Intent(getActivity(), RecipesDetailActivity.class);
															intent.putExtra("_id", _id);
															startActivity(intent);
														}
													});
											       progressCircular.setVisibility(LinearLayout.GONE);

							                    }

							                }, new ErrorListener() {
							                    @Override
							                    public void onErrorResponse(VolleyError error) {
							                    	Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
							                    	System.out.println("err " + error.getMessage());
							                    	progressCircular.setVisibility(LinearLayout.GONE);
							                    }
							                }
						                );
						
					       Volley.newRequestQueue(getActivity()).add(getRecipes);
						}
					});
				}
			};
			
			checkUpdate.start();
		} else {
			Toast.makeText(getActivity(), "Error : No internet connection !", Toast.LENGTH_LONG).show();
		}
		
		return view;
	}
}
