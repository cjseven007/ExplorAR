package com.example.explorar.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.explorar.GlobalVariables;
import com.example.explorar.R;
import com.example.explorar.databinding.FragmentProfileBinding;
import com.example.explorar.ui.login.LoginActivity;
import com.example.explorar.user.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

private FragmentProfileBinding binding;
    private TextView textView;
    private TextView userNameTextView;
    private TextView userIdTextView;

    private UserData userData;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userData = GlobalVariables.getUserData();

        userNameTextView = root.findViewById(R.id.user_name);
        userIdTextView = root.findViewById(R.id.user_id);

        userNameTextView.setText(userData.getName());
        userIdTextView.setText(userData.getStudentId());

        TextView textView = root.findViewById(R.id.logout_button);
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
                    GlobalVariables.setUserData(new UserData());
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