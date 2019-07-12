package com.example.basis.utils.network

import java.io.IOException
import javax.inject.Singleton

@Singleton
class NetworkHelper {

    @Throws(InterruptedException::class, IOException::class)
    fun isNetworkConnected(): Boolean {
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }
}