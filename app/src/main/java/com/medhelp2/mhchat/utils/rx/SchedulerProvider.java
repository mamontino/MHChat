package com.medhelp2.mhchat.utils.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider
{
    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}
