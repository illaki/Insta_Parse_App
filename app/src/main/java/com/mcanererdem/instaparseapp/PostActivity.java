package com.mcanererdem.instaparseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class PostActivity extends AppCompatActivity {
    EditText editTextComment;
    ImageView imageViewSelectImage;
    Bitmap chosenBitmap;
    String commentString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imageViewSelectImage = findViewById(R.id.imageView);
        editTextComment = findViewById(R.id.post_activity_editText_comment);
        chosenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.selectimage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery , 2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2  &&  resultCode == RESULT_OK  &&  data != null) {
            Uri myUri = data.getData();
            try {
                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), myUri);
                    chosenBitmap = ImageDecoder.decodeBitmap(source);
                    imageViewSelectImage.setImageBitmap(chosenBitmap);
                }
                else {
                    chosenBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myUri);
                    imageViewSelectImage.setImageBitmap(chosenBitmap);
                }
            }catch (Exception e){ e.printStackTrace();}
        }else {
            Toast.makeText(this,"Image is not selected!",Toast.LENGTH_LONG).show();
            chosenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.selectimage);
        }
    }

    public void selectImage(View view) {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }

    }
    public void upload(View view) {
        //Check If Comment Is Valid
        if (editTextComment.getText().toString().matches("")) {
            commentString = "";
        }else {
            commentString = editTextComment.getText().toString();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        chosenBitmap.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        ParseFile parseFileImage = new ParseFile("image.png",imageByte);
        //Uploading Post

        try {
            ParseObject parseObject = new ParseObject("Posts");
            parseObject.put("username" , ParseUser.getCurrentUser().getUsername());
            parseObject.put("comment" , commentString);
            parseObject.put("image" , parseFileImage);
            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                    }else {
                        Toast.makeText(PostActivity.this, "Upload Successful!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PostActivity.this,FeedActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}