package com.siba.searchmvvmpractice.vo

/**
 * A generic class that holds a value with its loading status
 *
 * Data의 상태를 나타내줄 Data class
 *
 * Success
 *
 * Error
 *
 * Laoding
 *
 * generic인 T를 인자로 받고 , enumClass인 status와 T형태의 data , string형의 message를 가지고 있다.
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        /**
         * 각 함수별로
         *
         * success 함수를 호출하면 SUCCESS,T형의 데이터 , null 을 만들어서 반환
         *
         * error 함수를 호출하면 ERROR , T형의 데이터 , message 를 만들어서 반환
         *
         * loading 함수를 호출하면 LOADING , T형의 데이터 , null 을 만들어서 반환
         */
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
