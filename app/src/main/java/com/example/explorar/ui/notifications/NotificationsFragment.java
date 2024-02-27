package com.example.explorar.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.explorar.R;
import com.example.explorar.databinding.FragmentNotificationsBinding;
import com.example.explorar.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

private FragmentNotificationsBinding binding;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Button button;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //example code for firebase
        button = root.findViewById(R.id.get_data_button);
        textView = root.findViewById(R.id.placeholder_text_view);
        ArrayList<String> textList = new ArrayList<String>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("com/example/explorar/ui/courses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                textList.add(document.toString());
                            }
                        } else {
                            Toast.makeText(getContext(),
                                            "Get data failed",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                        String text = "";
                        for (String str : textList) {
                            text = text + str;
                        }
                        textView.setText(text);
                    }
                });
            }
        });
        // firebase code ends here

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth;
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(getContext(),
                                    "Logout failed",
                                    Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getContext(),
                                    "Logout successful",
                                    Toast.LENGTH_LONG)
                            .show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}