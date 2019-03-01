package com.progressifff.bookfinder

import android.os.Bundle
import android.support.v4.util.LongSparseArray
import java.lang.Exception
import java.util.concurrent.atomic.AtomicLong

object PresenterManager {
    private var curPresenterId: AtomicLong = AtomicLong()
    private var presenters = LongSparseArray<BasePresenter<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <P : BasePresenter<*>> restorePresenter(savedInstanceState: Bundle): P{
        val presenterId = savedInstanceState.getLong(Constants.PRESENTER_ID)
        val presenter = presenters.get(presenterId)
        if(presenter != null) {
            presenters.remove(presenterId)
            return presenter as P
        }
        throw Exception("Failed to restore presenter")
    }

    fun savePresenter(presenter: BasePresenter<*>, outState: Bundle){
        val presenterId = curPresenterId.incrementAndGet()
        presenters.put(presenterId, presenter)
        outState.putLong(Constants.PRESENTER_ID, presenterId)
    }
}