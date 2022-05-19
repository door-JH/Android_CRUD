package com.example.ps4.Model;

import java.io.Serializable;

public class Student implements Serializable {

    String num, name, phone, dept;

    public Student(){}

    public Student(String num, String name, String phone, String dept) {
        this.num = num;
        this.name = name;
        this.phone = phone;
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
                ", dept='" + dept + '\'' +
                '}';
    }
}
