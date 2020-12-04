package com.siba.searchmvvmpractice.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val TAG = "SingleLiveEvent"

    private val pending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if(hasActiveObservers()){
            Log.w(TAG,"Mutiple observers regustered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner){
            if(pending.compareAndSet(true,false)){
                observer.onChanged(it)
            }
        }
    }

    @MainThread
    override fun setValue(value: T) {
        pending.set(true)
        super.setValue(value)
    }
}

