package com.example.explorar.ui.courses;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.explorar.R;
import com.example.explorar.ar.ARActivity;
import com.example.explorar.reading.ReadingActivity;
import com.example.explorar.video.VideoActivity;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseItemAdapter extends ArrayAdapter<CourseItem> {
    Context context;
    Courses course;
    public CourseItemAdapter(@NonNull Context context, ArrayList<CourseItem> courseItems, Courses course) {
        super(context, R.layout.list_item, courseItems);
        this.context = context;
        this.course = course;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CourseItem courseItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView idTextView = convertView.findViewById(R.id.id_text_view);
        TextView titleTextView = convertView.findViewById(R.id.title_text_view);
        TextView typeTextView = convertView.findViewById(R.id.type_text_view);
        ImageView imageView = convertView.findViewById(R.id.image_button);
        CheckBox statusCheckBox = convertView.findViewById(R.id.completed_check_box);

        idTextView.setText(String.valueOf(courseItem.index +1));
        titleTextView.setText(courseItem.title);
        typeTextView.setText(courseItem.type);
        statusCheckBox.setChecked(courseItem.status);
        switch (courseItem.type) {
            case "AR":
                imageView.setBackgroundResource(R.drawable.baseline_3d_rotation_24);
                break;
            case  "VIDEO":
                imageView.setBackgroundResource(R.drawable.baseline_ondemand_video_24);
                break;
            case "READING":
                imageView.setBackgroundResource(R.drawable.baseline_menu_book_24);
                break;
        }

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

        statusCheckBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                courseItem.status = statusCheckBox.isChecked();
                Courses newCourse = course;
                switch (courseItem.type) {
                    case "AR":

                        break;
                    case  "VIDEO":

                        break;
                    case "READING":

                        break;
                }


                updateStatus(courseItem);
            }
        });



        return convertView;
    }

    private void updateStatus(CourseItem courseItem){
        FirebaseFirestore.getInstance().collection("courses").document(course.docId).set(courseIt);
    }
}
