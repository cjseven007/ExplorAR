package com.example.explorar.ui.courses;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.explorar.R;
import com.example.explorar.ar.ARActivity;
import com.example.explorar.reading.ReadingActivity;
import com.example.explorar.video.VideoActivity;

import java.util.ArrayList;

public class CourseItemAdapter extends ArrayAdapter<CourseItem> {
    public CourseItemAdapter(@NonNull Context context, ArrayList<CourseItem> courseItems) {
        super(context, R.layout.list_item, courseItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CourseItem courseItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.title_text_view);
        TextView contentTextView = convertView.findViewById(R.id.content_text_view);
        TextView typeTextView = convertView.findViewById(R.id.type_text_view);
        ImageButton imageButton = convertView.findViewById(R.id.image_button);

        titleTextView.setText(courseItem.title);
        contentTextView.setText(courseItem.content);
        typeTextView.setText(courseItem.type);
        switch (courseItem.type) {
            case "AR":
                imageButton.setBackgroundResource(R.drawable.baseline_3d_rotation_24);
                break;
            case  "VIDEO":
                imageButton.setBackgroundResource(R.drawable.baseline_ondemand_video_24);
                break;
            case "READING":
                imageButton.setBackgroundResource(R.drawable.baseline_menu_book_24);
                break;
        }
        Log.println(Log.DEBUG, "AFAKJBVIHBAEVIUABVIUASBVUADBVO", String.valueOf(courseItem.status));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (courseItem.type) {
                    case "AR":
                        intent = new Intent(getContext(), ARActivity.class);
                        intent.putExtra("title", courseItem.title);
                        intent.putExtra("content", courseItem.content);
                        intent.putExtra("status", courseItem.status);
                        intent.putExtra("lowerBound", courseItem.lowerBound);
                        getContext().startActivity(intent);
                        break;
                    case "VIDEO":
                        intent = new Intent(getContext(), VideoActivity.class);
                        intent.putExtra("title", courseItem.title);
                        intent.putExtra("content", courseItem.content);
                        intent.putExtra("status", courseItem.status);
                        intent.putExtra("lowerBound", courseItem.lowerBound);
                        getContext().startActivity(intent);
                        break;
                    case "READING":
                        intent = new Intent(getContext(), ReadingActivity.class);
                        intent.putExtra("title", courseItem.title);
                        intent.putExtra("content", courseItem.content);
                        intent.putExtra("status", courseItem.status);
                        intent.putExtra("lowerBound", courseItem.lowerBound);
                        getContext().startActivity(intent);
                        break;
                }
            }
        });

        return convertView;
    }
}
