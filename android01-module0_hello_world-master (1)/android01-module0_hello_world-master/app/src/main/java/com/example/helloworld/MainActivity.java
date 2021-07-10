package com.example.helloworld;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText etName;
    private Button btHello;
    private Button btBye;
    private TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        btHello = findViewById(R.id.bt_hello);
        btBye = findViewById(R.id.bt_bye);
        tvHello = findViewById(R.id.tv_hello);

        //1. set click btHello
//        btHello.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Log.d(TAG, "onClick: ");
//
//                //2. get text from etName
//                String name = etName.getText().toString();
//                Log.d(TAG, "onClick: " + name);
//
//                //3.
//                tvHello.setText(String.format("Hello, %s", name));
//            }
//        });
//
//        btBye.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Log.d(TAG, "onClick: ");
//
//                //2. get text from etName
//                String name = etName.getText().toString();
//                Log.d(TAG, "onClick: " + name);
//
//                //3.
//                tvHello.setText(String.format("Bye, %s", name));
//            }
//        });

        btHello.setOnClickListener(this);
        btBye.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString();
        Log.d(TAG, "onClick: " + name);

        //3.
        if (v.getId() == R.id.bt_hello)
            tvHello.setText(String.format("Hello, %s", name));
        else
            tvHello.setText(String.format("Bye, %s", name));
    }
}
