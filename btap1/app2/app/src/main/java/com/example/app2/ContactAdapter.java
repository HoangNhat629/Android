package com.example.app2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;
    private List<Contact> arrContact;
    public ContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this. resource = resource;
        this.arrContact = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_lv, parent, false);
            viewHolder.ivAvatar = convertView.findViewById(R.id.iv_image);
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvNumber = convertView.findViewById(R.id.tv_number);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = arrContact.get(position);

        viewHolder.ivAvatar.setBackgroundResource(R.drawable.ic_person_black_24dp);
        viewHolder.tvName.setText(contact.getmName());
        viewHolder.tvNumber.setText(contact.getmNumber());
        return convertView;
    }

    public class ViewHolder{
        ImageView ivAvatar;
        TextView tvName;
        TextView tvNumber;
    }

}