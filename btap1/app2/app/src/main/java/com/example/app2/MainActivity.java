package com.example.app2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btPhoneBook, btPhone;

    private List<Contact> arrContact;
    private ContactAdapter adapter;
    private EditText edtName, edtNumber;
    private Button bntAddContact;
    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create();

        btPhoneBook.setBackgroundResource(0);
        btPhone.setBackgroundResource(0);

        arrContact = new ArrayList<>();
        adapter = new ContactAdapter(MainActivity.this, R.layout.item_contact_lv, arrContact);
        lvContact.setAdapter(adapter);
        clickAddContact();
        checkPermission();
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intentCall(position);
            }
        });
    }

    public void create(){
        btPhoneBook = findViewById(R.id.bt_phone_book);
        btPhone = findViewById(R.id.bt_phone);
        edtName = findViewById(R.id.edt_name);
        edtNumber = findViewById(R.id.edt_number);
        bntAddContact = findViewById(R.id.bnt_add_contact);
        lvContact = findViewById(R.id.lv_contact);
    }

    public void clickAddContact(){
        bntAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { ;
                String name = edtName.getText().toString().trim();
                String number = edtNumber.getText().toString().trim();
                Log.d("test1", "onClick: comeIn" + name);
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(number)){
                    Log.d("test1", "onClick: comeInIf");
                    Toast.makeText(MainActivity.this, "Bạn chưa điền tên hoặc sđt"
                            , Toast.LENGTH_SHORT)
                            .show();
                }else{
                    Contact contact = new Contact(name, number);
                    arrContact.add(contact);
                    edtNumber.setText("");
                    edtName.setText("");
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void checkPermission(){
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
        List<String> listPermissions = new ArrayList<>();
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                listPermissions.add(permission);
            }
        }
        if(!listPermissions.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissions.toArray(new String[listPermissions.size()]), 1);
        }
    }

    public void intentCall(int position){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + arrContact.get(position).getmNumber()));
        startActivity(intent);
    }
}
