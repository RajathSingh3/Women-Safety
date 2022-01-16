package com.example.womensafety;

public class model {
    String name;
    String phNo;

    public model(String name, String phNo) {
        this.name = name;
        this.phNo = phNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }
}
