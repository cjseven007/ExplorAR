package com.example.explorar.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorar.R;
import com.example.explorar.databinding.FragmentDashboardBinding;
import com.example.explorar.ui.courses.Courses;
import com.example.explorar.ui.courses.CoursesAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DashboardFragment extends Fragment {

private FragmentDashboardBinding binding;

    private RecyclerView recyclerView;
    private CoursesAdapter coursesAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recycler_view);
        Query query = FirebaseFirestore.getInstance().collection("courses");
        FirestoreRecyclerOptions<Courses> options = new FirestoreRecyclerOptions.Builder<Courses>().setQuery(query, Courses.class).build();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        coursesAdapter = new CoursesAdapter(options, getContext());
        recyclerView.setAdapter(coursesAdapter);
        coursesAdapter.startListening();
        coursesAdapter.notifyDataSetChanged();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        coursesAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        coursesAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        coursesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}