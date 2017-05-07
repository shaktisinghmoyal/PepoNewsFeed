package com.pepo.news.domain.interactor;

import com.pepo.news.domain.executor.PostExecutionThread;
import com.pepo.news.domain.executor.ThreadExecutor;
import com.pepo.news.domain.repository.INewsFeedRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetNewsFeed extends BaseUseCase {
    private final String Tag = "GetNewsFeed";
    private INewsFeedRepository repository;

    @Inject
    public GetNewsFeed(INewsFeedRepository repository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }


    @Override
    public Observable buildUseCaseObservable() {
        return repository.getNewsFeed();
    }
}