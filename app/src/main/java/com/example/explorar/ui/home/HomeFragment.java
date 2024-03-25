package com.example.explorar.ui.home;

import static com.google.firebase.firestore.FieldPath.documentId;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorar.GlobalVariables;
import com.example.explorar.R;
import com.example.explorar.databinding.FragmentHomeBinding;
import com.example.explorar.course.Course;
import com.example.explorar.course.CourseAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;

    private CourseAdapter courseAdapter;
    private RecyclerView recyclerView;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

//        MaterialCardView cardView = root.findViewById(R.id.card);
//
//        ShapeAppearanceModel shapeAppearanceModel = cardView.getShapeAppearanceModel().toBuilder()
//                .setTopLeftCorner(new CrazyCornerTreatment())
//                .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
//                .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
//                .build();
//
//        cardView.setShapeAppearanceModel(shapeAppearanceModel);


        setUpRecyclerView();

        return root;
    }

    private void setUpRecyclerView() {
        Query query;
        if (GlobalVariables.getUserData().getCourses().isEmpty()) {
            TextView textView = root.findViewById(R.id.empty_text_view);
            textView.setVisibility(View.VISIBLE);
            query = FirebaseFirestore.getInstance().collection("placeholder");
        } else {
            query = FirebaseFirestore.getInstance().collection("courses").whereIn(documentId(), GlobalVariables.getUserData().getCourses());
        }
        recyclerView = root.findViewById(R.id.recycler_view);
        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>().setQuery(query, Course.class).build();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        courseAdapter = new CourseAdapter(options, getContext());
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (GlobalVariables.isDataChanged()) {
            GlobalVariables.setDataChanged(false);
            courseAdapter.notifyDataSetChanged();
        }
        if (GlobalVariables.isDataDeleted()) {
            GlobalVariables.setDataDeleted(false);
            setUpRecyclerView();
        }
    }
}

