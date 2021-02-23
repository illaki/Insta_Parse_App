package com.mcanererdem.instaparseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    ArrayList<String> userNameListFromParse;
    ArrayList<String> commentListFromParse;
    ArrayList<Bitmap> bitmapListFromParse;
    ListView myListView;
    CostumPostAdapter costumPostAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.feed_menu_layout , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.feed_menu_item_add_post) {
            Intent intentToPost = new Intent(FeedActivity.this,PostActivity.class);
            startActivity(intentToPost);
        }
        else if (item.getItemId() == R.id.feed_menu_item_logout) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Toast.makeText(FeedActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intentToSignUp = new Intent(FeedActivity.this, SignUpActivity.class);
                        startActivity(intentToSignUp);
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeed);

        //Initilazing
        myListView = findViewById(R.id.feedactivity_list_view);
        userNameListFromParse = new ArrayList<>();
        commentListFromParse = new ArrayList<>();
        bitmapListFromParse = new ArrayList<>();

        costumPostAdapter = new CostumPostAdapter(userNameListFromParse, commentListFromParse, bitmapListFromParse,this);
        myListView.setAdapter(costumPostAdapter);

        downloadDataFromParse();
    }

    public void downloadDataFromParse() {
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Posts");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }else {

                    if (objects.size() > 0) {
                        for (final ParseObject parseObject : objects) {

                            ParseFile parseFile = (ParseFile) parseObject.get("image");

                            if (parseFile != null) {

                                parseFile.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        if (data.length > 0 && e == null) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0, data.length);

                                            bitmapListFromParse.add(bitmap);
                                            userNameListFromParse.add(parseObject.getString("username"));
                                            commentListFromParse.add(parseObject.getString("comment"));

                                            costumPostAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(getApplicationContext(),"6.downloadDataFromParse() => findInBackground => if() => for()",Toast.LENGTH_LONG).show();
                            }
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"İndirilen Liste Boş",Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }
}