package com.medhelp2.mhchat.utils.rx;

import android.widget.SearchView;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxSearch
{

    private RxSearch()
    {
    }

    public static Observable<String> fromView(SearchView searchView)
    {
        final PublishSubject<String> subject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text)
            {
                subject.onNext(text);
                return true;
            }
        });

        return subject;
    }
}
