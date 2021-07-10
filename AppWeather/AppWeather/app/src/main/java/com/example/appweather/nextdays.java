package com.example.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class nextdays extends AppCompatActivity {
    String tenthanhpho = "";
    ImageView imgback;
    TextView txtName;
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<Thoitiet> mangthoitiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextdays);
        Anhxa();
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ketqua", "Dữ liệu truyền qua :" + city);
        if (city.equals("")) {
            tenthanhpho = "Saigon";
            Get7daysData(tenthanhpho);
        } else {
            tenthanhpho = city;
            Get7daysData(tenthanhpho);
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void Anhxa() {
        imgback = (ImageView) findViewById(R.id.imageviewback);
        txtName = (TextView) findViewById(R.id.textviewtenthanhpho);
        lv = (ListView) findViewById(R.id.listview);
        mangthoitiet = new ArrayList<Thoitiet>();
        customAdapter = new CustomAdapter(nextdays.this, mangthoitiet);
        lv.setAdapter(customAdapter);
    }

    private void Get7daysData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + data + "&units=metric&cnt=7&appid=2900838bbe4399e2bb6258a2abcedf7d";
        RequestQueue requestQueue = Volley.newRequestQueue(nextdays.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                    String name = jsonObjectCity.getString("name");
                    txtName.setText(name);

                    JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArrayList.length(); i++) {
                        JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                        String ngay = jsonObjectList.getString("dt");

                        long l = Long.valueOf(ngay);
                        Date date = new Date(l * 1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                        String Day = simpleDateFormat.format(date);

                        JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                        String max = jsonObjectTemp.getString("max");
                        String min = jsonObjectTemp.getString("min");

                        Double a = Double.valueOf(max);
                        String NhietdoMax = String.valueOf(a.intValue());
                        Double b = Double.valueOf(min);
                        String NhietdoMin = String.valueOf(b.intValue());

                        JSONArray jsonArrayWeather= jsonObjectList.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String status = jsonObjectWeather.getString("description");
                        String icon = jsonObjectWeather.getString("icon");

                        mangthoitiet.add(new Thoitiet(Day,status,icon,NhietdoMax,NhietdoMin));

                    }
                    customAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}







