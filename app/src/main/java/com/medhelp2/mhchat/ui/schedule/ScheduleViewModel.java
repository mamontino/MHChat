package com.medhelp2.mhchat.ui.schedule;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class ScheduleViewModel extends ViewModel
{
    private MutableLiveData<List<String>> users;

    public LiveData<List<String>> getUsers()
    {
        if (users == null)
        {
            users = new MutableLiveData<List<String>>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers()
    {
        // Do an asyncronous operation to fetch users.
    }
}
