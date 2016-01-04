package com.project_training.nourriture.adapter;

import java.util.List;

import com.project_training.nourriture.R;
import com.project_training.nourriture.products.model.Product;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductsAdapter extends ArrayAdapter<List<Product>> {

	private Activity 		context;
	private List<Product>	listProducts;

	static class ViewHolder {
		
		@Bind(R.id.img_product) ImageView	imgProduct;
		@Bind(R.id.title_product) TextView 	titleProduct;
		@Bind(R.id.price_product) TextView	priceProduct;
		@Bind(R.id.description_product) TextView descriptionProduct;
		@Bind(R.id.type_product) TextView 	typeProduct;
		
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
	
	public ProductsAdapter(Activity context, List<Product> listProducts) {
		super(context, -1);
		this.context = context;
		this.listProducts = listProducts;
	}
	
	public List<Product> getListProducts() {
		return listProducts;
	}
	
	@Override
	public int getCount() {
		return listProducts.size();
	}

	@Override
	public long getItemId(int position) {
		return listProducts.get(position).hashCode();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if ( convertView == null ) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.listview_product_row, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		Product product = listProducts.get(position);
		
//		holder.imgProduct.setImageResource(product.getImage());
		holder.titleProduct.setText(product.getName());
		holder.priceProduct.setText(Float.toString(product.getPrice()) + " RMB");
		holder.descriptionProduct.setText(product.getDescription());
//		holder.typeProduct.setText(prod);
		

		return convertView;
	}
}
