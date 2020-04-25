package com.quess.catapp.utils

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class EventLiveData<T> : MutableLiveData<T>() {

    private var isRead: AtomicBoolean = AtomicBoolean(false)

    /**
     * ensure the event is non-null and can only been seen once
     */
    fun observeEvent(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, Observer {
            if (it != null && isRead.compareAndSet(false, true)) {
                observer.onChanged(it)
            }
        })
    }

    /**
     * re-implemented post method, as the original implementation may swallow date changes
     * by ignored all data before last [postValue] call
     */
    override fun postValue(value: T) {

        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            setValue(value)
        } else {
            Handler(Looper.getMainLooper()).post { setValue(value) }
        }
    }

    override fun setValue(value: T) {
        isRead.set(false)
        super.setValue(value)
    }
}