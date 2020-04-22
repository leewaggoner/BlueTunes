package com.wreckingball.bluetunes.ui.splash

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.wreckingball.bluetunes.R
import com.wreckingball.bluetunes.components.BluetoothProgress
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val model: SplashViewModel by viewModel()
    private var called = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model.setupBluetooth(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.bluetoothProgress.observe(viewLifecycleOwner, Observer {progress ->
            when (progress) {
                BluetoothProgress.NOT_SUPPORTED -> {
                    progressBarSplash.visibility = View.INVISIBLE
                    Snackbar.make(view, R.string.bt_not_supported, Snackbar.LENGTH_LONG).show()
                }
                BluetoothProgress.IS_ENABLED -> {
                    model.discoverDevices(requireActivity())
                }
                BluetoothProgress.NOT_ENABLED -> {
                    progressBarSplash.visibility = View.INVISIBLE
                    Snackbar.make(view, R.string.bt_not_enabled, Snackbar.LENGTH_LONG).show()
                }
                BluetoothProgress.PAIR_DEVICE -> {
                    progressBarSplash.visibility = View.INVISIBLE
                    showPairDialog()
                }
                BluetoothProgress.DEVICE_CONNECTED -> {
                    progressBarSplash.visibility = View.INVISIBLE
                    if (!called) {
                        val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                        view.findNavController().navigate(action)
                        called = true
                    }
                }
                BluetoothProgress.DEVICE_DISCONNECTED -> {
                    progressBarSplash.visibility = View.INVISIBLE
                    showTurnOnDeviceDialog()
                }
                else -> {
                    progressBarSplash.visibility = View.INVISIBLE
                    Snackbar.make(view, R.string.unknown_error, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun showPairDialog() {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle(R.string.pair_device_title)
            setMessage(R.string.pair_device)
            setIcon(R.drawable.bluetooth)
            setPositiveButton(android.R.string.ok) { _, _ ->
                model.pairDevice(requireActivity())
            }
        }
        val pairDialog = builder.create()
        pairDialog?.setCanceledOnTouchOutside(false)
        pairDialog?.show()
    }

    private fun showTurnOnDeviceDialog() {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle(R.string.turn_on_device_title)
            setMessage(R.string.turn_on_device)
            setIcon(R.drawable.bluetooth)
            setPositiveButton(android.R.string.ok, null)
        }
        val pairDialog = builder.create()
        pairDialog?.setCanceledOnTouchOutside(false)
        pairDialog?.show()
    }
}
