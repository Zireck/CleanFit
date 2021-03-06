package com.zireck.calories.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;
import com.zireck.calories.R;
import com.zireck.calories.presentation.dagger.component.FoodComponent;
import com.zireck.calories.presentation.helper.RecyclerItemClickListener;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.navigation.Navigator;
import com.zireck.calories.presentation.presenter.FoodListPresenter;
import com.zireck.calories.presentation.view.FoodListView;
import com.zireck.calories.presentation.view.adapter.FoodRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Zireck on 22/07/2015.
 */
public class FoodListFragment extends BaseFragment implements FoodListView {

    @Inject Navigator mNavigator;
    @Inject FoodListPresenter mPresenter;

    @Bind(R.id.food_list_empty) TextView mFoodListEmpty;

    @Bind(R.id.food_list) RecyclerView mRecyclerView;
    protected FoodRecyclerAdapter mAdapter;

    public static FoodListFragment newInstance() {
        return new FoodListFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_food_list;
    }

    @Override
    public void renderFoodList(Collection<FoodModel> foodModelsCollection) {
        if (foodModelsCollection != null && foodModelsCollection.size() > 0) {
            mFoodListEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mFoodListEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

        mAdapter.setFoodsCollection(foodModelsCollection);
    }

    @Override
    public void navigateToFoodDetails(FoodModel food) {
        mNavigator.openFoodDetailActivity(getActivity(), food);
    }

    protected void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mPresenter.setView(this);

        mFoodListEmpty.setText("Your food list is empty.");
        mFoodListEmpty.setTypeface(EasyFonts.robotoLight(getActivity()));
        mFoodListEmpty.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FoodModel food = mAdapter.getItem(position);
                mPresenter.onItemClick(food);
            }
        }));

        mAdapter = new FoodRecyclerAdapter(getActivity(), new ArrayList<FoodModel>(), FoodRecyclerAdapter.ITEM_LAYOUT);
        mRecyclerView.setAdapter(mAdapter);
    }

}
