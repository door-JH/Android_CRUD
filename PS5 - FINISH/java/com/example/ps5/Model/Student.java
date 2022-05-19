package com.example.ps5.Model;

import java.io.Serializable;

public class Student implements Serializable {

    String num, name, phone, imageName, dept;

    public Student(){}

    public Student(String num, String name, String phone, String imageName, String dept) {
        this.num = num;
        this.name = name;
        this.phone = phone;
        this.imageName = imageName;
        this.dept = dept;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Student{" +
                "num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", imageName='" + imageName + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}