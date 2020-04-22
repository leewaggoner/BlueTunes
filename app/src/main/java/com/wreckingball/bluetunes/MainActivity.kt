package com.wreckingball.bluetunes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wreckingball.bluetunes.components.BTModel
import com.wreckingball.bluetunes.components.MusicPlayer
import org.koin.android.ext.android.inject

private const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
private const val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2

class MainActivity : AppCompatActivity() {
    private val btModel: BTModel by inject()
    private val musicPlayer: MusicPlayer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            BTModel.REQUEST_ENABLE_BT -> {
                if (resultCode == Activity.RESULT_CANCELED) {
                    //let BTModel know Bluetooth could not be enabled
                    btModel.couldNotEnable()
                }
            }
            BTModel.REQUEST_PAIRING -> {
                btModel.donePairing()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.release()
        btModel.disconnect(this)
    }
}
