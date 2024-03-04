package com.example.explorar.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserData implements Serializable {
    String userId;
    ArrayList<String> courses;
    List<Map<String, Object>> completed;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public List<Map<String, Object>> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Map<String, Object>> completed) {
        this.completed = completed;
    }
}
