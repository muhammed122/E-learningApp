package com.example.e_learningapp.models;

import java.util.ArrayList;

public class ModelCourse {



    private String instructorId  ;
    private String courseId ;
    private String courseName ;
    private double assignmentGrade ;
    private double attendanceGrade ;
    private double projectsGrade ;


    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getAssignmentGrade() {
        return assignmentGrade;
    }

    public void setAssignmentGrade(double assignmentGrade) {
        this.assignmentGrade = assignmentGrade;
    }

    public double getAttendanceGrade() {
        return attendanceGrade;
    }

    public void setAttendanceGrade(double attendanceGrade) {
        this.attendanceGrade = attendanceGrade;
    }

    public double getProjectsGrade() {
        return projectsGrade;
    }

    public void setProjectsGrade(double projectsGrade) {
        this.projectsGrade = projectsGrade;
    }

}
