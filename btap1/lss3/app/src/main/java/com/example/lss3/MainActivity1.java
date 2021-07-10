package com.example.lss3;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton flbtnAdd= findViewById(R.id.add);
        final ScrollView scrollView=findViewById(R.id.sv);
        final LinearLayout llScroll =findViewById(R.id.ll_in_scroll);

        flbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llScroll.addView(createImage(MainActivity1.this));
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
    }
    public int randomImage(){
        Random random = new Random();
        int img=random.nextInt(5) +1;
        switch (img) {
            case 1:
                return R.drawable.food_1;
            case 2:
                return R.drawable.food_2 ;
            case 3:
                return R.drawable.food_3;
            case 4:
                return R.drawable.food_4 ;
            case 5:
                return R.drawable.food_5 ;

        }
        return 0;
    }
    public CustomView createImage(Context context){
        CustomView imageView = new CustomView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);
        layoutParams.setMargins(0,0,0,16);
        imageView.setBackgroundResource(randomImage());
        return imageView;
    }

}