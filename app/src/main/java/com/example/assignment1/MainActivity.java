package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
//    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String TAG = "MainActivity";
    public Button create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create_account = findViewById(R.id.create_acc);
        create_account.setOnClickListener(this::onClick);

//        create_account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "Start on click");
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                Log.d(TAG, "After intent creation, starting activity");
//                startActivity(intent);
//                Log.d(TAG, "End on click");
//            }
//        });
    }

    private void onClick(View view) {
        Log.d(TAG, "Start on click");
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        Log.d(TAG, "After intent creation, starting activity");
        startActivity(intent);
        Log.d(TAG, "End on click");
    }
}