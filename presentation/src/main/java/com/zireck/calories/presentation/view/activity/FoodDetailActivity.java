package com.zireck.calories.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;
import com.zireck.calories.R;
import com.zireck.calories.presentation.dagger.HasComponent;
import com.zireck.calories.presentation.dagger.component.DaggerFoodComponent;
import com.zireck.calories.presentation.dagger.component.FoodComponent;
import com.zireck.calories.presentation.dagger.module.FoodModule;
import com.zireck.calories.presentation.listener.FoodDetailCallback;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.util.PictureUtils;
import com.zireck.calories.presentation.view.fragment.FoodDetailFragment;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailActivity extends BaseActivity implements FoodDetailCallback,
                                                                HasComponent<FoodComponent> {

    private static final String EXTRA_FOOD_OBJECT = "food_object";

    private FoodComponent mFoodComponent;

    private FoodModel mFood;

    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.food_image) ImageView mFoodImage;
    @Bind(R.id.fab) FloatingActionButton mFloatingActionButton;

    /**
     * Generates the intent needed to launch this activity.
     */
    public static Intent getLaunchIntent(final Context context, final FoodModel food) {
        if (food == null) {
            FoodDetailActivity.throwIllegalArgumentException();
        }

        Intent intent = new Intent(context, FoodDetailActivity.class);
        intent.putExtra(FoodDetailActivity.EXTRA_FOOD_OBJECT, food);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mapExtras();
        initInjector();
        initActionBar();
        initFloatingActionButton();
        initFoodImage();
        initializeFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public FoodComponent getComponent() {
        return mFoodComponent;
    }

    @Override
    public void setFoodPicture(String foodPicture) {
        Picasso.with(this).load(PictureUtils.getPhotoFileUri(foodPicture)).fit().centerCrop().into(mFoodImage);
    }

    /**
     * As FoodId is mandatory if there is not an extra inside the bundle that launches this activity
     * we are going to throw a new IllegalArgumentException.
     */
    private void mapExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throwIllegalArgumentException();
        }

        mFood = extras.getParcelable(FoodDetailActivity.EXTRA_FOOD_OBJECT);
        if (mFood == null) {
            throwIllegalArgumentException();
        }
    }

    private void initInjector() {
        mFoodComponent = DaggerFoodComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .foodModule(new FoodModule(mFoodModelDataMapper.transformInverse(mFood)))
                .build();
    }

    private void initActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setTitle(" ");
    }

    private void initFloatingActionButton() {
        MaterialIconView icon = new MaterialIconView(this);
        icon.setIcon(MaterialDrawableBuilder.IconValue.SILVERWARE_FORK);
        icon.setColorResource(android.R.color.white);
        mFloatingActionButton.setImageDrawable(icon.getDrawable());
    }

    private void initFoodImage() {
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getColor(mFood.getName());

        mFoodImage.setBackgroundColor(color);
    }

    @OnClick(R.id.fab)
    public void onClickFAB() {
        mNavigator.openAddMealActivity(this);
    }

    private void initializeFragment() {
        if (mFood == null) {
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, FoodDetailFragment.newInstance(mFood)).commit();
    }

    private static void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "FoodDetailActivity has to be launched using a valid Food object as extra");
    }
}
