package com.project_training.nourriture;

import com.project_training.nourriture.utils.UsefulFunctions;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View	view = inflater.inflate(R.layout.fragment_main, container, false);
		ButterKnife.bind(this, view);
	
		if ( true == UsefulFunctions.checkConnection(getActivity()) ) {
			
		}
		
		return view;
	}
}
