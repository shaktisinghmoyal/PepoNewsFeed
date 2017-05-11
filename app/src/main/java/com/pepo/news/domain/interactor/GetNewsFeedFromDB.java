package com.pepo.news.domain.interactor;

import com.pepo.news.domain.executor.PostExecutionThread;
import com.pepo.news.domain.executor.ThreadExecutor;
import com.pepo.news.domain.repository.INewsFeedRepository;

import javax.inject.Inject;

import rx.Observable;

/*
This is the Use case , basically we will have a use case
for every calls which asked to update or  the get data either form server or database. Sole
purpose of this class is to fetch the network data or DB data  asynchronously and make it
available to the respective presenter

In this cases we will fetch the news feed form DB
*/
public class GetNewsFeedFromDB extends BaseUseCase{
    private INewsFeedRepository repository;

    @Inject
    public GetNewsFeedFromDB(INewsFeedRepository repository, ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }


    @Override
    public Observable buildUseCaseObservable() {
        return repository.getNewsFeedFromDB();
    }
}
