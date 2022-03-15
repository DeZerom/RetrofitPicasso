package com.example.retrofitpicasso.retrofit

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(application: Application): Interceptor {

    private val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val netInfo = connectivityManager.activeNetworkInfo
        if (netInfo != null && netInfo.isConnected) {
            return chain.proceed(chain.request())
        } else {
            throw IOException("No connection")
        }
    }
}