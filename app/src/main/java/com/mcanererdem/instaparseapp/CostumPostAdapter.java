package com.mcanererdem.instaparseapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
public class CostumPostAdapter extends ArrayAdapter<String> {
    private final ArrayList<String> userNameList;
    private final ArrayList<String> userCommentList;
    private final ArrayList<Bitmap> userBitmapList;
    private final Activity context;

    public CostumPostAdapter(ArrayList<String> userNameList, ArrayList<String> userCommentList, ArrayList<Bitmap> userBitmapList, Activity context) {
        super(context,R.layout.costum_view,userNameList);
        this.userNameList = userNameList;
        this.userCommentList = userCommentList;
        this.userBitmapList = userBitmapList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View costumView = inflater.inflate(R.layout.costum_view, null ,true);

        TextView textViewUserName = costumView.findViewById(R.id.costum_view_textView_username);
        TextView textViewComment = costumView.findViewById(R.id.costum_view_textview_comment);
        ImageView imageView = costumView.findViewById(R.id.costum_view_image_view);

        try {
            textViewUserName.setText(userNameList.get(position));
            textViewComment.setText(userCommentList.get(position));
            imageView.setImageBitmap(userBitmapList.get(position));

        }catch (Exception e) {
            Toast.makeText(context," CostumPostAdapter => getView() \t" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        return costumView;
    }
}
