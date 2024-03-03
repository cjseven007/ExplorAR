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
import com.example.explorar.ui.user.UserData;
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

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> myCourses = (ArrayList<String>) task.getResult().get("courses");
                List<Map<String, Object>> completed = (List<Map<String, Object>>) task.getResult().get("completed");
                UserData userData = new UserData();
                userData.setMyCourses(myCourses);
                userData.setCompleted(completed);

                Query query = FirebaseFirestore.getInstance().collection("courses");
                recyclerView = root.findViewById(R.id.recycler_view);
                FirestoreRecyclerOptions<Courses> options = new FirestoreRecyclerOptions.Builder<Courses>().setQuery(query, Courses.class).build();
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                coursesAdapter = new CoursesAdapter(options, getContext(), userData);
                recyclerView.setAdapter(coursesAdapter);
                coursesAdapter.startListening();
                coursesAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }
}