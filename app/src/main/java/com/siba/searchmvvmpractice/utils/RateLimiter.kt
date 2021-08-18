package com.siba.searchmvvmpractice.utils

import android.os.SystemClock
import android.util.ArrayMap
import java.util.concurrent.TimeUnit

/**
 * Utility class that decides whether we should fetch some data or not.
 *
 * SSong-develop
 * : 데이터를 fetch를 할때 시간을 기록해둠으로써 연속으로 fetch를 하는 것을 방지하는 클래스
 *
 */
class RateLimiter<in KEY>(timeout : Int , timeUnit : TimeUnit) {
    private val timestamps = ArrayMap<KEY,Long>() // 시간 기록 Map
    private val timeout = timeUnit.toMillis((timeout.toLong())) // 정해진 시간

    @Synchronized
    fun shouldFetch(key : KEY) : Boolean {
        val lastFetched = timestamps[key] // 기존에 네트워킹 했던 시간을 가져온다
        val now = now() // 현 시각을 가져온다
        if(lastFetched == null){ // 만약 기존에 네트워킹을 했던 기록이 없다면 해주고
            timestamps[key] = now
            return true
        }
        if(now - lastFetched > timeout) { // 현시간에서 기존의 네트워킹 시간을 뺀게 정해진 시간보다 크다면 fetch한다.
            timestamps[key] = now
            return true
        }
        return false // 위의 두 조건이 아닐 경우 false로 네트워크를 진행하지 않도록 한다.
    }

    private fun now() = SystemClock.uptimeMillis()

    @Synchronized
    fun reset(key : KEY) {
        timestamps.remove(key)
    }
}