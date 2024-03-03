package com.example.explorar.ui.courses;

import com.example.explorar.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.explorar.ui.user.UserData;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoursesAdapter extends FirestoreRecyclerAdapter<Courses, CoursesAdapter.CoursesViewHolder> {
    Context context;
    UserData userData;

    public CoursesAdapter(@NonNull FirestoreRecyclerOptions<Courses> options, Context context, UserData userData) {
        super(options);
        this.context = context;
        this.userData = userData;
    }

    public static String truncateAndAddEllipsis(String input, int maxLength) {
        if (input.length() > maxLength) {
            return input.substring(0, maxLength) + "...";
        } else {
            return input;
        }
    }
    @Override
    protected void onBindViewHolder(@NonNull CoursesViewHolder holder, int position, @NonNull Courses courses) {
        List<Map<String, Object>> reading = courses.getReading();
        List<Map<String, Object>> videos = courses.getVideos();
        List<Map<String, Object>> ar = courses.getAr();

        int totalItems = reading.size() + videos.size() + ar.size();

        ArrayList<String> myCourses = userData.getMyCourses();
        List<Map<String, Object>> completed = userData.getCompleted();

        if (myCourses.contains(courses.docId)) {
            int totalCompleted = 0;
            float percentage = 0.0f;

            ArrayList<Boolean> courseCompletion = (ArrayList<Boolean>) completed.get(0).get(courses.docId);
            for (int i=0; i<courseCompletion.size(); i++) {
                if (courseCompletion.get(i)) {
                    totalCompleted++;
                }
            }

            percentage = (totalCompleted*1.0f)/(totalItems*1.0f);

            holder.titleTextView.setText(courses.title);
            holder.contentTextView.setText(truncateAndAddEllipsis(courses.content, 80));
            holder.progressBar.setMax(100);
            holder.progressBar.setProgress(Math.round(percentage*100.0f));
            holder.percentageTextView.setText(Math.round(percentage*100.0f) +"%");
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, ViewCoursesActivity.class);
                intent.putExtra("course", courses);
                intent.putExtra("userData", userData);
                context.startActivity(intent);
            });
        } else {
            holder.progressBarLinearLayout.setVisibility(View.GONE);
            holder.titleTextView.setText(courses.title);
            holder.contentTextView.setText(truncateAndAddEllipsis(courses.content, 80));
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, ViewCoursesActivity.class);
                intent.putExtra("course", courses);
                intent.putExtra("userData", userData);
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_layout, parent, false);
        return new CoursesViewHolder(view);
    }

    static class CoursesViewHolder extends RecyclerView.ViewHolder {
        LinearLayout progressBarLinearLayout;
        TextView titleTextView, contentTextView, percentageTextView;
        ProgressBar progressBar;

        public CoursesViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBarLinearLayout = itemView.findViewById(R.id.progress_bar_linear_layout);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            contentTextView = itemView.findViewById(R.id.content_text_view);
            percentageTextView = itemView.findViewById(R.id.percentage_text_view);
            progressBar = itemView.findViewById(R.id.course_progress_bar);

        }
    }
}
