package com.example.instagram;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class listActivity extends AppCompatActivity {
    ListView names;
    public void getPhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Uri img=data.getData();
        if(requestCode==1&&resultCode==RESULT_OK){
            try{
                Bitmap Image=MediaStore.Images.Media.getBitmap(this.getContentResolver(),img);
                ByteArrayOutputStream setter=new ByteArrayOutputStream();
                Image.compress(Bitmap.CompressFormat.JPEG,100,setter);
                byte[] Byte=setter.toByteArray();
                ParseFile file =new ParseFile("image.png",Byte);
                ParseObject good=new ParseObject("Image");
                good.put("image",file);
                good.put("username",ParseUser.getCurrentUser().getUsername());
                good.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(listActivity.this,"Image shared",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            e.printStackTrace();
                            Toast.makeText(listActivity.this,"An error occurred",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.shared,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.shareid)
        {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                getPhoto();
            }
        }
        else if(item.getItemId()==R.id.logout){
            ParseUser.logOut();
            Intent revert=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(revert);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("User Feed");
        names = findViewById(R.id.list);
        final ArrayList<String> userNames = new ArrayList<>();
        final ArrayAdapter<String> going = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNames);
        names.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inert = new Intent(getApplicationContext(), pictures.class);
                inert.putExtra("username", userNames.get(position));
                startActivity(inert);
            }
        });
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByAscending("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseUser name : objects) {
                            userNames.add(name.getUsername());
                        }
                        names.setAdapter(going);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

    }
}