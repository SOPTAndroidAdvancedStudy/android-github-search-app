package com.siba.searchmvvmpractice.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class NetworkConnectionCheck(
    private val context: Context,
    private val viewModel: SearchViewModel
) : ConnectivityManager.NetworkCallback(), LifecycleOwner {
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        // 기존에 WIFI를 끊어놓고 앱을 시작하게 되면 onLost가 check되지 않아 networkChecked가 true로 된다
        viewModel.networkChecked = true
        Toast.makeText(context, "Network is Available + ${viewModel.networkChecked}", Toast.LENGTH_SHORT).show()
        // Network가 가능 상태 일때는 , appDatabase에 담아만 놓고, 기존에 검색이 다된 데이터를 보여준다.
        // can't observe on background thread

    }

    override fun onLost(network: Network) {
        super.onLost(network)
        viewModel.networkChecked = false
        Toast.makeText(context, "Network is not Available + ${viewModel.networkChecked}", Toast.LENGTH_SHORT).show()
        // Network가 불가능한 상태일 때는 , appDatabase의 데이터를 가져와 보여주도록 한다.
    }

    fun registerNetworkCallback() {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun terminateNetworkCallback() {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        connectivityManager.unregisterNetworkCallback(this)

    }

    override fun getLifecycle(): Lifecycle {
        return LifecycleRegistry(this)
    }
}