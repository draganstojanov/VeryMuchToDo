package com.andraganoid.verymuchtodo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.backgorundColor)
    }
}


//Store: C:\Users\Stojanov 4\.android\debug.keystore
//Alias: AndroidDebugKey
//MD5: 7D:A1:4F:55:01:77:DF:DA:4A:89:8C:1A:D1:A0:77:70
//SHA1: F4:35:B9:4F:4C:96:5E:2D:23:8A:CB:53:39:F0:AA:6D:49:53:CF:F1
//SHA-256: 06:E8:B5:62:56:3C:AF:DA:AA:55:56:75:73:AB:E6:24:36:1C:8D:F6:43:70:BF:9A:C9:46:82:4F:4D:E3:EA:48
//Valid until: Tuesday, March 8, 2050
