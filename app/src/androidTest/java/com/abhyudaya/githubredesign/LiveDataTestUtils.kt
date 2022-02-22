package com.abhyudaya.githubredesign

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.runners.model.TestTimedOutException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(): T {
    var data: T? = null
    val latch = CountDownLatch(2)

    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            this@getOrAwaitValue.removeObserver(this)
            latch.countDown()
        }
    }

    this.observeForever(observer)

//    try {
//        if (!latch.await(4, TimeUnit.SECONDS)) {
//            throw TimeoutException("Live data never gets value")
//        }
//    }
//    finally {
//        this.removeObserver(observer)
//    }

    return data as T
 }