package com.zireck.calories.domain.interactor;

import com.zireck.calories.domain.executor.PostExecutionThread;
import com.zireck.calories.domain.executor.ThreadExecutor;
import com.zireck.calories.domain.repository.FoodRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 09/11/2015.
 */
public class CheckIfFoodCatalogIsEmpty extends Interactor {

    private final FoodRepository mFoodRepository;

    @Inject
    public CheckIfFoodCatalogIsEmpty(FoodRepository foodRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFoodRepository = foodRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mFoodRepository.isCatalogEmpty();
    }
}
