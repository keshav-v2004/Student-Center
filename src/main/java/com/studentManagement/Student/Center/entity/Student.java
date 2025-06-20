package com.studentManagement.Student.Center.entity;


public class Student {

    private String roll;
    private String name;
    private String branch;


    //constructor
    public Student(
            String roll, String name, String branch
    ) {
        this.roll = roll;
        this.name = name;
        this.branch = branch;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }





}
