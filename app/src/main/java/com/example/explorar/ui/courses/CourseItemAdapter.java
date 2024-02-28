package com.example.explorar.ui.courses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.explorar.R;
import com.example.explorar.reading.ReadingActivity;

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

        titleTextView.setText(courseItem.title);
        contentTextView.setText(courseItem.content);
        typeTextView.setText(courseItem.type);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReadingActivity.class);
                intent.putExtra("content", courseItem.content);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
