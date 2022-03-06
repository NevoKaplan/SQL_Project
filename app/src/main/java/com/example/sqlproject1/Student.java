package com.example.sqlproject1;

public class Student {
    private long id;
    static private int counter = 0;
    private String name;
    private String surname;
    private String address;
    private int avgGrades;

    public Student(String name, String surname, String address, int avgGrades) {
        counter++;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.avgGrades = avgGrades;
        this.id = counter;
    }

    public Student(String name, String surname, String address, int avgGrades, long id) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.avgGrades = avgGrades;
        this.id = id;
    }


    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int count) {
        counter = count;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public int getAvgGrades() {
        return avgGrades;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvgGrades(int avgGrades) {
        this.avgGrades = avgGrades;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", avgGrades=" + avgGrades +
                '}';
    }
}
