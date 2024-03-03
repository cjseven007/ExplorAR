package com.example.explorar.ui.courses;

import com.example.explorar.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CoursesAdapter extends FirestoreRecyclerAdapter<Courses, CoursesAdapter.CoursesViewHolder> {
    Context context;

    public CoursesAdapter(@NonNull FirestoreRecyclerOptions<Courses> options, Context context) {
        super(options);
        this.context = context;
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
        holder.titleTextView.setText(courses.title);

        holder.contentTextView.setText(truncateAndAddEllipsis(courses.content, 80));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ViewCoursesActivity.class);
            intent.putExtra("course", courses);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_layout, parent, false);
        return new CoursesViewHolder(view);
    }

    static class CoursesViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView;

        public CoursesViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title_text_view);
            contentTextView = itemView.findViewById(R.id.content_text_view);

        }
    }
}
