package com.example.explorar.ui.courses;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorar.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    protected void onBindViewHolder(@NonNull MyCoursesAdapter.MyCoursesViewHolder holder, int position, @NonNull Courses myCourses) {
        int totalCompleted = 0;
        double percentage = 0;

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
        myCourses.setReading(newReading);

        List<Map<String, Object>> videos = myCourses.getVideos();
        List<Map<String, Object>> newVideos = new ArrayList<>();
        videos.forEach(stringObjectMap -> {
            int index = Integer.valueOf(stringObjectMap.get("index").toString());
            boolean status = courseCompletion.get(index);
            stringObjectMap.replace("status", status);
            newVideos.add(stringObjectMap);
        });
        myCourses.setVideos(newVideos);

        List<Map<String, Object>> ar = myCourses.getAr();
        List<Map<String, Object>> newAr = new ArrayList<>();
        ar.forEach(stringObjectMap -> {
            int index = Integer.valueOf(stringObjectMap.get("index").toString());
            boolean status = courseCompletion.get(index);
            stringObjectMap.replace("status", status);
            newAr.add(stringObjectMap);
        });
        myCourses.setAr(newAr);

        percentage = (totalCompleted*1.0)/((reading.size() + videos.size() + ar.size())*1.0);

        holder.titleTextView.setText(myCourses.title);
        holder.contentTextView.setText(myCourses.content/* + courses.reading.toString() + courses.videos.toString() + courses.ar.toString()*/);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ViewCoursesActivity.class);
            intent.putExtra("course", myCourses);
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
        TextView titleTextView, contentTextView;

        public MyCoursesViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title_text_view);
            contentTextView = itemView.findViewById(R.id.content_text_view);

        }
    }
}
