package com.example.pretest_sk_5_0.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.pretest_sk_5_0.R

class ChargeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        // if getBooleanExtra
        val isPowerCharge = intent?.getBooleanExtra("state", true) ?: return

        // checking power
        if (isPowerCharge) {
            // showing the toast message if true
            Toast.makeText(
                context,
                context!!.getString(
                    R.string.charge_plugin
                ),
                Toast.LENGTH_LONG
            ).show()
        } else {
            // showing the toast message if false
            Toast.makeText(
                context,
                context!!.getString(
                    R.string.charge_unplugin
                ),
                Toast.LENGTH_LONG
            ).show()
        }
    }

}