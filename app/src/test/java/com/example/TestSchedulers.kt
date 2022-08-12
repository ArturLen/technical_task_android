package com.example

import com.example.sliideusersbook.core.rx.SchedulersProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class TestSchedulers : SchedulersProvider {
    override fun main(): Scheduler = Schedulers.trampoline()

    override fun io() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()
}