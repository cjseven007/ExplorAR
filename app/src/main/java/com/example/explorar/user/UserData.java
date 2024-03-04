package com.example.explorar.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserData implements Serializable {
    ArrayList<String> myCourses;
    List<Map<String, Object>> completed;

    public ArrayList<String> getMyCourses() {
        return myCourses;
    }

    public void setMyCourses(ArrayList<String> myCourses) {
        this.myCourses = myCourses;
    }

    public List<Map<String, Object>> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Map<String, Object>> completed) {
        this.completed = completed;
    }
}
