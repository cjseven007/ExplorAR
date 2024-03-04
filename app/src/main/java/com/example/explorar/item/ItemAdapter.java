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

public class ItemAdapter extends ArrayAdapter<Item> {
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
        titleTextView.setText(item.title);
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
                Intent intent = new Intent();
                switch (item.type) {
                    case "AR":
                        intent = new Intent(getContext(), ARActivity.class);
                        break;
                    case "VIDEO":
                        intent = new Intent(getContext(), VideoActivity.class);
                        break;
                    case "READING":
                        intent = new Intent(getContext(), ReadingActivity.class);
                        break;
                }
                intent.putExtra("title", item.title);
                intent.putExtra("content", item.content);
                intent.putExtra("status", item.status);
                intent.putExtra("lowerBound", item.lowerBound);
                getContext().startActivity(intent);
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
        ArrayList<Boolean> courseCompletion = (ArrayList<Boolean>) userData.getCompleted().get(0).get(courseId);
        courseCompletion.set(item.getIndex(), newStatus);
        completed.get(0).replace(courseId, courseCompletion);

        userData.setCompleted(completed);
        FirebaseFirestore.getInstance().collection("users").document(userData.getUserId()).set(userData);
    }
}
