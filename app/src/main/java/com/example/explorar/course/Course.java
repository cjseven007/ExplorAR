package com.example.explorar.course;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Course implements Serializable {
    String title;
    String content;
    String docId;
    List<Map<String, Object>> ar;
    List<Map<String, Object>> reading;
    List<Map<String, Object>> videos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public List<Map<String, Object>> getAr() {
        return ar;
    }

    public void setAr(List<Map<String, Object>> ar) {
        this.ar = ar;
    }

    public List<Map<String, Object>> getReading() {
        return reading;
    }

    public void setReading(List<Map<String, Object>> reading) {
        this.reading = reading;
    }

    public List<Map<String, Object>> getVideos() {
        return videos;
    }

    public void setVideos(List<Map<String, Object>> videos) {
        this.videos = videos;
    }
}
