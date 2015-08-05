package com.zireck.projectk.dagger;

import com.zireck.projectk.fragment.FoodDetailFragment;
import com.zireck.projectk.interactor.FoodDetailInteractor;
import com.zireck.projectk.presenter.FoodDetailPresenter;
import com.zireck.projectk.presenter.FoodDetailPresenterImpl;
import com.zireck.projectk.view.FoodDetailView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zireck on 06/08/2015.
 */
@Module(
        injects = FoodDetailFragment.class,
        addsTo = ActivityModule.class,
        complete = false
)
public class FoodDetailModule {

    private FoodDetailView mView;

    public FoodDetailModule(FoodDetailView view) {
        mView = view;
    }

    @Provides @Singleton
    public FoodDetailView provideView() {
        return mView;
    }

    @Provides @Singleton
    public FoodDetailPresenter providePresenter(FoodDetailView view, FoodDetailInteractor interactor) {
        return new FoodDetailPresenterImpl(view, interactor);
    }
}
