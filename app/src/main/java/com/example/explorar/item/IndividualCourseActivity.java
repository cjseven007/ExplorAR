package com.example.explorar.item;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.explorar.GlobalVariables;
import com.example.explorar.R;
import com.example.explorar.course.Course;
import com.example.explorar.user.UserData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class IndividualCourseActivity extends AppCompatActivity {
    private Course course;
    private UserData userData;
    private TextView titleTextView;
    private ListView listView;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course);

        course = (Course) getIntent().getSerializableExtra("course");
        userData = GlobalVariables.getUserData();

        titleTextView = findViewById(R.id.title_text_view);
        titleTextView.setText(course.getTitle());

        listView = findViewById(R.id.list_view);

        List<Map<String, Object>> reading = course.getReading();
        List<Map<String, Object>> videos = course.getVideos();
        List<Map<String, Object>> ar = course.getAr();

        addCourseItems(reading);
        addCourseItems(videos);
        addCourseItems(ar);

        ArrayList<String> myCourses = userData.getMyCourses();
        if (myCourses.contains(course.getDocId())) {
            ArrayList<Boolean> courseCompletion = (ArrayList<Boolean>) userData.getCompleted().get(0).get(course.getDocId());
            for (int i = 0; i< items.size(); i++) {
                Item item = items.get(i);
                item.setStatus(courseCompletion.get(i));
                items.set(i, item);
            }
        }

        items.sort(Comparator.comparing(Item::getIndex));

        itemAdapter = new ItemAdapter(IndividualCourseActivity.this, items, course);

        listView.setAdapter(itemAdapter);
    }

    private void addCourseItems(List<Map<String, Object>> mapList) {
        mapList.forEach(stringObjectMap -> {
            String title = stringObjectMap.get("title").toString();
            String content = stringObjectMap.get("content").toString();
            String type = stringObjectMap.get("type").toString();
            int index = Integer.valueOf(stringObjectMap.get("index").toString());

            Item item = new Item();
            item.setTitle(title);
            item.setContent(content);
            item.setType(type);
            item.setIndex(index);

            items.add(item);
        });
    }
}