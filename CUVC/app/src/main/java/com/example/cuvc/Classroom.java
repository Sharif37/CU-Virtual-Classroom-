package com.example.cuvc;

public class Classroom {
    private String classId;
    private String className;
    private String classDescription;
    private String adminId;
    private String classKey;

    public Classroom(String classId, String className, String classDescription, String adminId, String classKey) {
        this.classId = classId;
        this.className = className;
        this.classDescription = classDescription;
        this.adminId = adminId;
        this.classKey = classKey;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getClassKey() {
        return classKey;
    }

    public void setClassKey(String classKey) {
        this.classKey = classKey;
    }
}

