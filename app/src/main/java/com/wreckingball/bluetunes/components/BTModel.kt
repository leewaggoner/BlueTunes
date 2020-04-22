package com.wreckingball.bluetunes.components

import android.app.Activity
import android.bluetooth.BluetoothA2dp
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings
import androidx.lifecycle.MutableLiveData
import com.wreckingball.bluetunes.MainActivity


enum class BluetoothProgress {
    NOT_SUPPORTED,
    IS_ENABLED,
    NOT_ENABLED,
    PAIR_DEVICE,
    DEVICE_CONNECTED,
    DEVICE_DISCONNECTED
}

class BTModel {
    companion object {
        const val REQUEST_ENABLE_BT = 1
        const val REQUEST_PAIRING = 2
    }

    val progress = MutableLiveData<BluetoothProgress>()
    private var pairedDevices = mutableListOf<BluetoothDevice>()

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothA2dp: BluetoothA2dp? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bluetoothAction = intent?.action
            bluetoothAction?.let {action ->
                when (action) {
                    BluetoothDevice.ACTION_ACL_CONNECTED -> {
                        safeSetProgress(BluetoothProgress.DEVICE_CONNECTED)
                    }
                    BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                        safeSetProgress(BluetoothProgress.DEVICE_DISCONNECTED)
                    }
                }
            }
        }
    }

    private val profileListener = object : BluetoothProfile.ServiceListener {
        override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
            if (profile == BluetoothProfile.A2DP) {
                bluetoothA2dp = proxy as BluetoothA2dp
                var connected = bluetoothAdapter?.getProfileConnectionState(BluetoothProfile.A2DP) == BluetoothProfile.STATE_CONNECTED
                if (connected) {
                    safeSetProgress(BluetoothProgress.DEVICE_CONNECTED)
                } else {
                    safeSetProgress(BluetoothProgress.DEVICE_DISCONNECTED)
                }
            }
        }
        override fun onServiceDisconnected(profile: Int) {
            if (profile == BluetoothProfile.A2DP) {
                bluetoothA2dp = null
            }
        }
    }

    fun isSupported() : Boolean {
        var ok = true
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            safeSetProgress(BluetoothProgress.NOT_SUPPORTED)
            ok = false
        }
        return ok
    }

    fun enableBluetooth(activity: Activity) {
        val mainActivity = activity as MainActivity
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            mainActivity.startActivityForResult(enableBtIntent,
                REQUEST_ENABLE_BT
            )
        } else {
            safeSetProgress(BluetoothProgress.IS_ENABLED)
        }
    }

    fun couldNotEnable() {
        safeSetProgress(BluetoothProgress.NOT_ENABLED)
    }

    fun donePairing() {
        safeSetProgress(BluetoothProgress.IS_ENABLED)
    }

    fun getPairedDevices() : Boolean {
        val pairedDevicesSet: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        pairedDevicesSet?.forEach { device ->
            pairedDevices.add(device)
        }
        return pairedDevices.isNotEmpty()
    }

    fun connect(activity: Activity) {
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        activity.registerReceiver(receiver, filter)

        bluetoothAdapter?.getProfileProxy(activity, profileListener, BluetoothProfile.A2DP)
    }

    fun disconnect(activity: Activity) {
        try {
            activity.unregisterReceiver(receiver)
        } catch (ex: IllegalArgumentException) {
            //receiver not registered
        }
        bluetoothAdapter?.closeProfileProxy(BluetoothProfile.A2DP, bluetoothA2dp)
    }

    fun deviceNotPaired() {
        safeSetProgress(BluetoothProgress.PAIR_DEVICE)
    }

    fun pairDevice(activity: Activity) {
        val mainActivity = activity as MainActivity

        val intentBluetooth = Intent()
        intentBluetooth.action = Settings.ACTION_BLUETOOTH_SETTINGS
        mainActivity.startActivityForResult(intentBluetooth,
            REQUEST_PAIRING
        )
    }

    private fun safeSetProgress(state: BluetoothProgress) {
        if (progress.value != state) {
            progress.value = state
        }
    }
}