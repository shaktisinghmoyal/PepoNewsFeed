package com.pepo.news.domain.interactor;

import com.pepo.news.domain.executor.PostExecutionThread;
import com.pepo.news.domain.executor.ThreadExecutor;
import com.pepo.news.domain.repository.INewsFeedRepository;

import javax.inject.Inject;

import rx.Observable;

/*
This is the Use case , basically we will have a use case
for every calls which asked to update or get the data either form server or database. Sole
purpose of this class is to fetch the network data or DB data  asynchronously and make it
available to the respective presenter

In this cases we will update  the read  news feed with DB
*/
public class UpdateReadInfoInDB extends BaseUseCase {
    private INewsFeedRepository repository;

    @Inject
    public UpdateReadInfoInDB(INewsFeedRepository repository, ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }


    @Override
    public Observable buildUseCaseObservable() {
        return repository.updateReadInfoInDB(position);
    }
}
