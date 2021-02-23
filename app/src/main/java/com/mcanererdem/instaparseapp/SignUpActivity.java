package com.mcanererdem.instaparseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextUserName,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUserName = findViewById(R.id.SignUp_Activity_EditText_UserName);
        editTextPassword = findViewById(R.id.SingUp_Activity_EditText_Password);

        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser != null) {
            Intent intentToFeed = new Intent(SignUpActivity.this, FeedActivity.class);
            startActivity(intentToFeed);
            finish();
        }

    }

    public void signup(View view) {
        if (editTextUserName.getText().toString() != null  && !editTextUserName.getText().toString().matches("")) {
            if (editTextPassword.getText().toString() != null && !editTextPassword.getText().toString().matches("")) {

                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(editTextUserName.getText().toString());
                parseUser.setPassword(editTextPassword.getText().toString());
                parseUser.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
                            Intent intentToFeed = new Intent(SignUpActivity.this, FeedActivity.class);
                            startActivity(intentToFeed);
                            finish();
                        }
                    }
                });
            }else { Toast.makeText(SignUpActivity.this,"Password Invalid!",Toast.LENGTH_LONG).show();}
        } else {Toast.makeText(SignUpActivity.this,"Username Invalid!" , Toast.LENGTH_LONG).show();}
    }


    public void signin(View view) {
        if (editTextUserName.getText().toString() != null  && !editTextUserName.getText().toString().matches("")) {
            if (editTextPassword.getText().toString() != null && !editTextPassword.getText().toString().matches("")) {
                ParseUser.logInInBackground(editTextUserName.getText().toString(), editTextPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e != null) {
                            Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Sign In Successful", Toast.LENGTH_LONG).show();
                            Intent intentToFeed = new Intent(SignUpActivity.this, FeedActivity.class);
                            startActivity(intentToFeed);
                            finish();
                        }
                    }
                });

            }else { Toast.makeText(SignUpActivity.this,"Password Invalid!",Toast.LENGTH_LONG).show();}
        } else {Toast.makeText(SignUpActivity.this,"Username Invalid!" , Toast.LENGTH_LONG).show();}
    }
}