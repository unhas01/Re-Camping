package com.example.woowa_recamping.data;

public class User {
    private String name;
    private String id;
    private String pwd;
    private int age;
    private String email;
    private String phone;
    private String addr;
    private int mile;

    public User(String name, String id, String pwd, int age, String email, String phone, String addr) {
        this.name = name;
        this.id = id;
        this.pwd = pwd;
        this.age =age;
        this.email = email;
        this.phone= phone;
        this.addr = addr;
        this.mile = 0;
    }
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddr() {
        return addr;
    }

    public int getMile() {
        return mile;
    }
}
