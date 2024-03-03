package com.example.explorar.ui.courses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorar.R;
import com.example.explorar.ui.user.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyCoursesAdapter extends FirestoreRecyclerAdapter<Courses, MyCoursesAdapter.MyCoursesViewHolder> {

    Context context;
    List<Map<String, Object>> completed;

    public MyCoursesAdapter(@NonNull FirestoreRecyclerOptions<Courses> options, Context context, List<Map<String, Object>> completed) {
        super(options);
        this.context = context;
        this.completed = completed;
    }

    public static String truncateAndAddEllipsis(String input, int maxLength) {
        if (input.length() > maxLength) {
            return input.substring(0, maxLength) + "...";
        } else {
            return input;
        }
    }
    @Override
    protected void onBindViewHolder(@NonNull MyCoursesAdapter.MyCoursesViewHolder holder, int position, @NonNull Courses myCourses) {
        int totalCompleted = 0;
        float percentage = 0.0f;
        Courses newMyCourses = myCourses;

        ArrayList<Boolean> courseCompletion = (ArrayList<Boolean>) completed.get(0).get(myCourses.docId);
        for (int i=0; i<courseCompletion.size(); i++) {
            if (courseCompletion.get(i)) {
                totalCompleted++;
            }
        }

        List<Map<String, Object>> reading = myCourses.getReading();
        List<Map<String, Object>> newReading = new ArrayList<>();
        reading.forEach(stringObjectMap -> {
            int index = Integer.valueOf(stringObjectMap.get("index").toString());
            boolean status = courseCompletion.get(index);
            stringObjectMap.replace("status", status);
            newReading.add(stringObjectMap);
        });
        newMyCourses.setReading(newReading);

        List<Map<String, Object>> videos = myCourses.getVideos();
        List<Map<String, Object>> newVideos = new ArrayList<>();
        videos.forEach(stringObjectMap -> {
            int index = Integer.valueOf(stringObjectMap.get("index").toString());
            boolean status = courseCompletion.get(index);
            stringObjectMap.replace("status", status);
            newVideos.add(stringObjectMap);
        });
        newMyCourses.setVideos(newVideos);

        List<Map<String, Object>> ar = myCourses.getAr();
        List<Map<String, Object>> newAr = new ArrayList<>();
        ar.forEach(stringObjectMap -> {
            int index = Integer.valueOf(stringObjectMap.get("index").toString());
            boolean status = courseCompletion.get(index);
            stringObjectMap.replace("status", status);
            newAr.add(stringObjectMap);
        });
        newMyCourses.setAr(newAr);

        percentage = (totalCompleted*1.0f)/((reading.size() + videos.size() + ar.size())*1.0f);
        User user = new User();
        user.setCourseCompletion(courseCompletion);

        holder.titleTextView.setText(myCourses.title);
        holder.contentTextView.setText(truncateAndAddEllipsis(myCourses.content, 80));
        holder.progressBar.setMax(100);
        holder.progressBar.setProgress(Math.round(percentage*100.0f));
        holder.percentageTextView.setText(Math.round(percentage*100.0f) +"%");
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ViewCoursesActivity.class);
            intent.putExtra("course", newMyCourses);
            intent.putExtra("courseCompletion", user);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public MyCoursesAdapter.MyCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_courses_layout, parent, false);
        return new MyCoursesAdapter.MyCoursesViewHolder(view);
    }

    static class MyCoursesViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView, percentageTextView;
        ProgressBar progressBar;

        public MyCoursesViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title_text_view);
            contentTextView = itemView.findViewById(R.id.content_text_view);
            percentageTextView = itemView.findViewById(R.id.percentage_text_view);
            progressBar = itemView.findViewById(R.id.course_progress_bar);
        }
    }
}
