package com.lacreatelit.android.nerdlauncher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {
	
	protected abstract Fragment createFragment();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	// Set the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        
        // Get the Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        
        // Check if the Fragment exists, else create the Fragment
        Fragment fragment = 
        		fragmentManager.findFragmentById(R.id.fragmentContainer);
        
        if(fragment == null) {
        	
        	fragment = createFragment();
        	fragmentManager.beginTransaction()
        		.add(R.id.fragmentContainer, fragment)
        		.commit();
        	
        }
    }

}
