package com.example.explorar.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserData implements Serializable {
    String name;
    String studentId;
    String userId;
    String email;
    ArrayList<String> courses;
    List<Map<String, Object>> completed;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
