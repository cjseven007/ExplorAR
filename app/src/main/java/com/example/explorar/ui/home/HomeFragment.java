package com.example.explorar.ui.home;

import static com.google.firebase.firestore.FieldPath.documentId;

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
import com.example.explorar.databinding.FragmentHomeBinding;
import com.example.explorar.ui.courses.Courses;
import com.example.explorar.ui.courses.MyCoursesAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;

    RecyclerView recyclerView;
    MyCoursesAdapter myCoursesAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> myCourses = (ArrayList<String>) task.getResult().get("courses");

                Query query = FirebaseFirestore.getInstance().collection("courses").whereIn(documentId(), myCourses);

                recyclerView = root.findViewById(R.id.recycler_view);
                FirestoreRecyclerOptions<Courses> options = new FirestoreRecyclerOptions.Builder<Courses>().setQuery(query, Courses.class).build();
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                myCoursesAdapter = new MyCoursesAdapter(options, getContext());
                recyclerView.setAdapter(myCoursesAdapter);
                myCoursesAdapter.startListening();
                myCoursesAdapter.notifyDataSetChanged();
            }
        });





//        listView = root.findViewById(R.id.module_list_view);
//
//        ArrayAdapter<String> arr = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, tutorials);
//        listView.setAdapter(arr);
        return root;
    }

}

