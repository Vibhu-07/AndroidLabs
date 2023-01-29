package com.cst2335.sikk0006;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.widget.CompoundButton;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Switch switch3;
@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_constraint);
        button = findViewById(R.id.button);
        switch3=findViewById(R.id.switch1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Here is more Information", Toast.LENGTH_LONG).show();
            }
        });
    boolean originalState = switch3.isChecked();
    switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String message = "The switch is now " + (isChecked ? "on" : "off");
            Snackbar snackbar = Snackbar.make(buttonView, message, Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch3.setChecked(originalState);
                        }
                    });
            snackbar.show();
        }
    } );
    }
}