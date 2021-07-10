package com.example.module1_drawingnotes;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    int requestCodeWtireStorage = 1001;
    String[] permission = new String[1];

    private GridView gridView;
    private ImageAdapter imageAdapter;
    private TextView tvNoImage;
    private TextView tvAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fbAdd = findViewById(R.id.fb_add);
        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                startActivity(intent);
            }
        });

        setupPermission();


        tvNoImage = findViewById(R.id.tv_noimage);
        tvAdd = findViewById(R.id.tv_add);

        gridView = findViewById(R.id.gv_images);
        imageAdapter = new ImageAdapter();
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to delete this image?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ImageUtils.getListImage().get(position).delete();
                                imageAdapter.notifyDataSetChanged();
                                if (ImageUtils.getListImage().isEmpty()){
                                    tvAdd.setText("Click + to add more");
                                    tvNoImage.setText("No image");
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                return true;
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        imageAdapter.notifyDataSetChanged();
        if(ImageUtils.getListImage().isEmpty()){
            tvAdd.setText("Click + to add more");
            tvNoImage.setText("No image");
        } else {
            tvNoImage.setText(null);
            tvAdd.setText(null);
        }
    }



    public void setupPermission() {

        permission[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permission, requestCodeWtireStorage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == requestCodeWtireStorage) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Warning")
                        .setMessage("Without permission, you can not use this app. Do you want to grant permission?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setupPermission();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                        .show();
            }
        }
    }
}
