package com.project_training.nourriture.products.controller;

import java.util.Arrays;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.project_training.nourriture.R;
import com.project_training.nourriture.adapter.ProductsAdapter;
import com.project_training.nourriture.api.ConstantsPathNourriture;
import com.project_training.nourriture.api.GsonRequest;
import com.project_training.nourriture.products.controller.ProductsDetailActivity;
import com.project_training.nourriture.products.model.Product;
import com.project_training.nourriture.utils.UsefulFunctions;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductsListFragment extends Fragment {

	@Bind(R.id.progress_circular) ProgressBar	progressCircular;
	@Bind(R.id.products_not_found) TextView 	productsNotFound;
	@Bind(R.id.lv_products) ListView			lvProducts;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View	view = inflater.inflate(R.layout.fragment_products_list, container, false);
		ButterKnife.bind(this, view);

		if ( true == UsefulFunctions.checkConnection(getActivity()) ) {
			final Thread	checkUpdate = new Thread() {
		
				@Override
				public void run() {
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {

							GsonRequest<Product[]> getProducts = 
							        new GsonRequest<Product[]>(Method.GET, ConstantsPathNourriture._LIST_PRODUCTS_, Product[].class,
							                new Listener<Product[]>() {
							                	
							                    @Override
							                    public void onResponse(Product[] response) {

							                        List<Product> products = Arrays.asList(response);

							                        if ( products.isEmpty() ) {
							                        	productsNotFound.setVisibility(LinearLayout.VISIBLE);
							                        }
							                        
							                		final ProductsAdapter productsAdapter = new ProductsAdapter(getActivity(), products);
													
													lvProducts.setAdapter(productsAdapter);
													lvProducts.setOnItemClickListener(new OnItemClickListener() {
											
														@Override
														public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
											
															String _id = productsAdapter.getListProducts().get(position).getId();
															Toast.makeText(getActivity(), _id, Toast.LENGTH_LONG).show();
															
															Intent	intent = new Intent(getActivity(), ProductsDetailActivity.class);
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
						
					       Volley.newRequestQueue(getActivity()).add(getProducts);
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
