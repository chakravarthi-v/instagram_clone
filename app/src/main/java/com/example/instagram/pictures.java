package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class pictures extends AppCompatActivity {
    LinearLayout grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        grid=findViewById(R.id.lin);
        Intent from=getIntent();
        String username=from.getStringExtra("username");
        setTitle(username+"'s photos");
        Toast.makeText(this,"load1",Toast.LENGTH_SHORT).show();
        ParseQuery<ParseObject> check= new ParseQuery<>("Image");
        check.whereEqualTo("username",username);
        check.orderByDescending("createdAt");
        check.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                  
                    for(ParseObject imgs:objects){
                        ParseFile folder=(ParseFile) imgs.get("image");
                       
                        assert folder != null;
                        folder.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                
                                if(e==null&&data!=null){
                                    
                                    Bitmap bit= BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView pico=new ImageView(getApplicationContext());
                                    pico.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                                    pico.setImageBitmap(bit);
                                    grid.addView(pico);
                               
                                }
                                else{
                                    assert e != null;
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                }
                else{
                    e.printStackTrace();
                }
            }
        });

    }
}
