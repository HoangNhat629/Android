package com.example.appweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Thoitiet> arrayList;

    public CustomAdapter(Context context, ArrayList<Thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_view, null);

        Thoitiet thoitiet = arrayList.get(i);
        TextView txtDay = (TextView) view.findViewById(R.id.textviewngay);
        TextView txtStatus = (TextView) view.findViewById(R.id.textviewtrangthai);
        TextView txtMaxTemp = (TextView) view.findViewById(R.id.textviewMaxTemp);
        TextView txtMinTemp = (TextView) view.findViewById(R.id.textviewMinTemp);
        ImageView imgStatus =(ImageView) view.findViewById(R.id.imageviewtrangthai);

        txtDay.setText(thoitiet.Day);
        txtStatus.setText(thoitiet.Status);
        txtMaxTemp.setText(thoitiet.Maxtemp + "*C");
        txtMinTemp.setText(thoitiet.Mintemp + "*c");

        Picasso.with(context).load("http://openweathermap.org/img/wn/" + thoitiet.Image + ".png").into(imgStatus);
        return view;
    }
}