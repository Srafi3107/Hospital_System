package model;

public class LabTest extends Test {
    public LabTest() { super(); }
    public LabTest(String testId, String testName, double cost) { super(testId, testName, cost); }
    @Override
    public String getType() { return "Lab"; }
}