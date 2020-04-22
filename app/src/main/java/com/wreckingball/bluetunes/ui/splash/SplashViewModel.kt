package com.wreckingball.bluetunes.ui.splash

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.wreckingball.bluetunes.components.BTModel

class SplashViewModel(private val btModel: BTModel) : ViewModel() {
    val bluetoothProgress = btModel.progress

    fun setupBluetooth(activity: Activity) {
        if (!btModel.isSupported()) {
            return
        }

        btModel.enableBluetooth(activity)
    }

    fun discoverDevices(activity: Activity) {
        if (btModel.getPairedDevices()) {
            //there are paired devices
//            if (btModel.pairedDevices.size == 1) {
                btModel.connect(activity)
//            }
        } else {
            btModel.deviceNotPaired()
        }
    }

    fun pairDevice(activity: Activity) {
        btModel.pairDevice(activity)
    }
}