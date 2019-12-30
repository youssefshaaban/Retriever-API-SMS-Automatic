package com.example.retrivalapisms

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MySMSBroadcastReceiver.OTPReceiveListener {


    val TAG = MainActivity::class.java.simpleName

    override fun onReceiveOTPMessage(message: String) {
        txtLabel.text = message
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver)
            smsReceiver = null
        }
    }

    override fun onTimeOut() {
        showToast("OTP Time out")
        startSmsUserConsent()
    }

    private var smsReceiver: MySMSBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signature = AppSignatureHashHelper(this)
        val sign = signature.appSignatures[0]
        startSmsUserConsent()
        Log.d(TAG+"HashKey", sign)
    }

    private fun startSmsUserConsent() {
        try {
            smsReceiver = MySMSBroadcastReceiver()
            smsReceiver!!.otpListener = this
            val intentFilter = IntentFilter()
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
            this.registerReceiver(smsReceiver, intentFilter)
            val client: SmsRetrieverClient = SmsRetriever.getClient(this)
            val task = client.startSmsRetriever()
            task.addOnSuccessListener {
                Log.d("Success", "success")
            }
            task.addOnFailureListener {
                Log.d("Exception", it.message!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver)
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
