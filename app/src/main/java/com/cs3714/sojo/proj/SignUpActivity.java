package com.cs3714.sojo.proj;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by SOJO on 8/10/15.
 */
public class SignUpActivity extends Activity {
Button processB;
    EditText idField;
    EditText pwField;
    EditText emailField;
    String username;
    String pw;
    String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        processB = (Button) findViewById(R.id.processB);
        idField = (EditText) findViewById(R.id.idField);
        pwField = (EditText) findViewById(R.id.pwField);
        emailField = (EditText) findViewById(R.id.emailField);

        processB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = idField.getText().toString();
                pw = pwField.getText().toString();
                email = emailField.getText().toString();

                if (!username.equals("") && !pw.equals("") && !email.equals("")) {
                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(pw);
                    user.setEmail(email);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
                else{
                    Toast.makeText(getApplicationContext(), "Please fill required fields", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
