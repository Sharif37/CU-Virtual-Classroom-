package com.example.cuvc;

public class ClassScheduleItem {
    private String courseName;
    private String startTime;
    //private String endTime;
    private String teacher;

    public ClassScheduleItem() {
        // Required empty constructor for Firebase
    }
    public ClassScheduleItem(String courseName, String startTime, String teacher) {
        this.courseName = courseName;
        this.startTime = startTime;
       // this.endTime = endTime;
        this.teacher = teacher;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getStartTime() {
        return startTime;
    }

//    public String getEndTime() {
//        return endTime;
//    }

    public String getTeacher() {
        return teacher;
    }
}
