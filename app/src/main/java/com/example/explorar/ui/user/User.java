package com.example.explorar.ui.user;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    String courses[];
    ArrayList<Boolean> courseCompletion;

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public ArrayList<Boolean> getCourseCompletion() {
        return courseCompletion;
    }

    public void setCourseCompletion(ArrayList<Boolean> courseCompletion) {
        this.courseCompletion = courseCompletion;
    }
}
