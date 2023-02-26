package com.cst2335.sikk0006;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/** This class represents the main activity of the application.
 * The activity provides a user interface that allows the user to input a password and checks if the password meets certain requirements.
 * If the password meets all these requirements, a "Your password meets the requirements" message is displayed.
 * Otherwise, a "You shall not pass!" message is displayed.
 * @author vibhu
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This holds the heading text "Type your password here"
     */
    private EditText editText = null;
    /**
     * This holds the text at the centre of the screen
     */
    private TextView textview = null;
    /**
     * This is a button to verify password entered
     */
    private Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        textview = findViewById(R.id.textView);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = editText.getText().toString();

                if (checkPasswordComplexity(password)) {
                    textview.setText("Your password meets the requirements");
                } else {
                    textview.setText("You shall not pass!");
                }
            }
        });
    }

    /** This function has various checks that iterate over each char in the password and returns true/false depending on result.
     * @param password The string object that we are checking
     * @return Returns true if all conditions are passed
     */
    boolean checkPasswordComplexity(String password) {

        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial, lengthValid;
        lengthValid= foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        lengthValid = (password.length() >= 4 && password.length() <= 20);

            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isUpperCase(c)) {
                    foundUpperCase = true;
                } else if (Character.isLowerCase(c)) {
                    foundLowerCase = true;
                } else if (Character.isDigit(c)) {
                    foundNumber = true;
                } else if (isSpecialCharacter(c)) {
                    foundSpecial = true;
                }
            }

            if (!lengthValid) {
                Toast.makeText(this, "Your password must be between 4 and 20 characters long", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!foundUpperCase) {
                Toast.makeText(this, "Your password does not have an upper case letter", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!foundLowerCase) {
                Toast.makeText(this, "Your password does not have a lower case letter", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!foundNumber) {
                Toast.makeText(this, "Your password does not have a number", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!foundSpecial) {
                Toast.makeText(this, "Your password does not have a special symbol", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Toast.makeText(this, "Your password meets all the requirements", Toast.LENGTH_SHORT).show();
                return true;
            }
    }

    /**
     * This function checks for the special characters present in the password.
     * @param c Selected character of String object
     * @return
     */
    public boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}