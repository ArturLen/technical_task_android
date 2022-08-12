package com.example.sliideusersbook.core.rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

interface SchedulersProvider {
    fun main(): Scheduler
    fun io(): Scheduler
    fun computation(): Scheduler
}

class AppSchedulersProvider : SchedulersProvider {
    override fun main(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler = Schedulers.computation()
}