package com.company;

import java.util.Random;

public class Human {
    private String name;
    private int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final String[] names = new String[]{
      "VIKTOR", "MARIA", "DARIA", "VADIM", "DAVID", "STEFAN", "ARMINE", "JOSEF", "SUS",
            "SUSI", "DART", "PETYA", "VITALIK", "VOLODIMIR", "NIKOLAY"
    };

    public static Human createRandHuman(){
        Random random = new Random() ;
        return new Human(names[random.nextInt(names.length-1)], random.nextInt(90) + 10);
    }

    @Override
    public String toString() {
        return name + " "+ age;
    }
}
