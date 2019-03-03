package com.andraganoid.verymuchtodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Todo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);

        Log.d("TODO NAME: " , getIntent().getStringExtra("displayName"));
        Log.d("TODO ID: ",getIntent().getStringExtra("userId"));

    }
}
