package com.cst2335.sikk0006;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences shrd = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();
        Button save = findViewById(R.id.button3);
        Button clear = findViewById(R.id.button4);
        EditText name = findViewById(R.id.editTextTextPersonName2);
        EditText address = findViewById(R.id.editTextTextEmailAddress);
        TextView email = findViewById(R.id.textView4);

        Intent fromMain = getIntent();
        String mail = fromMain.getStringExtra("EMAIL");
        email.setText(mail);

        name.setText(shrd.getString("Name",""));
        address.setText(shrd.getString("Address", ""));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("Name", name.getText().toString());
                editor.putString("Address", address.getText().toString());
                editor.apply();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();
            }
        });

    }


}




