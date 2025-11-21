package model;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String pid;
    private String name;
    private String disease;
    private int age;
    private List<Test> tests = new ArrayList<>();

    public Patient() {}

    public Patient(String pid, String name, String disease, int age) {
        this.pid = pid;
        this.name = name;
        this.disease = disease;
        this.age = age;
    }

    public String getPid() { return pid; }
    public void setPid(String pid) { this.pid = pid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public List<Test> getTests() { return tests; }

    public void addTest(Test t) {
        if (t != null) tests.add(t);
    }

    @Override
    public String toString() {
        return String.format("Patient[pid=%s, name=%s, disease=%s, age=%d, tests=%d]",
                pid, name, disease, age, tests.size());
    }
}