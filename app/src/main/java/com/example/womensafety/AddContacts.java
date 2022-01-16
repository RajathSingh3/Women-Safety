package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;


public class AddContacts extends AppCompatActivity {

    TextInputLayout txtName, txtPhone;
    Button btnAdd, btnView;
    DBHelper db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontacts);
        txtName = (TextInputLayout) findViewById(R.id.textName);
        txtPhone = (TextInputLayout) findViewById(R.id.textPhone);
        btnAdd = (Button) findViewById(R.id.addButton);
        btnView = (Button) findViewById(R.id.viewButton);
        db = new DBHelper(this);
        String UniqueID = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getEditText().getText().toString();
                String phone = txtPhone.getEditText().getText().toString();
                Boolean res1 = validateName(name);
                Boolean res2 = validatePhoneNumber(phone);
                if((res1 == true && res2 == true)) {
                    boolean checkInsertData = db.insertUserData(name, phone);
                    if (checkInsertData == true)
                        Toast.makeText(AddContacts.this, "New Entry Has Been Inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddContacts.this, "New Entry Has Not Been Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Cursor res = db.getUserData();
//                if (res.getCount()==0) {
//                    Toast.makeText(AddContacts.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                StringBuffer buffer = new StringBuffer();
//                while(res.moveToNext()){
//                    buffer.append("ID : "+res.getString(0)+"\n");
//                    buffer.append("NAME : "+res.getString(1)+"\n");
//                    buffer.append("CONTACT NUMBER : "+res.getString(2)+"\n\n");
//                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(AddContacts.this);
//                builder.setCancelable(true);
//                builder.setTitle("Emergency Contacts");
//                builder.setMessage(buffer.toString());
//                builder.show();
                startActivity(new Intent(getApplicationContext(),ViewContacts.class));
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateName(@NonNull String name) {
        if (name.isEmpty()) {
            txtName.setError("Field can not be empty");
            return false;
        } else {
            txtName.setError(null);
            txtName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhoneNumber(@NonNull String phone) {
        if (phone.isEmpty()) {
            txtPhone.setError("Enter valid phone number");
            return false;
        }else if(Pattern.matches("[a-zA-Z]+", phone)) {
            txtPhone.setError("Enter only numbers!");
            return false;
        }
            else if (phone.length()!=10){
                txtPhone.setError("Phone Number length should be 10");
                return false;
        } else {
            txtPhone.setError(null);
            txtPhone.setErrorEnabled(false);
            return true;
        }
    }

}
