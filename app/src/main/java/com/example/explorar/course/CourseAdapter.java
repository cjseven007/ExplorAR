package com.example.explorar.course;

import com.example.explorar.GlobalVariables;
import com.example.explorar.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorar.item.IndividualCourseActivity;
import com.example.explorar.user.UserData;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseAdapter extends FirestoreRecyclerAdapter<Course, CourseAdapter.CoursesViewHolder> {
    Context context;
    UserData userData;

    public CourseAdapter(@NonNull FirestoreRecyclerOptions<Course> options, Context context) {
        super(options);
        this.context = context;
        this.userData = GlobalVariables.getUserData();
    }

    public static String truncateAndAddEllipsis(String input, int maxLength) {
        if (input.length() > maxLength) {
            return input.substring(0, maxLength) + "...";
        } else {
            return input;
        }
    }
    @Override
    protected void onBindViewHolder(@NonNull CoursesViewHolder holder, int position, @NonNull Course course) {
        List<Map<String, Object>> reading = course.getReading();
        List<Map<String, Object>> videos = course.getVideos();
        List<Map<String, Object>> ar = course.getAr();

        int totalItems = reading.size() + videos.size() + ar.size();

        ArrayList<String> myCourses = userData.getCourses();
        List<Map<String, Object>> completed = userData.getCompleted();

        if (myCourses.contains(course.docId)) {
            int totalCompleted = 0;
            float percentage = 0.0f;

            ArrayList<Boolean> courseCompletion;
            int pos = 0;
            for (int j=0; j<completed.size(); j++) {
                if (completed.get(j).containsKey(course.getDocId())) {
                    pos = j;
                }
            }

            courseCompletion = (ArrayList<Boolean>) completed.get(pos).get(course.docId);
            for (int i=0; i<courseCompletion.size(); i++) {
                if (courseCompletion.get(i)) {
                    totalCompleted++;
                }
            }

            percentage = (totalCompleted*1.0f)/(totalItems*1.0f);

            holder.titleTextView.setText(course.getTitle());
            holder.contentTextView.setText(truncateAndAddEllipsis(course.getContent(), 80));
            holder.progressBar.setMax(100);
            holder.progressBar.setProgress(Math.round(percentage*100.0f));
            holder.percentageTextView.setText(Math.round(percentage*100.0f) +"%");
            holder.registerButtonLinearLayout.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, IndividualCourseActivity.class);
                intent.putExtra("course", course);
                context.startActivity(intent);
            });
        } else {
            holder.progressBarLinearLayout.setVisibility(View.GONE);
            holder.titleTextView.setText(course.getTitle());
            holder.contentTextView.setText(truncateAndAddEllipsis(course.getContent(), 80));
            holder.progressBarLinearLayout.setVisibility(View.GONE);

            holder.registerButton.setOnClickListener(view -> {
                registerCourse(course);
                holder.registerButtonLinearLayout.setVisibility(View.GONE);
                holder.progressBarLinearLayout.setVisibility(View.VISIBLE);
                holder.progressBar.setMax(100);
                holder.progressBar.setProgress(0);
                holder.percentageTextView.setText("0%");
            });
        }
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_layout, parent, false);
        return new CoursesViewHolder(view);
    }

    static class CoursesViewHolder extends RecyclerView.ViewHolder {
        LinearLayout progressBarLinearLayout, registerButtonLinearLayout;
        TextView titleTextView, contentTextView, percentageTextView;
        ProgressBar progressBar;
        Button registerButton;

        public CoursesViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBarLinearLayout = itemView.findViewById(R.id.progress_bar_linear_layout);
            registerButtonLinearLayout = itemView.findViewById(R.id.register_button_linear_layout);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            contentTextView = itemView.findViewById(R.id.content_text_view);
            percentageTextView = itemView.findViewById(R.id.percentage_text_view);
            progressBar = itemView.findViewById(R.id.course_progress_bar);
            registerButton = itemView.findViewById(R.id.register_button);
        }
    }

    private void registerCourse(Course course) {
        ArrayList<String> courses = userData.getCourses();
        List<Map<String, Object>> completed = userData.getCompleted();

        int lengthArr = course.getReading().size() + course.getVideos().size() + course.getAr().size();
        ArrayList<Boolean> courseCompletion = new ArrayList<>(lengthArr);
        courseCompletion.addAll(Collections.nCopies(lengthArr, Boolean.FALSE));
        Map<String, Object> courseCompletionMap = new HashMap<>();
        courseCompletionMap.put(course.getDocId(), courseCompletion);

        courses.add(course.getDocId());
        completed.add(courseCompletionMap);

        userData.setCourses(courses);
        userData.setCompleted(completed);

        GlobalVariables.setUserData(userData);

        FirebaseFirestore.getInstance().collection("users").document(userData.getUserId()).set(userData);
    }
}
