package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener,View.OnKeyListener {
    EditText users,pass;
    TextView change;
    Boolean check=true;
    Button press;
    ImageView pic;
    ConstraintLayout lay;
   public void active(){
        Intent intent=new Intent(getApplicationContext(),listActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN)
            signUp(v);
        return false;
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.textView) {
            if (check) {
                check = false;
                change.setText("or, sign up");
                press.setText("Login");
            } else {
                check = true;
                change.setText("or login");
                press.setText("sign up");
            }
        }
        else if(v.getId()==R.id.imageView||v.getId()==R.id.coed){
            InputMethodManager press=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            press.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }

    }
    public void signUp(View view ){
        InputMethodManager press=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        press.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        if(users.getText().toString().matches("")||pass.getText().toString().matches("")){
            Toast.makeText(MainActivity.this,"please enter a valid username or password",Toast.LENGTH_SHORT).show();
        }
        else{
            if(check) {
                ParseUser user = new ParseUser();
                user.setUsername(users.getText().toString());
                user.setPassword(pass.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("text", "signed up");
                            Toast.makeText(MainActivity.this, "Successfully signed up, Login back", Toast.LENGTH_SHORT).show();
                            users.setText("");
                            pass.setText("");
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                ParseUser.logInInBackground(users.getText().toString(), pass.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null){
                            Log.i("text","Logging in");
                            active();
                        }
                        else{
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        users=findViewById(R.id.editTextTextPersonName);
        pass=findViewById(R.id.editTextTextPersonName2);
        change=findViewById(R.id.textView);
        change.setOnClickListener(this);
        press=findViewById(R.id.button);
        pic=findViewById(R.id.imageView);
        pic.setOnClickListener(this);
        lay=findViewById(R.id.coed);
        lay.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!=null){
           active();
        }
    }



}