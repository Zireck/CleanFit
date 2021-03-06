package com.zireck.calories.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.zireck.calories.presentation.dagger.component.DaggerFoodComponent;
import com.zireck.calories.presentation.dagger.module.FoodModule;
import com.zireck.calories.presentation.listener.OnAddFoodFinishedListener;
import com.zireck.calories.presentation.view.fragment.AddFoodFragment;
import com.zireck.calories.R;

/**
 * Created by Zireck on 24/07/2015.
 */
public class AddFoodActivity extends AddEditFoodActivity implements OnAddFoodFinishedListener {

    /**
     * Generates the intent needed to launch this activity.
     */
    public static Intent getLaunchIntent(final Context context) {
        return new Intent(context, AddFoodActivity.class);
    }

    @Override
    public void foodAdded() {
        navigateBack(RESULT_OK);
    }

    @Override
    public void initInjector() {
        mFoodComponent = DaggerFoodComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .foodModule(new FoodModule())
                .build();
    }

    @Override
    public void initializeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(R.id.fragment_container, AddFoodFragment.newInstance())
                .commit();
    }

}
