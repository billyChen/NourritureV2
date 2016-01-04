package com.project_training.nourriture.ingredients.controller;

import java.util.Arrays;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.project_training.nourriture.R;
import com.project_training.nourriture.adapter.IngredientsAdapter;
import com.project_training.nourriture.adapter.RecipesAdapter;
import com.project_training.nourriture.api.ConstantsPathNourriture;
import com.project_training.nourriture.api.GsonRequest;
import com.project_training.nourriture.ingredients.model.Ingredient;
import com.project_training.nourriture.recipes.controller.RecipesDetailActivity;
import com.project_training.nourriture.recipes.model.Recipe;
import com.project_training.nourriture.utils.UsefulFunctions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import butterknife.Bind;
import butterknife.ButterKnife;

public class IngredientsListFragment extends Fragment {

	@Bind(R.id.progress_circular) ProgressBar	progressCircular;
	@Bind(R.id.ingredients_not_found) TextView 	ingredientsNotFound;
	@Bind(R.id.refresh_btn) ImageButton			refreshBtn;
	@Bind(R.id.lv_ingredients) ListView			lvIngredients;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View	view = inflater.inflate(R.layout.fragment_ingredients_list, container, false);
		ButterKnife.bind(this, view);

		if ( true == UsefulFunctions.checkConnection(getActivity()) ) {
			final Thread	checkUpdate = new Thread() {
		
				@Override
				public void run() {
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {

							GsonRequest<Ingredient[]> getIngredients = 
							        new GsonRequest<Ingredient[]>(Method.GET, ConstantsPathNourriture._LIST_INGREDIENTS_, Ingredient[].class,
							                new Listener<Ingredient[]>() {
							                	
							                    @Override
							                    public void onResponse(Ingredient[] response) {

							                        List<Ingredient> ingredients = Arrays.asList(response);

							                        if ( ingredients.isEmpty() ) {
							                        	ingredientsNotFound.setVisibility(LinearLayout.VISIBLE);
							                        }
							                        
							                		final IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getActivity(), ingredients);
													
													lvIngredients.setAdapter(ingredientsAdapter);
													lvIngredients.setOnItemClickListener(new OnItemClickListener() {
											
														@Override
														public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								
															String _id = ingredientsAdapter.getListIngredients().get(position).getId();
															Toast.makeText(getActivity(), _id, Toast.LENGTH_LONG).show();
																														
															Intent	intent = new Intent(getActivity(), IngredientsDetailActivity.class);
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
							                    	progressCircular.setVisibility(LinearLayout.GONE);
							                    	ingredientsNotFound.setVisibility(LinearLayout.VISIBLE);
							                    }
							                }
						                );
						
					       Volley.newRequestQueue(getActivity()).add(getIngredients);
						}
					});
				}
			};
			
			checkUpdate.start();
		} else {
			Toast.makeText(getActivity(), "Error : No internet connection !", Toast.LENGTH_LONG).show();
			
        	progressCircular.setVisibility(LinearLayout.GONE);
        	refreshBtn.setVisibility(LinearLayout.VISIBLE);
        	refreshBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					refreshBtn.setVisibility(LinearLayout.GONE);
					Fragment f = getFragmentManager().findFragmentByTag("IngredientsListFragment");
					FragmentTransaction	ft = getActivity().getFragmentManager().beginTransaction();
					ft.detach(f).attach(f).commit();	
				}
			});
		}
		
		return view;
	}
}
