package com.project_training.nourriture.users.controller;

import com.project_training.nourriture.R;
import com.project_training.nourriture.utils.FontsOverride;
import com.project_training.nourriture.utils.MLRoundedImageView;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileManagerFragment extends Fragment {

	@Bind(R.id.img_profile) MLRoundedImageView imgProfile;
	@Bind(R.id.profile_fullname) TextView	profileFullName;
	@Bind(R.id.moments_list) ListView	momentsList;
	@Bind(R.id.my_recipes_list) ListView	recipesList;
	@Bind(R.id.my_parameters) TextView	myParameters;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View	view = inflater.inflate(R.layout.fragment_profile_manager, container, false);
		ButterKnife.bind(this, view);
				
		ButterKnife.bind(this, view);
		ButterKnife.setDebug(true);
	
		return view;
	}
}
