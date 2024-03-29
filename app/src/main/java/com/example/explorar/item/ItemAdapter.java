package com.example.explorar.item;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.explorar.GlobalVariables;
import com.example.explorar.R;
import com.example.explorar.ui.ar.ARActivity;
import com.example.explorar.ui.reading.ReadingActivity;
import com.example.explorar.course.Course;
import com.example.explorar.ui.video.VideoActivity;
import com.example.explorar.user.UserData;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemAdapter extends ArrayAdapter<Item> implements ItemCategory {
    Course course;
    UserData userData;
    public ItemAdapter(@NonNull Context context, ArrayList<Item> items, Course course) {
        super(context, R.layout.list_item, items);
        this.course = course;
        this.userData = GlobalVariables.getUserData();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView idTextView = convertView.findViewById(R.id.id_text_view);
        TextView titleTextView = convertView.findViewById(R.id.title_text_view);
        TextView typeTextView = convertView.findViewById(R.id.type_text_view);
        ImageView imageView = convertView.findViewById(R.id.image_button);
        CheckBox statusCheckBox = convertView.findViewById(R.id.completed_check_box);

        idTextView.setText(String.valueOf(item.index +1));
        titleTextView.setText(item.getTitle());
        typeTextView.setText(item.type);
        statusCheckBox.setChecked(item.status);
        switch (item.type) {
            case "AR":
                imageView.setBackgroundResource(R.drawable.baseline_3d_rotation_24);
                break;
            case  "VIDEO":
                imageView.setBackgroundResource(R.drawable.baseline_ondemand_video_24);
                break;
            case "READING":
                imageView.setBackgroundResource(R.drawable.baseline_menu_book_24);
                break;
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(getIntent(item));
            }
        });

        statusCheckBox.setOnClickListener(view -> {
            item.status = statusCheckBox.isChecked();

            updateStatus(item, item.status);
        });

        return convertView;
    }

    private void updateStatus(Item item, boolean newStatus){
        String courseId = course.getDocId();
        List<Map<String, Object>> completed = userData.getCompleted();
        ArrayList<Boolean> courseCompletion;
        int pos = 0;
        for (int j=0; j<completed.size(); j++) {
            if (completed.get(j).containsKey(course.getDocId())) {
                pos = j;
            }
        }
        courseCompletion = (ArrayList<Boolean>) userData.getCompleted().get(pos).get(courseId);
        courseCompletion.set(item.getIndex(), newStatus);
        completed.get(pos).replace(courseId, courseCompletion);

        userData.setCompleted(completed);
        FirebaseFirestore.getInstance().collection("users").document(userData.getUserId()).set(userData);

        GlobalVariables.setDataChanged(true);
    }

    @Override
    public Intent getIntent(Item item) {
        Intent intent = new Intent();
        switch (item.type) {
            case "AR":
                return generateActivity(item.getTitle(), item.getContent(), item.getLowerBound());
            case "VIDEO":
                return generateActivity(item.getTitle(), item.getContent(), item.isStatus());
            case "READING":
                return generateActivity(item.getTitle(), item.getContent());
        }
        return intent;
    }

    @Override
    public Intent generateActivity(String title, String content, float lowerBound) {
        Intent intent = new Intent(getContext(), ARActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("lowerBound", lowerBound);
        return intent;
    }

    @Override
    public Intent generateActivity(String title, String content, boolean status) {
        Intent intent = intent = new Intent(getContext(), VideoActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("status", status);
        return intent;
    }

    @Override
    public Intent generateActivity(String title, String content) {
        Intent intent = new Intent(getContext(), ReadingActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        return intent;
    }
}
