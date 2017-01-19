package com.cs3714.sojo.proj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
// login activity that identify each user and connect with server

public class LoginActivity extends Activity {

    Button loginB;
    EditText idField;
    EditText pwField;
    String username;
    String pw;
    Button signUpB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseObject testObject = new ParseObject("TestObject");

        testObject.put("foo", "bar");
        testObject.saveInBackground();

        setContentView(R.layout.loginactivity);



        signUpB = (Button) findViewById(R.id.signupB);
        loginB = (Button) findViewById(R.id.loginB);
        idField = (EditText) findViewById(R.id.idEdit);
        pwField = (EditText) findViewById(R.id.pwEdit);
// this button allow user access to the cook book

        loginB.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                username = idField.getText().toString();
                pw = pwField.getText().toString();

                if (!username.equals("") || !pw.equals("")){
                ParseUser.logInInBackground(username, pw, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.abc_fade_in, R.anim.fade_out);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "login wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
                                      }
                                  }

        );
        //when user does not have id it request signup

        signUpB.setOnClickListener(new View.OnClickListener()

                                   {

                                       @Override
                                       public void onClick (View v){
                                           Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                                           startActivity(intent);
                                           overridePendingTransition(R.anim.abc_fade_in, R.anim.fade_out);


                                       }
                                   }

        );

    }



}