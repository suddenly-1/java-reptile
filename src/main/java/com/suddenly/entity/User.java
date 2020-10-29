package com.suddenly.entity;

public class User {
    private String name;
    private String age;
    private String sex1;

    public User() {
    }

    public User(String name, String age, String sex1) {
        this.name = name;
        this.age = age;
        this.sex1 = sex1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex1() {
        return sex1;
    }

    public void setSex1(String sex1) {
        this.sex1 = sex1;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex1='" + sex1 + '\'' +
                '}';
    }
}
