package com.example.agenda.ui;

import java.io.Serializable;

public class test implements Serializable {
    public String name;

    public test(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
