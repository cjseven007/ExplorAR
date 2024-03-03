package com.example.explorar.ui.courses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.explorar.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ViewCoursesActivity extends AppCompatActivity {
    private Courses course;
    private TextView titleTextView;
    private ListView listView;
    private CourseItemAdapter courseItemAdapter;
    private ArrayList<CourseItem> courseItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);

        course = (Courses) getIntent().getSerializableExtra("course");

        titleTextView = findViewById(R.id.title_text_view);
        titleTextView.setText(course.title);

        listView = findViewById(R.id.list_view);

        List<Map<String, Object>> reading = course.getReading();
        List<Map<String, Object>> videos = course.getVideos();
        List<Map<String, Object>> ar = course.getAr();

        addCourseItems(reading);
        addCourseItems(videos);
        addCourseItems(ar);

        courseItems.sort(Comparator.comparing(CourseItem::getIndex));

        courseItemAdapter = new CourseItemAdapter(ViewCoursesActivity.this, courseItems);

        listView.setAdapter(courseItemAdapter);
    }

    private void addCourseItems(List<Map<String, Object>> mapList) {
        mapList.forEach(stringObjectMap -> {
            String title = stringObjectMap.get("title").toString();
            String content = stringObjectMap.get("content").toString();
            String type = stringObjectMap.get("type").toString();
            int index = Integer.valueOf(stringObjectMap.get("index").toString());

            CourseItem courseItem = new CourseItem();
            courseItem.setTitle(title);
            courseItem.setContent(content);
            courseItem.setType(type);
            courseItem.setIndex(index);

            courseItems.add(courseItem);
        });
    }
}