package com.example.nemsy;


public class Bill {
    String name;
    String age;
    String proposer;
    String date;

    public Bill(String name, String age, String proposer, String date) {
        this.name = name;
        this.age = age;
        this.proposer = proposer;
        this.date = date;
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

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
