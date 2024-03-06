package com.example.explorar.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.explorar.GlobalVariables;
import com.example.explorar.R;
import com.example.explorar.course.Course;
import com.example.explorar.user.UserData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class IndividualCourseActivity extends AppCompatActivity {
    private Course course;
    private UserData userData;
    private TextView titleTextView;
    private ListView listView;
    private Button button;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course);

        course = (Course) getIntent().getSerializableExtra("course");
        userData = GlobalVariables.getUserData();

        titleTextView = findViewById(R.id.title_text_view);
        listView = findViewById(R.id.list_view);
        button = findViewById(R.id.unenroll_button);

        titleTextView.setText(course.getTitle());
        setUpListView();
        button.setOnClickListener(view -> {
            unenrollCourse();
        });
    }

    private void setUpListView() {
        List<Map<String, Object>> reading = course.getReading();
        List<Map<String, Object>> videos = course.getVideos();
        List<Map<String, Object>> ar = course.getAr();

        addCourseItems(reading);
        addCourseItems(videos);
        addCourseItems(ar);

        items.sort(Comparator.comparing(Item::getIndex));

        ArrayList<String> myCourses = userData.getCourses();
        List<Map<String, Object>> completed = userData.getCompleted();
        ArrayList<Boolean> courseCompletion;
        int pos = 0;
        for (int j=0; j<completed.size(); j++) {
            if (completed.get(j).containsKey(course.getDocId())) {
                pos = j;
            }
        }
        if (myCourses.contains(course.getDocId())) {
            courseCompletion = (ArrayList<Boolean>) userData.getCompleted().get(pos).get(course.getDocId());
            for (int i = 0; i< items.size(); i++) {
                Item item = items.get(i);
                item.setStatus(courseCompletion.get(i));
                items.set(i, item);
            }
        }

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

    private void unenrollCourse() {
        ArrayList<String> courses = userData.getCourses();
        List<Map<String, Object>> completed = userData.getCompleted();

        int index = courses.indexOf(course.getDocId());
        courses.remove(index);
        completed.remove(index);

        userData.setCourses(courses);
        userData.setCompleted(completed);

        FirebaseFirestore.getInstance().collection("users").document(userData.getUserId()).set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                GlobalVariables.setUserData(userData);
                GlobalVariables.setDataDeleted(true);
                finish();
            }
        });
    }
}