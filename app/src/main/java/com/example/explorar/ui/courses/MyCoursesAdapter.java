package com.example.explorar.ui.courses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorar.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MyCoursesAdapter extends FirestoreRecyclerAdapter<Courses, MyCoursesAdapter.MyCoursesViewHolder> {

    Context context;

    public MyCoursesAdapter(@NonNull FirestoreRecyclerOptions<Courses> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyCoursesAdapter.MyCoursesViewHolder holder, int position, @NonNull Courses myCourses) {
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
