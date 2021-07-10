package com.example.appweather;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    EditText editSearch;
    Button btnSearch, btnChange;
    TextView txtName, txtCountry, txtTemp, txtStatus, txtRain, txtCloudy, txtWind, txtDay;
    ImageView imgIcon;
    String City = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editSearch.getText().toString();
                if (city.equals("")) {
                    City = "Hanoi";
                    GetCurrentWeatherData(City);
                } else {
                    City = city;
                    GetCurrentWeatherData(City);
                }
                GetCurrentWeatherData(city);

            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this, nextdays.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });
    }

    public void GetCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=2900838bbe4399e2bb6258a2abcedf7d";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,


                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtName.setText("City : " + name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);

                            txtDay.setText(Day);
                            JSONArray jsonArrayweather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectweather = jsonArrayweather.getJSONObject(0);
                            String status = jsonObjectweather.getString("main");
                            String icon = jsonObjectweather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/" + icon + ".png").into(imgIcon);
                            txtStatus.setText(status);

                            JSONObject jsonObjectmain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectmain.getString("temp");
                            String doam = jsonObjectmain.getString("humidity");

                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());

                            txtTemp.setText(Nhietdo + "*C");
                            txtRain.setText(doam + "%");

                            JSONObject jsonObjectwind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectwind.getString("speed");
                            txtWind.setText(gio + " m/s");

                            JSONObject jsonObjectclouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectclouds.getString("all");
                            txtCloudy.setText(may + "%");

                            JSONObject jsonObjectsys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectsys.getString("country");
                            txtCountry.setText("Country : " + country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    private void Anhxa() {
        editSearch = (EditText) findViewById(R.id.edittextSearch);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        btnChange = (Button) findViewById(R.id.buttonChange);
        txtName = (TextView) findViewById(R.id.textviewName);
        txtCountry = (TextView) findViewById(R.id.textviewCountry);
        txtTemp = (TextView) findViewById(R.id.textviewTemp);
        txtStatus = (TextView) findViewById(R.id.textviewStatus);
        txtRain = (TextView) findViewById(R.id.textviewRain);
        txtCloudy = (TextView) findViewById(R.id.textviewCloudy);
        txtWind = (TextView) findViewById(R.id.textviewWindy);
        txtDay = (TextView) findViewById(R.id.textviewDay);
        imgIcon = (ImageView) findViewById(R.id.imageIcon);
    }
}