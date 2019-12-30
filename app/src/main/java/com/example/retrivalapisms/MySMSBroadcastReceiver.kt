package com.example.retrivalapisms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


class MySMSBroadcastReceiver : BroadcastReceiver() {

    /**
     * BroadcastReceiver to wait for SMS messages. This can be registered either
     * in the AndroidManifest or at runtime.  Should filter Intents on
     * SmsRetriever.SMS_RETRIEVED_ACTION.
     */
     var otpListener: OTPReceiveListener? = null

    /**
     * @param otpListener
     */


    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent!!.action) {
            SmsRetriever.SMS_RETRIEVED_ACTION -> {
                val extras = intent.extras
                val status = extras!!.get(SmsRetriever.EXTRA_STATUS) as Status?
                when (status!!.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val message:String = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                        // Extract one-time code from the message and complete verification
                        // by sending the code back to your server.
                        /*<#> Your ExampleApp code is: 123ABC78
                            FA+9qCX9VSu*/
                        Log.d("MySMSBroadcastReceiver",message)
                        val splitMessage =
                            message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                                .toTypedArray()
                        //Extract the OTP code and send to the listener
                        if (otpListener!=null)
                            otpListener!!.onReceiveOTPMessage(splitMessage[splitMessage.size-1].split("\n".toRegex()).toTypedArray()[0])
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        // Waiting for SMS timed out (5 minutes)
                        // Handle the error ...
                        //val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE)
                        if (otpListener!=null)
                            otpListener!!.onTimeOut()
                    }
                }
            }

        }
    }

    interface OTPReceiveListener {
        fun onReceiveOTPMessage(message: String)
        fun onTimeOut()
    }
}