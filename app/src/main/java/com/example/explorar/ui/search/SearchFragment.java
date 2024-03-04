package com.example.explorar.ui.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorar.R;
import com.example.explorar.databinding.FragmentSearchBinding;
import com.example.explorar.course.Course;
import com.example.explorar.course.CourseAdapter;
import com.example.explorar.user.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {

private FragmentSearchBinding binding;

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Query query = FirebaseFirestore.getInstance().collection("courses");
        recyclerView = root.findViewById(R.id.recycler_view);
        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>().setQuery(query, Course.class).build();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        courseAdapter = new CourseAdapter(options, getContext());
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.startListening();
        courseAdapter.notifyDataSetChanged();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        courseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        courseAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        courseAdapter.notifyDataSetChanged();
    }
}